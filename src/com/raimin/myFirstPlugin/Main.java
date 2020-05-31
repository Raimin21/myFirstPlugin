/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raimin.myFirstPlugin;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.MINUTES;
import jdk.nashorn.internal.ir.Block;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import static org.bukkit.WorldCreator.name;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {
    
    public static Main instance;
    public Scoreboard board;
    
    boolean propulsion = true;

    @Override 
    public void onEnable(){
        instance = this;
        
        setupScoreboards();
        getServer().getPluginManager().registerEvents(this, this);
        
        this.getCommand("Comandoxd").setExecutor(new Comando(this));
        //this.getServer().getPluginManager().registerEvents(new PlayerListener(instance), instance);
        this.getLogger().info("El plugin ha sido cargado");
    }
    
    public void setupScoreboards(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        
        Objective objetivo = board.registerNewObjective("bloques", "dummy");
        objetivo.setDisplayName("Bloques Rotos");
        objetivo.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    @Override 
    public void onDisable(){
        this.getLogger().info("El plugin ha sido descargado");
    }
    
    private HashMap<Player, Integer> bloquesRotos = new HashMap<>();
    @EventHandler
    public void onBreak (BlockBreakEvent e) {
        int bloques = 0;
        if (bloquesRotos.containsKey(e.getPlayer())){
            bloques = bloquesRotos.get(e.getPlayer());
        }
        bloques++;
        bloquesRotos.put(e.getPlayer(), bloques);
        board.getObjective(DisplaySlot.SIDEBAR).getScore(e.getPlayer().getName()).setScore(bloques);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
	if ((e.getDamager() instanceof Snowball)) {
        	e.setDamage(e.getDamage() + 10);
                this.getLogger().info("PLAYER ESTÁ SIENDO DAÑADO CON UNA SNOWBALL");
                int bolasAcertadas = 0;
                bolasAcertadas++;
                //loquesRotos.put((Player) e.getDamager(), bolasAcertadas);
                //board.getObjective(DisplaySlot.SIDEBAR).getScore(e.getDamager().getName()).setScore(bolasAcertadas);
	}

}

    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.getPlayer().setScoreboard(board);
        Player p = e.getPlayer();
        p.teleport(new Location(p.getWorld(),751 ,47 ,1369), PlayerTeleportEvent.TeleportCause.PLUGIN);
        e.setJoinMessage("§3¡§b" + e.getPlayer().getName() + "§3 ha entrado!");
        
        p.sendTitle("§bBienvenidx a SnowBall II", "§f§odesigned by DixiMax & redeveloped by Raimin21", 30, 20, 30);
        
        dar(p);
    }
    
    public void dar(Player p){
        ItemStack snowball = new ItemStack(Material.SNOWBALL, 1);
        ItemStack slimeball = new ItemStack(Material.SLIME_BALL, 1);
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemStack bloques = new ItemStack(Material.SNOW_BLOCK, 16);
        ItemMeta im = slimeball.getItemMeta();
        im.setDisplayName("vida");
        p.getInventory().setItem(0, snowball);
        p.getInventory().setItem(3, bloques);
        p.getInventory().setItem(5, slimeball);
        p.getInventory().setItem(8, clock);
    }
    
    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) throws InterruptedException {
        Player p = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && item.getType() == Material.SLIME_BALL) {
                if (propulsion == true) {
                    propulsion = false;
                    p.sendMessage( "§3§opium!" );
                    p.setVelocity(p.getLocation().getDirection().multiply(2));
                    //TimeUnit.SECONDS.sleep(10);
                    propulsion = true;
                } else {
                    p.sendMessage("§cDebes esperar para de usar este objeto de nuevo :o");
                }
                
            }
        }
        
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && item.getType() == Material.SNOWBALL) {
                ItemStack snowball = new ItemStack(Material.SNOWBALL);
                p.getInventory().setItem(0, snowball);
            }
        }
        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null && item.getType() == Material.CLOCK) {
                curar(p);
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player && event.getCause() == DamageCause.FALL)
             event.setCancelled(true);
    }    
    
    @EventHandler
    public void bolas (ProjectileLaunchEvent e){
        this.getLogger().info("§cDANDO BOLAS");
        Snowball a = (Snowball) e.getEntity();
        Player p = (Player) a.getShooter();
        this.getLogger().info("§cDANDO BOLAS X2");
        //dar(p);
        ItemStack snowball = new ItemStack(Material.SNOWBALL);
        p.getInventory().addItem(snowball);
    }

    public void curar (Player p) {
        double salud = p.getHealth();
        if (salud <= 12){
            p.getPlayer().setHealth((float)p.getPlayer().getHealth() + (float)8);
            p.sendMessage("§aCurado! §7§o(+4hearts)");
        }else {
            p.sendMessage("§eAmigo/a no puedes usar eso ¡ya tienes mucha vida!");
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(new Location(e.getPlayer().getWorld(), 751 ,47 ,1369));
        e.getPlayer().setScoreboard(board);
        Player p = e.getPlayer();
        dar(p);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        return true;
    }
 
    //ESTO ESTÁ SIN TERMINAR (REMPLAZAR LOS BLOQUES)
    public void onBlockPlace(BlockPlaceEvent e){
       // e.getBlock().getX()
       Block block = (Block) e.getBlock();
       //Material material = block.getType();
       Player p = e.getPlayer();
    }
    //HASTA AQUÍ
    
    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.setFoodLevel(20);
   }
}
