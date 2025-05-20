package org.github.wolfetti.jssht.conf;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "multi-tunnel.enabled", havingValue = "true", matchIfMissing = false)
public class MultiTunnelConfiguration {
    
    @Bean
    @ConfigurationProperties("multi-tunnel.tunnels")
    public List<Tunnel> tunnels(){
        return new ArrayList<>();
    }

    @Bean
    public TunnelDefinitions multiTunnel(List<Tunnel> tunnels) {
        return new TunnelDefinitions(tunnels);
    }
}
