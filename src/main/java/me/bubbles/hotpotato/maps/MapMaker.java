package me.bubbles.hotpotato.maps;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MapMaker {

    private HotPotato plugin;
    private Map map;
    private String name;
    private ConfigurationSection mapConfig;

    public MapMaker(HotPotato plugin, Map map) {
        this(plugin,map.getName());
        this.map=map;
    }

    public MapMaker(HotPotato plugin, String name) {
        this.name=name;
        this.plugin=plugin;
        if(plugin.getMapManager().mapFromString(name)!=null) {
            this.map=plugin.getMapManager().mapFromString(name);
        }
        this.mapConfig=plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration().getConfigurationSection("maps."+name.toLowerCase());
    }

    public void createMap() {

        FileConfiguration data = plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration();
        ConfigurationSection configurationSection = data.getConfigurationSection("maps");

        try {
            if(!(configurationSection
                    .getKeys(false)
                    .contains(name))) {
                configurationSection.set(name.toLowerCase()+".name",name);
            }
        } catch (NullPointerException e) {
            data.set("maps."+name.toLowerCase()+".name",name);
        }

        plugin.getConfigManager().saveAll();

        mapConfig=plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration().getConfigurationSection("maps."+name.toLowerCase());

    }

    public void addSpawnPoint(Location loc) {
        if(mapConfig.getKeys(false).contains("spawnpoints")) {
            String str = loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getYaw()+","+loc.getPitch();
            List<String> result = mapConfig.getStringList("spawnpoints");
            result.add(str);
            mapConfig.set("spawnpoints",result);
            plugin.getConfigManager().saveAll();
            return;
        }
        String str = loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getYaw()+","+loc.getPitch();
        List<String> result = new ArrayList<>();
        result.add(str);
        mapConfig.set("spawnpoints",result);

        plugin.getConfigManager().saveAll();
    }

    public void setLobby(Location loc) {
        String str = loc.getX()+","+loc.getY()+","+loc.getZ()+","+loc.getYaw()+","+loc.getPitch();
        mapConfig.set("spawnpoints",str);
        plugin.getConfigManager().saveAll();
    }

    public void setMaxPlayers(int maxPlayer) {
        mapConfig.set("maxplayers",maxPlayer);
        plugin.getConfigManager().saveAll();
    }

    public void setEndTime(int endTime) {
        mapConfig.set("endtime",endTime);
        plugin.getConfigManager().saveAll();
    }

    public void setStartTime(int startTime) {
        mapConfig.set("starttime",startTime);
        plugin.getConfigManager().saveAll();
    }

    public void setRounds(int rounds) {
        mapConfig.set("rounds",rounds);
        plugin.getConfigManager().saveAll();
    }

    public void setWorld(World world) {
        mapConfig.set("world",world.getName());
        plugin.getConfigManager().saveAll();
    }

    public boolean exists() {
        return (plugin.getMapManager().mapFromString(name)!=null);
    }

}
