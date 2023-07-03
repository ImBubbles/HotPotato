package me.bubbles.hotpotato.configs;

import me.bubbles.hotpotato.HotPotato;

import java.util.HashSet;

public class ConfigManager {

    private HashSet<Config> configList = new HashSet<>();
    private HotPotato plugin;

    public ConfigManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public void addConfig(String... names) {
        for(String name : names) {
            configList.add(new Config(plugin, name));
        }
    }

    public Config getConfig(String name) {
        for(Config config : configList) {
            if(config.getName().equalsIgnoreCase(name)) {
                return config;
            }
        }
        return null;
    }

    public void reloadAll() {
        configList.forEach(Config::reload);
        plugin.reloadConfig();
    }

    public void saveAll() {
        configList.forEach(Config::save);
    }

}
