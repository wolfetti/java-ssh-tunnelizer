package org.github.wolfetti.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;
import java.util.Properties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.github.wolfetti.ssh.dto.Tunnel;
import org.github.wolfetti.ssh.dto.TunnelDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.github.wolfetti.ssh.dto.SSHConnection;
import org.github.wolfetti.ssh.util.Slf4jJschLogger;

@Startup
@ApplicationScoped
public class SSHLauncher {

    private static final Logger log = LoggerFactory.getLogger(SSHLauncher.class);

    @Inject
    Instance<TunnelDefinitions> tunnels;

    @Inject
    SSHConnection ssh;

    private void initForNative() {
        Security.removeProvider("SunEC");
        if (Security.getProvider("BC") == null) {
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
        }

        // Algoritmi nel pacchetto base com.jcraft.jsch
        /*JSch.setConfig("diffie-hellman-group-exchange-sha256", "com.jcraft.jsch.DHGEX256");
        JSch.setConfig("diffie-hellman-group14-sha256", "com.jcraft.jsch.DH14");

        // Algoritmi nel pacchetto com.jcraft.jsch.jce
        JSch.setConfig("ecdh-sha2-nistp256", "com.jcraft.jsch.jce.ECDH256");
        JSch.setConfig("ecdh-sha2-nistp384", "com.jcraft.jsch.jce.ECDH384");
        JSch.setConfig("ecdh-sha2-nistp521", "com.jcraft.jsch.jce.ECDH521");
        JSch.setConfig("ssh-ed25519", "com.jcraft.jsch.jce.SignatureEd25519");
        JSch.setConfig("random", "com.jcraft.jsch.jce.Random");

        // Firme RSA SHA-2
        JSch.setConfig("rsa-sha2-256", "com.jcraft.jsch.jce.SignatureRSASHA256");
        JSch.setConfig("rsa-sha2-512", "com.jcraft.jsch.jce.SignatureRSASHA512");

        // Cifrari Moderni
        JSch.setConfig("aes128-gcm@openssh.com", "com.jcraft.jsch.jce.AES128GCM");
        JSch.setConfig("aes256-gcm@openssh.com", "com.jcraft.jsch.jce.AES256GCM");*/
    }

    /**
     * In Quarkus, il 'main' è implicito. Usiamo StartupEvent per eseguire codice all'avvio.
     */
    void onStart(@Observes StartupEvent ev) throws JSchException, IOException {

        // Logger setup
        JSch.setLogger(new Slf4jJschLogger());

        // This is for native image that complains with ssh security providers
        initForNative();

        // Start connection
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
        sessionConfig.put("kex", "ecdh-sha2-nistp256,ecdh-sha2-nistp384,ecdh-sha2-nistp521,diffie-hellman-group-exchange-sha256,diffie-hellman-group14-sha256");
        sessionConfig.put("server_host_key", "ecdsa-sha2-nistp256,ecdsa-sha2-nistp384,ecdsa-sha2-nistp521,rsa-sha2-512,rsa-sha2-256");

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
