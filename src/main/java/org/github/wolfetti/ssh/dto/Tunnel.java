package org.github.wolfetti.ssh.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.github.wolfetti.ssh.validation.ValidIpAddressOrHostname;
import org.github.wolfetti.ssh.validation.ValidPortNumber;

public class Tunnel {

    public Tunnel() {
    }

    public Tunnel(String host, Integer port, Integer localPort) {
        this.host = host;
        this.port = port;
        this.localPort = localPort;
    }

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

    public Integer getLocalPort() {
        if (localPort == null) {
            return port;
        }

        return localPort;
    }
}
