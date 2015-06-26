package com.scuph.scuphsutilities.commands;

import com.scuph.scuphsutilities.ScuphUtils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.requesthelp")
public class Command_requesthelp extends BukkitCommand<ScuphUtils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        player.chat(ChatColor.UNDERLINE + "I need help! Can a staff member assist me?");
        return true;
    }

}
