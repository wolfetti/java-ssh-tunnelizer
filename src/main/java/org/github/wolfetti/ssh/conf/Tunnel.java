package org.github.wolfetti.ssh.conf;

import io.smallrye.config.ConfigMapping;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.github.wolfetti.ssh.validation.ValidIpAddressOrHostname;
import org.github.wolfetti.ssh.validation.ValidPortNumber;

@ConfigMapping(prefix = "multi-tunnel")
public class Tunnel {

    @NotBlank
    @ValidIpAddressOrHostname
    private String host;

    @NotNull
    @ValidPortNumber
    private Integer port;

    @ValidPortNumber
    private Integer localPort;

    public void setLocalPort(Integer localPort) {
        this.localPort = localPort;
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

    public int getLocalPort() {
        if (localPort == null) {
            return port;
        }

        return localPort;
    }
}
