package org.github.wolfetti.ssh.cdi;

import io.quarkus.arc.lookup.LookupIfProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.Produces;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.github.wolfetti.ssh.conf.MultiTunnelConfiguration;
import org.github.wolfetti.ssh.conf.SingleTunnelConfiguration;
import org.github.wolfetti.ssh.dto.Tunnel;
import org.github.wolfetti.ssh.dto.TunnelDefinitions;

@ApplicationScoped
public class TunnelDefinitionsProducer {

    @Produces
    @Singleton
    @LookupIfProperty(name = "multi-tunnel.enabled", stringValue = "true")
    public TunnelDefinitions multiTunnel(MultiTunnelConfiguration config, Validator validator) {
        List<Tunnel> tunnels = config.tunnels().stream()
            .map(t -> new Tunnel(t.host(), t.port(), t.localPort().orElse(null)))
            .toList();

        Set<ConstraintViolation<Tunnel>> violations = new HashSet();

        for (Tunnel t : tunnels) {
            var tmp = new ArrayList<>(validator.validate(t, Tunnel.class));
            if (CollectionUtils.isNotEmpty(tmp)) {
                violations.addAll(tmp);
            }
        }

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return new TunnelDefinitions(tunnels);
    }

    @Produces
    @Singleton
    @LookupIfProperty(name = "multi-tunnel.enabled", stringValue = "false", lookupIfMissing = true)
    public TunnelDefinitions singleTunnel(SingleTunnelConfiguration config, Validator validator) {
        Tunnel tunnel = new Tunnel();
        tunnel.setHost(config.host());
        tunnel.setPort(config.port());
        tunnel.setLocalPort(config.localPort().orElse(null));

        var violations = validator.validate(tunnel);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return new TunnelDefinitions(tunnel);
    }
}
