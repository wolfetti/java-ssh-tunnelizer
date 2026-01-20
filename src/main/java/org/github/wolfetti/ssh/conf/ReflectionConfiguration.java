package org.github.wolfetti.ssh.conf;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
    com.jcraft.jsch.JSch.class,
    com.jcraft.jsch.Session.class,
    com.jcraft.jsch.Channel.class,
    com.jcraft.jsch.ChannelDirectTCPIP.class,
    com.jcraft.jsch.KeyPair.class
}, registerFullHierarchy = true)
public class ReflectionConfiguration {

}
