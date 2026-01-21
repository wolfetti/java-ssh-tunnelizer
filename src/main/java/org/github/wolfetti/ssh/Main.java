package org.github.wolfetti.ssh;

import com.jcraft.jsch.JSch;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.github.wolfetti.ssh.util.Slf4jJschLogger;

@QuarkusMain
public class Main {

    public static void main(String... args) {
        JSch.setLogger(new Slf4jJschLogger());
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        Quarkus.run(args);
    }
}
