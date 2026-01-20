package org.github.wolfetti.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.github.wolfetti.ssh.dto.Tunnel;
import org.github.wolfetti.ssh.dto.TunnelDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.github.wolfetti.ssh.dto.SSHConnection;
import org.github.wolfetti.ssh.util.Slf4jJschLogger;

@ApplicationScoped 
public class SSHLauncher {
    private static final Logger log = LoggerFactory.getLogger(SSHLauncher.class);
    
    @Inject
    Instance<TunnelDefinitions> tunnels;

    @Inject
    SSHConnection ssh;

    /**
     * In Quarkus, il 'main' è implicito. Usiamo StartupEvent per eseguire codice all'avvio.
     */
    void onStart(@Observes StartupEvent ev) throws JSchException, IOException {
        JSch.setLogger(new Slf4jJschLogger());
        connect();
    }

    public void connect() throws JSchException, IOException {
        // Gestione della risorsa (sostituisce context.getResource)
        Path pkeyPath = Paths.get(ssh.getPrivateKey());

        if (!Files.exists(pkeyPath)) {
            throw new IllegalStateException("Private key not found: " + ssh.getPrivateKey());
        }
        if (!Files.isReadable(pkeyPath)) {
            throw new IllegalStateException("Private key not readable: " + ssh.getPrivateKey());
        }

        Properties sessionConfig = new Properties();
        sessionConfig.put("StrictHostKeyChecking", "no");
        sessionConfig.put("ServerAliveInterval", "60");

        JSch jsch = new JSch();

        log.info("Reading private key file from {}...", pkeyPath.toAbsolutePath());
        jsch.addIdentity(pkeyPath.toAbsolutePath().toString());

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
        tunnels.get().forEach(t -> {
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
        return "to " + t.getHost() + ":" + t.getPort() + " on local port " + t.getLocalPort();
    }
}
