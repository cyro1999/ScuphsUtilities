package com.scuph.scuphutils.util;

import com.scuph.scuphutils.ScuphUtils;
import com.scuph.scuphutils.util.Service;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractService implements Service, Listener {

    protected final ScuphUtils plugin;
    protected final Server server;
    protected final BukkitLogger logger;
    //
    protected boolean started;

    protected AbstractService(ScuphUtils plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.logger = plugin.logger;
        //
        this.started = false;
    }

    @Override
    public final void start() {
        if (started) {
            logger.warning("Tried to start service: " + getClass().getSimpleName() + " twice!");
            return;
        }
        started = true;

        // Start
        onStart();

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public final void stop() {
        if (!started) {
            logger.warning("Tried to stop service: " + getClass().getSimpleName() + " twice!");
            return;
        }
        started = false;

        // Unregister events
        HandlerList.unregisterAll(this);

        // Stop
        onStop();
    }

    protected abstract void onStart();

    protected abstract void onStop();
}
