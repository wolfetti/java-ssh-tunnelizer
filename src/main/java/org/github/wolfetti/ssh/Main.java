package org.github.wolfetti.ssh;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {
    public static void main(String... args) {
//        JSch.setLogger(new Slf4jJschLogger());
        
        // Lancia il framework Quarkus
        Quarkus.run(args);
    }
}
