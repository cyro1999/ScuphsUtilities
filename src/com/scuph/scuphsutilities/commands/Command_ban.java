package com.scuph.scuphsutilities.commands;

import com.scuph.scuphsutilities.ScuphUtils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.ban")
public class Command_ban extends BukkitCommand<ScuphUtils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 3) {
            return showUsage();
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }

        String banReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Banning: " + ChatColor.YELLOW + player.getName() + ChatColor.RED + "\nReason - " + ChatColor.YELLOW + banReason);

        player.kickPlayer("You have been banned\nReason: " + ChatColor.RED + banReason + ChatColor.RED + "\nBanned by ~ " + ChatColor.YELLOW + sender.getName() + "\n" + ChatColor.RED + plugin.getConfig().getString("Appeal-Message"));
        Bukkit.getBanList(Type.NAME).addBan(player.getName(), ChatColor.RED + banReason + ChatColor.RED + "\nBanned by ~ " + ChatColor.YELLOW + sender.getName() + plugin.getConfig().getString("Appeal-Message"), null, "source");

        sender.sendMessage(ChatColor.GRAY + "Player successfully banned");

        return true;
    }

}
