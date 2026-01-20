package org.github.wolfetti.ssh.conf;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(
    registerFullHierarchy = true,
    classNames = {
        // Core JSch
        "com.jcraft.jsch.JSch",
        "com.jcraft.jsch.Session",
        "com.jcraft.jsch.Channel",
        "com.jcraft.jsch.ChannelSession",
        "com.jcraft.jsch.ChannelDirectTCPIP",
        "com.jcraft.jsch.ChannelForwardedTCPIP",
        "com.jcraft.jsch.ChannelShell",
        "com.jcraft.jsch.ChannelExec",
        "com.jcraft.jsch.ChannelSftp",
        "com.jcraft.jsch.ChannelSubsystem",
        "com.jcraft.jsch.KeyPair",
        "com.jcraft.jsch.Identity",
        "com.jcraft.jsch.IdentityRepository",
        "com.jcraft.jsch.HostKey",
        "com.jcraft.jsch.HostKeyRepository",
        "com.jcraft.jsch.PortWatcher",
        "com.jcraft.jsch.Util",
        "com.jcraft.jsch.ConfigRepository",
        "com.jcraft.jsch.Proxy",
        "com.jcraft.jsch.ProxyHTTP",
        "com.jcraft.jsch.ProxySOCKS4",
        "com.jcraft.jsch.ProxySOCKS5",

        // Random & Security Providers
        "com.jcraft.jsch.jce.Random",
        "com.jcraft.jsch.jce.PBKDF2",
        "com.jcraft.jsch.jce.DH",
        "com.jcraft.jsch.jce.ECDH256",
        "com.jcraft.jsch.jce.ECDH384",
        "com.jcraft.jsch.jce.ECDH521",
        "com.jcraft.jsch.jce.X25519",
        "com.jcraft.jsch.jce.X448",

        // Ciphers (Standard & GCM)
        "com.jcraft.jsch.jce.AES128CTR",
        "com.jcraft.jsch.jce.AES192CTR",
        "com.jcraft.jsch.jce.AES256CTR",
        "com.jcraft.jsch.jce.AES128CBC",
        "com.jcraft.jsch.jce.AES192CBC",
        "com.jcraft.jsch.jce.AES256CBC",
        "com.jcraft.jsch.jce.AES128GCM",
        "com.jcraft.jsch.jce.AES256GCM",
        "com.jcraft.jsch.jce.BlowfishCBC",
        "com.jcraft.jsch.jce.TripleDESCBC",
        "com.jcraft.jsch.jce.ARCFOUR",
        "com.jcraft.jsch.jce.ARCFOUR128",
        "com.jcraft.jsch.jce.ARCFOUR256",

        // Signatures & KeyPair Generators
        "com.jcraft.jsch.jce.SignatureRSA",
        "com.jcraft.jsch.jce.SignatureRSASHA256",
        "com.jcraft.jsch.jce.SignatureRSASHA512",
        "com.jcraft.jsch.jce.SignatureDSA",
        "com.jcraft.jsch.jce.SignatureECDSA256",
        "com.jcraft.jsch.jce.SignatureECDSA384",
        "com.jcraft.jsch.jce.SignatureECDSA521",
        "com.jcraft.jsch.jce.SignatureEd25519",
        "com.jcraft.jsch.jce.SignatureEd448",
        "com.jcraft.jsch.jce.KeyPairGenRSA",
        "com.jcraft.jsch.jce.KeyPairGenDSA",
        "com.jcraft.jsch.jce.KeyPairGenECDSA",
        "com.jcraft.jsch.jce.KeyPairGenEd25519",
        "com.jcraft.jsch.jce.KeyPairGenEd448",

        // HMACs
        "com.jcraft.jsch.jce.HMACMD5",
        "com.jcraft.jsch.jce.HMACMD596",
        "com.jcraft.jsch.jce.HMACSHA1",
        "com.jcraft.jsch.jce.HMACSHA196",
        "com.jcraft.jsch.jce.HMACSHA256",
        "com.jcraft.jsch.jce.HMACSHA512",
        "com.jcraft.jsch.jce.HMACSHA1ETM",
        "com.jcraft.jsch.jce.HMACSHA256ETM",
        "com.jcraft.jsch.jce.HMACSHA512ETM",

        // Other components
        "com.jcraft.jsch.jcraft.Compression",
        "com.jcraft.jsch.jgss.GSSContextKrb5",
        "com.jcraft.jsch.UserAuthNone",
        "com.jcraft.jsch.UserAuthPassword",
        "com.jcraft.jsch.UserAuthPublicKey",
        "com.jcraft.jsch.UserAuthKeyboardInteractive",
        "com.jcraft.jsch.KeyExchange",
        "com.jcraft.jsch.DHEC256",
        "com.jcraft.jsch.DHEC384",
        "com.jcraft.jsch.DHEC521",
        "com.jcraft.jsch.DH25519",
        "com.jcraft.jsch.DH448"
    }
)
public class ReflectionConfiguration {
}
