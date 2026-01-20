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
        // Algoritmi AES GCM (necessari per OpenSSH moderno)
        "com.jcraft.jsch.jce.AES128GCM",
        "com.jcraft.jsch.jce.AES256GCM",
        "com.jcraft.jsch.jce.AES128CTR",
        "com.jcraft.jsch.jce.AES192CTR",
        "com.jcraft.jsch.jce.AES256CTR",
        "com.jcraft.jsch.jce.AES128CBC",
        "com.jcraft.jsch.jce.AES192CBC",
        "com.jcraft.jsch.jce.AES256CBC",
        // Scambio chiavi e firme
        "com.jcraft.jsch.jce.SignatureRSASHA256",
        "com.jcraft.jsch.jce.SignatureRSASHA512",
        "com.jcraft.jsch.jce.SignatureRSASHA256ETM",
        "com.jcraft.jsch.jce.SignatureRSASHA512ETM",
        "com.jcraft.jsch.jce.ECDH256",
        "com.jcraft.jsch.jce.ECDH384",
        "com.jcraft.jsch.jce.ECDH521",
        "com.jcraft.jsch.jce.X25519",
        "com.jcraft.jsch.jce.X448",
        "com.jcraft.jsch.jce.SignatureECDSA256",
        "com.jcraft.jsch.jce.SignatureECDSA384",
        "com.jcraft.jsch.jce.SignatureECDSA521",
        "com.jcraft.jsch.jce.SignatureEd25519",
        "com.jcraft.jsch.jce.SignatureEd448",
        // KeyPair generators
        "com.jcraft.jsch.jce.KeyPairGenRSA",
        "com.jcraft.jsch.jce.KeyPairGenDSA",
        "com.jcraft.jsch.jce.KeyPairGenECDSA",
        "com.jcraft.jsch.jce.KeyPairGenEd25519",
        "com.jcraft.jsch.jce.KeyPairGenEd448",
        // Altri
        "com.jcraft.jsch.jce.HMACSHA1",
        "com.jcraft.jsch.jce.HMACSHA256",
        "com.jcraft.jsch.jce.HMACSHA512",
        "com.jcraft.jsch.jce.PBKDF2",
        "com.jcraft.jsch.jcraft.Compression"
    }
)
public class ReflectionConfiguration {

}
