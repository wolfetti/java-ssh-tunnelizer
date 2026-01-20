package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.github.wolfetti.ssh.validation.ValidIpAddressOrHostname;
import org.github.wolfetti.ssh.validation.ValidPortNumber;

@ConfigMapping(prefix = "ssh")
public class SSHDefinitions {
    
    @NotBlank
    private String privateKey = "private.key";
    
    @NotBlank
    private String user;
    
    @NotBlank
    @ValidIpAddressOrHostname
    private String host;
    
    @NotNull
    @ValidPortNumber
    private Integer port = 22;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
