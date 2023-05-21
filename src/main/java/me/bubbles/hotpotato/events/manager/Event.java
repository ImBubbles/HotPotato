package me.bubbles.hotpotato.events.manager;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.event.Listener;

public class Event implements Listener {

    public HotPotato plugin;

    public Event(HotPotato plugin) {
        this.plugin=plugin;
    }

}
