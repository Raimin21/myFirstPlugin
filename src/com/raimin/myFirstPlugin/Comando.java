
package com.raimin.myFirstPlugin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Comando implements CommandExecutor {
    
    private Main plugin;
    
    public Comando(Main instance){
        
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (p.teleport(new Location(p.getWorld(),0 ,0 ,0), PlayerTeleportEvent.TeleportCause.PLUGIN)) {
            p.sendMessage("OK");
        } else {
            p.sendMessage("Nop");
        }


        if (label.equalsIgnoreCase("Comandoxd")){
            sender.sendMessage("JAJAJAJA HAS ESCRITO BIEN EL COMANDO BRO");
            if (sender instanceof Player){
                Player a = (Player) sender;
                a.sendMessage("Soy un jugador :D");
            } else {
                sender.sendMessage("Soy la consola :b");
            }
            if (args.length >= 1) {
                sender.sendMessage("Soy 1 argumento o más y has usado el argumento '"+ args[0]+"'");
            }
            
            if (sender.hasPermission("hola.test")){
                sender.sendMessage("Hey bro! Tienes el permiso 'hola.test'");
            }
        }
        return true;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
