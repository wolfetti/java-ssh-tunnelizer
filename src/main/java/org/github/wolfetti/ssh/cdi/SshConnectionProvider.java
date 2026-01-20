package org.github.wolfetti.ssh.cdi;

import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.Produces;
import org.apache.commons.collections4.CollectionUtils;
import org.github.wolfetti.ssh.conf.SSHConnectionConfiguration;
import org.github.wolfetti.ssh.dto.SSHConnection;

public class SshConnectionProvider {

    @Produces
    @Singleton
    public SSHConnection connection(SSHConnectionConfiguration config, Validator validator) {
        SSHConnection conn = new SSHConnection();
        conn.setHost(config.host());
        conn.setPort(config.port());
        conn.setUser(config.user());
        conn.setPrivateKey(config.privateKey());

        var violations = validator.validate(conn);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        return conn;
    }
}
