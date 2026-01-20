package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

/**
 *
 * @author Fabio Frijo
 */
@ConfigMapping(prefix = "ssh")
public interface SSHConnectionConfiguration {
    String host();
    String user();
    
    @WithDefault("22")
    Integer port();
    
    @WithDefault("private.key")
    String privateKey();
}
