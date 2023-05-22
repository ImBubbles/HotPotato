package me.bubbles.hotpotato.maps;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.List;

public class Map {

    private String name;
    private World world;
    private int maxPlayers;
    private int endTime;
    private int startTime;
    private int rounds;
    private int decay;
    private List<Location> spawnPoints;
    private Location lobby;

    public Map(String name, World world, int maxPlayers, int startTime, int endTime, int rounds, List<Location> spawnPoints, Location lobby) {
        this.name=name;
        this.world=world;
        this.maxPlayers=maxPlayers;
        this.spawnPoints=spawnPoints;
        this.startTime=startTime;
        this.endTime=endTime;
        this.decay=maxPlayers/rounds;
        this.lobby=lobby;
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getDecay() {
        return decay;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getRounds() {
        return rounds;
    }

    public List<Location> getSpawnPoints() {
        return spawnPoints;
    }
    public Location getLobby() {
        return lobby;
    }

}
