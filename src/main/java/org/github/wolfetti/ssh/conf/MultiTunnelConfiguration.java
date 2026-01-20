package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.util.List;
import java.util.Optional;

@ConfigMapping(prefix = "multi-tunnel")
public interface MultiTunnelConfiguration {
    boolean enabled();
    List<TunnelConfig> tunnels();
    
    interface TunnelConfig {
        String host();
        Integer port();
        @WithName("local-port") // Necessario per mappare local-port con il trattino
        Optional<Integer> localPort();
    }
}
