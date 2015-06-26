package com.scuph.scuphsutilities;

import com.scuph.scuphsutilities.commands.Command_admins;
import com.scuph.scuphsutilities.commands.Command_apply;
import com.scuph.scuphsutilities.commands.Command_ban;
import com.scuph.scuphsutilities.commands.Command_information;
import com.scuph.scuphsutilities.commands.Command_kick;
import com.scuph.scuphsutilities.commands.Command_notesforstaff;
import com.scuph.scuphsutilities.commands.Command_requesthelp;
import com.scuph.scuphsutilities.commands.Command_warn;
import com.scuph.scuphsutilities.listeners.JoinListener;
import com.scuph.scuphsutilities.listeners.ScuphsUtilitiesListener;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import net.pravian.bukkitlib.util.LoggerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ScuphUtils extends BukkitPlugin {

    public ScuphUtils plugin;
    public YamlConfig config;
    public BukkitCommandHandler handler;
    //

    @Override
    public void onLoad() {
        this.plugin = this;
        this.handler = new BukkitCommandHandler(plugin);
        //
    }

    @Override
    public void onEnable() {
        BukkitLib.init(plugin);
        config = new YamlConfig(plugin, "config.yml", true);
        config.load();

        register(new ScuphsUtilitiesListener());
        register(new JoinListener());

        handler.setCommandLocation(Command_information.class.getPackage());
        handler.setCommandLocation(Command_admins.class.getPackage());
        handler.setCommandLocation(Command_notesforstaff.class.getPackage());
        handler.setCommandLocation(Command_apply.class.getPackage());
        handler.setCommandLocation(Command_requesthelp.class.getPackage());
        handler.setCommandLocation(Command_ban.class.getPackage());
        handler.setCommandLocation(Command_warn.class.getPackage());
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
