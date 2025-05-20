package org.github.wolfetti.jssht.conf;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "multi-tunnel.enabled", havingValue = "false", matchIfMissing = true)
public class SingleTunnelConfiguration {
    
    @Bean
    @ConfigurationProperties("tunnel")
    public Tunnel tunnel(){
        return new Tunnel();
    }

    @Bean
    public TunnelDefinitions singleTunnel(Tunnel tunnel) {
        return new TunnelDefinitions(tunnel);
    }
}
