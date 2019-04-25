package net.unix.wuwqueue.manager.impl;

import net.unix.wuwqueue.manager.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.IntStream;

public class QueueManagerImpl implements QueueManager {

    private final Queue<UUID> queues = new PriorityQueue<>();

    @Override
    public void addToQueue(UUID uuid) {
        this.queues.add(uuid);
    }

    @Override
    public void removeFromQueue(UUID uuid) {
        this.queues.remove(uuid);
    }

    @Override
    public Player getAndRemove() {
        return Bukkit.getPlayer(this.queues.poll());
    }

    @Override
    public Queue<UUID> getQueues() {
        return this.queues;
    }

    @Override
    public int getAt(UUID uuid) {
        return IntStream.range(0, this.queues.size())
                .filter(i -> this.queues.toArray()[i] == uuid)
                .findFirst()
                .orElse(-1);
    }
}