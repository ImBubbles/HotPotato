package me.bubbles.hotpotato.maps;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MapManager {

    private HotPotato plugin;
    private FileConfiguration maps;
    private List<Map> mapsList = new ArrayList<>();

    public MapManager(HotPotato plugin) {
        this.plugin=plugin;
        this.maps=plugin.getConfigManager().getConfig("maps.yml").getFileConfiguration();
        loadMaps();
    }

    public boolean loadMaps() {

        this.mapsList=new ArrayList<>();

        try {
            maps.getConfigurationSection("maps").getKeys(false);
        }catch(NullPointerException e) {
            return false;
        }

        String[] required = {"name","world","maxplayers","rounds","starttime","endtime","spawnpoints"};

        for(String key : maps.getConfigurationSection("maps").getKeys(false)) {

            for(String requirement : required) {
                if(!maps.getConfigurationSection("maps."+key).getKeys(false).contains(requirement))
                    return false;
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

            Map map = new Map(name,world,maxPlayers,startTime,endTime,rounds,spawnPoints);
            mapsList.add(map);
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
        return mapsList;
    }

    public Map mapFromString(String name) {
        for(Map map : mapsList) {
            if(map.getName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }

}
