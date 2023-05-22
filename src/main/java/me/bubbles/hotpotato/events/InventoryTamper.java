package me.bubbles.hotpotato.events;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.events.manager.Event;
import me.bubbles.hotpotato.users.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InventoryTamper extends Event {

    public InventoryTamper(HotPotato plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        User user = plugin.getUserManager().getUser(e.getPlayer());
        if(!user.inGame()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        User user = plugin.getUserManager().getUser(e.getPlayer());
        if(!user.inGame()) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(InventoryClickEvent e) {
        if(!(e.getWhoClicked() instanceof Player)) {
            return;
        }

        User user = plugin.getUserManager().getUser((Player) e.getWhoClicked());
        if(!user.inGame()) {
            return;
        }
        e.setCancelled(true);
    }

}
