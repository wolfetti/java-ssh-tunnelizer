package org.github.wolfetti.ssh;

import com.jcraft.jsch.JSch;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.github.wolfetti.ssh.util.Slf4jJschLogger;

@QuarkusMain
public class Main {
    public static void main(String... args) {
        JSch.setLogger(new Slf4jJschLogger());
        Quarkus.run(args);
    }
}
