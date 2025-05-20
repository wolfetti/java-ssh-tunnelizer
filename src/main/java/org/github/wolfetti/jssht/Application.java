
package org.github.wolfetti.jssht;

import org.github.wolfetti.jssht.util.Slf4jJschLogger;
import org.github.wolfetti.jssht.conf.Tunnel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.github.wolfetti.jssht.conf.SSHDefinitions;
import org.github.wolfetti.jssht.conf.TunnelDefinitions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

/**
 * @author Fabio Frijo
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        JSch.setLogger(new Slf4jJschLogger());
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private TunnelDefinitions tunnels;
    
    @Autowired
    private SSHDefinitions ssh;
    
    @PostConstruct
    public void connect() throws JSchException, IOException {
        Resource pkey = context.getResource(ssh.getPrivateKey());
        if(!pkey.exists()){
            throw new IllegalStateException("Private key not found: " + ssh.getPrivateKey());
        }
        if (!pkey.isReadable()){
            throw new IllegalStateException("Private key not readable: " + ssh.getPrivateKey());
        }
    
        Properties sessionConfig = new Properties(); 
        sessionConfig.put("StrictHostKeyChecking", "no");
        sessionConfig.put("ServerAliveInterval", "60");
        
        JSch jsch = new JSch();

        log.info("Reading private key file...");
        jsch.addIdentity(pkey.getFile().getAbsolutePath());
        
        log.debug("Creating session...");
        Session session = jsch.getSession(
            ssh.getUser(),
            ssh.getHost(),
            ssh.getPort()
        );
        session.setConfig(sessionConfig);
        session.setTimeout(5000);
        log.debug("Session created");
        
        log.info("Connecting to remote host {} on port {}...", ssh.getHost(), ssh.getPort());
        session.connect();
        
        log.debug("Creating tunnels...");
        tunnels.forEach(t -> {
            log.info("Opening SSH tunnel {}...", createTunnelInfo(t));
        
            try {
                session.setPortForwardingL(
                    "0.0.0.0",
                    t.getLocalPort(),
                    t.getHost(),
                    t.getPort()
                );
            } catch (JSchException ex) {
                throw new IllegalStateException("Tunnel configuration is wrong", ex);
            }
        });
        
        log.info("All tunnels are up and running!");
    }

    private String createTunnelInfo(Tunnel t) {
        return new StringBuilder()
           .append("to ")
           .append(t.getHost())
           .append(":")
           .append(t.getPort())
           .append(" on local port ")
           .append(t.getLocalPort())
        .toString();
    }
}
