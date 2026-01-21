package org.github.wolfetti.ssh.conf;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class SecurityProviderConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityProviderConfig.class);

    void onStart(@Observes StartupEvent ev) {
        log.debug("Registering BouncyCastle Provider...");
        Security.removeProvider("SunEC");
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            // Inserisci al primo posto per precedenza assoluta
            Security.insertProviderAt(new BouncyCastleProvider(), 1);
        }
        log.debug("Security Providers initialized.");
    }
}
