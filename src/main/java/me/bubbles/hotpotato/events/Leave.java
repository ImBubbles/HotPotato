package me.bubbles.hotpotato.events;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.events.manager.Event;
import me.bubbles.hotpotato.users.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave(HotPotato plugin) {
        super(plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        User user = plugin.getUserManager().getUser(e.getPlayer());

        if(user.inGame()) {
            e.getPlayer().getInventory().setContents(user.getInventoryContents());
            user.getGame().removeUser(user);
        }

    }

}
