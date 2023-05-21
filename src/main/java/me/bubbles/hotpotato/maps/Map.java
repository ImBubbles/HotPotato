package me.bubbles.hotpotato.maps;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class Map {

    private String name;
    private World world;
    private int maxPlayers;
    private List<Location> spawnPoints;

    public Map(String name, World world, int maxPlayers, List<Location> spawnPoints) {
        this.name=name;
        this.world=world;
        this.maxPlayers=maxPlayers;
        this.spawnPoints=spawnPoints;
    }

}
