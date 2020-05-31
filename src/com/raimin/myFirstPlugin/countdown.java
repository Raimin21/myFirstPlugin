/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raimin.myFirstPlugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author joser
 */
public class countdown extends JavaPlugin {
    
    @Override
    public void onEnable() {
        new MyTask().runTaskTimer(plugin, 0, 20);
    }

    public class MyTask extends BukkitRunnable {
        private int countdown;

        public MyTask() {
            this.countdown = 5;
        }

        @Override
        public void run() {
            if (countdown <= 0) {
                System.out.println("Countdown is over. Cancelling task.");

                this.cancel(); // cancel this task. countdown is over.
                return;
            }

            System.out.println("Countdown: " + countdown);
            countdown--; // decrement the counter
        }
    }
}

    
}
