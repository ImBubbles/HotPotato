package me.bubbles.hotpotato.events;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.events.manager.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Event {

    public Join(HotPotato plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getUserManager().addPlayer(e.getPlayer());
    }

}
