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

    public void loadMaps() {

        this.mapsList=new ArrayList<>();

        for(String key : maps.getConfigurationSection("maps").getKeys(false)) {

            ConfigurationSection selection = maps.getConfigurationSection("maps."+key);

            String name=selection.getString("name");
            World world=plugin.getServer().getWorld(
                    selection.getString("world")
            );
            int maxPlayers=selection.getInt("maxplayers");

            List<Location> spawnPoints = new ArrayList<>();
            for(String str : selection.getStringList("spawnpoints")) {
                spawnPoints.add(locationFromString(world,str));
            }

            Map map = new Map(name,world,maxPlayers,spawnPoints);
            mapsList.add(map);
        }

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

}
