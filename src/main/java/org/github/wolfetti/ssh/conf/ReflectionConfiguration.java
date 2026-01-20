package org.github.wolfetti.ssh.conf;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(
    registerFullHierarchy = true,
    classNames = {
        "com.jcraft.jsch.JSch",
        "com.jcraft.jsch.Session",
        "com.jcraft.jsch.Channel",
        "com.jcraft.jsch.ChannelDirectTCPIP",
        "com.jcraft.jsch.KeyPair",
        "com.jcraft.jsch.jce.Random",
        "com.jcraft.jsch.jce.KeyPairGenRSA",
        "com.jcraft.jsch.jce.KeyPairGenDSA",
        "com.jcraft.jsch.jce.KeyPairGenECDSA",
        "com.jcraft.jsch.jce.SignatureRSA",
        "com.jcraft.jsch.jce.SignatureDSA",
        "com.jcraft.jsch.jce.SignatureECDSA",
        "com.jcraft.jsch.jce.HMACSHA1",
        "com.jcraft.jsch.jce.AES128CBC",
        "com.jcraft.jsch.jce.AES128CTR",
        "com.jcraft.jsch.jce.BlowfishCBC",
        "com.jcraft.jsch.jce.TripleDESCBC",
        "com.jcraft.jsch.jce.PBKDF2",
        "com.jcraft.jsch.jce.ECDH256",
        "com.jcraft.jsch.jce.ECDH384",
        "com.jcraft.jsch.jce.ECDH521",
        "com.jcraft.jsch.jgss.GSSContextKrb5",
        "com.jcraft.jsch.jcraft.Compression"
    }
)
public class ReflectionConfiguration {

}
