
package org.github.wolfetti.jssht;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Autowired
    private ApplicationContext context;
    
    @Value("${ssh.private-key:classpath:private.key}")
    private String sshPrivateKey;
    
    @Value("${ssh.user}")
    private String sshUsername;
    
    @Value("${ssh.host}")
    private String sshHost;
    
    @Value("${ssh.port:22}")
    private int sshPort;
    
    @Value("${tunnel.host}")
    private String tunnelHost;
    
    @Value("${tunnel.port}")
    private int tunnelPort;
    
    @PostConstruct
    public void openTunnel() throws JSchException, IOException {
        log.info("Opening SSH tunnel to {}:{} through {}", tunnelHost, tunnelPort, sshHost);
        Resource pkey = context.getResource(sshPrivateKey);
        if(!pkey.exists()){
            throw new IllegalStateException("Private key not found in classpath: " + sshPrivateKey);
        }
    
        Properties sessionConfig = new Properties(); 
        sessionConfig.put("StrictHostKeyChecking", "no");
        sessionConfig.put("ServerAliveInterval", "60");
        
        JSch jsch = new JSch();

        log.debug("Reading identity file...");
        jsch.addIdentity(pkey.getFile().getAbsolutePath());
        
        log.debug("Creating session...");
        Session session = jsch.getSession(
            sshUsername,
            sshHost,
            sshPort
        );
        session.setConfig(sessionConfig);
        session.setTimeout(5000);
        log.debug("Session created");
        
        log.debug("Connecting to remote host...");
        session.connect();
        
        log.debug("Creating tunnel...");
        session.setPortForwardingL(
            "0.0.0.0", 
            tunnelPort, 
            tunnelHost, 
            tunnelPort
        );
        
        log.info("SSH tunnel to {}:{} through {} is up and running", tunnelHost, tunnelPort, sshHost);
    }

    public static void main(String[] args) {
        JSch.setLogger(new Slf4jJschLogger());
        SpringApplication.run(Application.class, args);
    }
}
