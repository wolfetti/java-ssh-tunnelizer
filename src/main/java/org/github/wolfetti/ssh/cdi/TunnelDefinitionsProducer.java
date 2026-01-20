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
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.github.wolfetti.ssh.conf.MultiTunnelConfiguration;
import org.github.wolfetti.ssh.conf.SingleTunnelConfiguration;
import org.github.wolfetti.ssh.conf.Tunnel;
import org.github.wolfetti.ssh.conf.TunnelDefinitions;

@ApplicationScoped
public class TunnelDefinitionsProducer {

    @Produces
    @Singleton
    @LookupIfProperty(name = "multi-tunnel.enabled", stringValue = "true")
    public TunnelDefinitions multiTunnel(MultiTunnelConfiguration config, Validator validator) {
        Set<ConstraintViolation<Tunnel>> violations = new HashSet();

        for (Tunnel t : config.tunnels()) {
            var tmp = new ArrayList<>(validator.validate(t, Tunnel.class));
            if (CollectionUtils.isNotEmpty(tmp)) {
                violations.addAll(tmp);
            }
        }

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return new TunnelDefinitions(config.tunnels());
    }

    @Produces
    @Singleton
    @LookupIfProperty(name = "multi-tunnel.enabled", stringValue = "false", lookupIfMissing = true)
    public TunnelDefinitions singleTunnel(SingleTunnelConfiguration tunnel, Validator validator) {
        var violations = validator.validate(tunnel);
        
        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return new TunnelDefinitions(tunnel);
    }
}
