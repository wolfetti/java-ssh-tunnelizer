package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import java.util.List;

@ConfigMapping(prefix = "multi-tunnel")
public interface MultiTunnelConfiguration {
    boolean enabled();
    List<Tunnel> tunnels();
}
