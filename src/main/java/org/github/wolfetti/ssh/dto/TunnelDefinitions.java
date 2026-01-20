package org.github.wolfetti.ssh.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public final class TunnelDefinitions implements Iterable<Tunnel>{
    private final List<Tunnel> tunnels;

    public TunnelDefinitions(List<Tunnel> tunnels) {
        this.tunnels = tunnels;
    }

    public TunnelDefinitions(Tunnel tunnel) {
        this(new ArrayList<>());
        this.tunnels.add(tunnel);
    }

    @Override
    public Iterator<Tunnel> iterator() {
        return this.tunnels.iterator();
    }

    @Override
    public void forEach(Consumer<? super Tunnel> action) {
        this.tunnels.forEach(action);
    }

    @Override
    public Spliterator<Tunnel> spliterator() {
        return this.tunnels.spliterator();
    }
}
