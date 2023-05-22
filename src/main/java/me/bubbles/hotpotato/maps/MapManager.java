package me.bubbles.hotpotato.maps;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private HotPotato plugin;
    private FileConfiguration maps;
    private List<Map> readyMapsList;
    private List<String> allMapsList;

    public MapManager(HotPotato plugin) {
        this.plugin=plugin;
        this.maps=plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration();
        loadMaps();
    }

    public boolean loadMaps() {

        this.readyMapsList=new ArrayList<>();
        this.allMapsList=new ArrayList<>();
        this.maps=plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration();

        String[] required = {"name","world","maxplayers","rounds","starttime","endtime","spawnpoints","lobby"};

        for(String key : maps.getConfigurationSection("maps").getKeys(false)) {

            for(String requirement : required) {
                if(!maps.getConfigurationSection("maps."+key).getKeys(false).contains(requirement)) {
                    System.out.println("Map "+key+" is not finished.");
                    allMapsList.add(key);
                    return false;
                }
            }

            ConfigurationSection selection = maps.getConfigurationSection("maps."+key);

            String name=selection.getString("name");
            World world=plugin.getServer().getWorld(
                    selection.getString("world")
            );
            int maxPlayers=selection.getInt("maxplayers");
            int startTime=selection.getInt("starttime");
            int endTime=selection.getInt("endtime");
            int rounds=selection.getInt("rounds");

            List<Location> spawnPoints = new ArrayList<>();
            for(String str : selection.getStringList("spawnpoints")) {
                spawnPoints.add(locationFromString(world,str));
            }

            Location lobby = locationFromString(world,selection.getString("lobby"));

            Map map = new Map(name,world,maxPlayers,startTime,endTime,rounds,spawnPoints,lobby);
            allMapsList.add(map.getName());
            readyMapsList.add(map);
        }

        return true;

    }

    private Location locationFromString(World world, String loc) {
        return new Location(
                world,
                Double.parseDouble(loc.split(",")[0]),
                Double.parseDouble(loc.split(",")[1]),
                Double.parseDouble(loc.split(",")[2]),
                Float.parseFloat(loc.split(",")[3]),
                Float.parseFloat(loc.split(",")[4])
                );
    }

    public List<Map> getMaps() {
        return readyMapsList;
    }

    public List<String> getAllMaps() {
        return allMapsList;
    }

    public Map mapFromString(String name) {
        for(Map map : readyMapsList) {
            if(map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

    public String anyMapFromString(String name) {
        for(String string : allMapsList) {
            if(string.equalsIgnoreCase(name)) {
                return string;
            }
        }
        return null;
    }

}
