package org.github.wolfetti.jssht.conf;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.github.wolfetti.jssht.validation.ValidPortNumber;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.github.wolfetti.jssht.validation.ValidIpAddressOrHostname;

@Setter
@Getter
@ToString
@Component
@Validated
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ConfigurationProperties("ssh")
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
}
