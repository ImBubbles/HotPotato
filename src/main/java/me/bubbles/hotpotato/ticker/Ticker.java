package me.bubbles.hotpotato.ticker;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.games.Timer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;

public class Ticker {

    private boolean enabled;
    private HotPotato plugin;

    public Ticker(HotPotato plugin) {
        this.plugin=plugin;
        Count();
    }

    private void Count() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(enabled) {
                    plugin.onSecond();
                    Count();
                }
            }
        }, 1);
    }

    public Ticker toggle() {
        enabled=!enabled;
        if(enabled) {
            Count();
        }
        return this;
    }

    public Ticker setEnabled(boolean bool) {
        enabled=bool;
        if(enabled) {
            Count();
        }
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
