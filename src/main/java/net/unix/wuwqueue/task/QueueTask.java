package net.unix.wuwqueue.task;

import lombok.AllArgsConstructor;
import net.unix.wuwqueue.basic.QueuePlugin;
import net.unix.wuwqueue.manager.QueueManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@AllArgsConstructor
public class QueueTask implements Runnable {

    private final QueuePlugin plugin;
    private final QueueManager queueManager;
    private final String server, message;

    @Override
    public void run() {
        if (this.queueManager.getQueues().size() == 0) {
            return;
        }

        final Player player = this.queueManager.getAndRemove();

        this.connect(player);
        this.queueManager.getQueues().forEach(q -> Bukkit.getPlayer(q).sendMessage(this.message.replace("{1}", Integer.toString(this.queueManager.getAt(q)))));
    }

    private void connect(Player player) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        final DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(this.server);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
    }
}