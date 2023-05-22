package me.bubbles.hotpotato.events.manager;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.events.InventoryTamper;
import me.bubbles.hotpotato.events.Join;
import me.bubbles.hotpotato.events.Leave;
import me.bubbles.hotpotato.events.PlayerDamage;

public class EventManager {
    private HotPotato plugin;

    public EventManager(HotPotato plugin) {
        this.plugin=plugin;
        registerEvents();
    }

    public void addEvent(Event... events) {
        for(Event event : events) {
            plugin.getServer().getPluginManager().registerEvents(event,plugin);
        }
    }

    public void registerEvents() {
        addEvent(new Join(plugin),new Leave(plugin),new InventoryTamper(plugin), new PlayerDamage(plugin));
    }

}
