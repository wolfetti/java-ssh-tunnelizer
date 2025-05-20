package org.github.wolfetti.jssht.conf;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import org.github.wolfetti.jssht.validation.ValidIpAddress;

@Setter
@ToString
@Validated
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Tunnel {

    @Getter
    @NotBlank
    @ValidIpAddress
    private String host;

    @Getter
    @NotNull
    @Min(1)
    @Max(65535)
    private Integer port;

    @Min(1)
    @Max(65535)
    private Integer localPort;

    public int getLocalPort() {
        if (localPort == null) {
            return port;
        }

        return localPort;
    }
}
