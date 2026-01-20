package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.util.Optional;

@ConfigMapping(prefix = "tunnel")
public interface SingleTunnelConfiguration {
    String host();
    Integer port();
    
    @WithName("local-port")
    Optional<Integer> localPort();
}
