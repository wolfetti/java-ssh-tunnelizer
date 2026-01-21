package org.github.wolfetti.ssh.conf;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Slf4jLogger;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class JschLoggerConfig {

    void onStart(@Observes StartupEvent ev) {
        JSch.setLogger(new Slf4jLogger());
    }
}
