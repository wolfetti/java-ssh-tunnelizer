package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import jakarta.enterprise.inject.Default;

@Default
@ConfigMapping(prefix = "tunnel")
public class SingleTunnelConfiguration extends Tunnel {
}
