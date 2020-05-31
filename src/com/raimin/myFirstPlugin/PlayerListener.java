package com.raimin.myFirstPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerListener implements Listener {
    
    private Main plugin;
    public PlayerListener(Main instance) {
        plugin = instance;
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        plugin.getServer().broadcastMessage("Se ha conectado "+p.getName()+" con UUID "+p.getUniqueId()+" y tiene una vida de "+p.getHealth()+"/20");
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player p = event.getPlayer();
        if (!p.hasPermission("raimin.moverse")){
            event.setCancelled(true);
            return;
        }
        p.sendMessage(ChatColor.GOLD+"Te has movido jeje. Ahora estás en X = "+event.getTo().getBlockX());
        p.setHealth(17D);
        p.setFoodLevel(9);
        p.getServer().broadcastMessage("La vida y la comida de "+p.getName()+" ha sido seteada");
    }
        
}
