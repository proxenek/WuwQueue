package net.unix.wuwqueue.basic;

import net.unix.wuwqueue.listener.PlayerJoinListener;
import net.unix.wuwqueue.listener.PlayerQuitListener;
import net.unix.wuwqueue.manager.QueueManager;
import net.unix.wuwqueue.manager.impl.QueueManagerImpl;
import net.unix.wuwqueue.task.QueueTask;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class QueuePlugin extends JavaPlugin {

    private final QueueManager queueManager;

    public QueuePlugin() {
        this.queueManager = new QueueManagerImpl();
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        final FileConfiguration config = this.getConfig();
        final Server server = this.getServer();
        final PluginManager pm = server.getPluginManager();

        pm.registerEvents(new PlayerJoinListener(this.queueManager), this);
        pm.registerEvents(new PlayerQuitListener(this.queueManager), this);

        server.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        server.getScheduler().runTaskTimer(this, new QueueTask(this, this.queueManager, config.getString("server"), config.getString("message")), 30L, 30L);
    }
}