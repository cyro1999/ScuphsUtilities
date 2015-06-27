package com.scuph.scuphutils;

import com.scuph.scuphutils.banning.BanManager;
import com.scuph.scuphutils.command.Command_kick;
import com.scuph.scuphutils.listener.UtilsListener;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class ScuphUtils extends BukkitPlugin {

    public static ScuphUtils plugin;
    //
    public YamlConfig config;
    public BukkitLogger logger;
    public BukkitCommandHandler<ScuphUtils> handler;
    public BanManager bm;

    @Override
    public void onLoad() {
        plugin = this;
        //
        this.config = new YamlConfig(plugin, "config.yml", true);
        this.logger = new BukkitLogger(plugin);
        this.handler = new BukkitCommandHandler<>(plugin);
        this.bm = new BanManager(plugin);
    }

    @Override
    public void onEnable() {
        plugin = this;
        BukkitLib.init(plugin);

        // Load config
        config.load();

        // Start services
        bm.start();

        // Register events
        register(new UtilsListener());

        // Setup command handler
        handler.setCommandLocation(Command_kick.class.getPackage());

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + StringUtils.join(getAuthors(), ", ") + " is enabled");
    }

    @Override
    public void onDisable() {

        plugin = this;

        // Unregister events
        HandlerList.unregisterAll(plugin);

        // Stop services
        bm.stop();

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }

}
