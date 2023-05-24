package me.bubbles.hotpotato.events;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.events.manager.Event;
import me.bubbles.hotpotato.users.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamage extends Event {

    public PlayerDamage(HotPotato plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player&&e.getDamager() instanceof Player) {

            User damager = plugin.getUserManager().getUser((Player) e.getDamager());
            User damaged = plugin.getUserManager().getUser((Player) e.getEntity());

            if(!(damager.inGame()&&damaged.inGame())) {
                return;
            }

            if(damager.getPlayer().getInventory().contains(Material.POTATO)&&(!damaged.getPlayer().getInventory().contains(Material.POTATO))) {
                damaged.getGame().tag(damager,damaged);
            }
        }
    }

}
