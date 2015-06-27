package com.scuph.scuphutils.command;

import com.scuph.scuphutils.ScuphUtils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.info")
public class Command_information extends BukkitCommand<ScuphUtils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "This plugin was made by ScuphGamingUK, made to set the core of a server! But keep in mind: he is not part of " + plugin.getConfig().getString("server-name") + "'s staff!");
        sender.sendMessage(ChatColor.RED + "This plugin's version is " + plugin.getVersion() + "!");
        sender.sendMessage(ChatColor.AQUA + "This plugin is running on " + plugin.getConfig().getString("server-name") + "!");
        sender.sendMessage(ChatColor.RED + "The server's current forum are " + plugin.getConfig().getString("website"));
        sender.sendMessage(ChatColor.BLUE + "The server's owner is " + plugin.getConfig().getString("owner") + "!");
        sender.sendMessage(ChatColor.DARK_RED + "The server's rules can be found by doing " + plugin.getConfig().getString("rule-command") + "!");
        return true;
    }

}
