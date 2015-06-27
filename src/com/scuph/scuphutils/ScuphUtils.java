package com.scuph.scuphutils;

import com.scuph.scuphutils.command.Command_kick;
import com.scuph.scuphutils.listener.JoinListener;
import com.scuph.scuphutils.listener.ScuphsUtilitiesListener;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ScuphUtils extends BukkitPlugin {

    public ScuphUtils plugin;
    public YamlConfig config;
    public BukkitLogger logger;
    public BukkitCommandHandler<ScuphUtils> handler;
    //

    @Override
    public void onLoad() {
        this.plugin = this;
        this.config = new YamlConfig(plugin, "config.yml", true);
        this.logger = new BukkitLogger(plugin);
        this.handler = new BukkitCommandHandler<>(plugin);
    }

    @Override
    public void onEnable() {
        BukkitLib.init(plugin);

        config.load();

        register(new ScuphsUtilitiesListener());
        register(new JoinListener());

        handler.setCommandLocation(Command_kick.class.getPackage());

        LoggerUtils.info(plugin, "for " + config.getString("server-name") + " Version: " + plugin.getVersion() + " by " + plugin.getAuthor() + " is enabled.");
    }

    @Override
    public void onDisable() {
        LoggerUtils.info(plugin, "for " + config.getString("server-name") + " Version: " + plugin.getVersion() + " by " + plugin.getAuthor() + " was disabled.");

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        return handler.handleCommand(sender, cmd, commandLabel, args);
    }

}
