package com.scuph.scuphsutilities.listeners;

import com.scuph.scuphsutilities.ScuphUtils;
import net.pravian.bukkitlib.config.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    public ScuphUtils plugin;
    public YamlConfig Config;

    @EventHandler
    public void OnJoin(PlayerJoinEvent event) {
        Player p = (Player) event.getPlayer();
        String name = p.getName();
        //If player has not joined before
        if (!p.hasPlayedBefore()) {
            p.sendMessage(ChatColor.DARK_GREEN + "Welcome, Hope you enjoy your stay!");
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + name + " has joined " + Config.getString("server-name") + " for the first time! Welcome them!");
        } else {
            p.sendMessage(ChatColor.DARK_GREEN + "Hey there " + name + ", welcome back to " + Config.getString("server-name") + "!");
            Bukkit.broadcastMessage(ChatColor.AQUA + "{" + Config.getString("server-name") + "}" + " Welcome back, " + name);
        }

    }
}
