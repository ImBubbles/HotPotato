package me.bubbles.hotpotato.configs;

import me.bubbles.hotpotato.HotPotato;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    private List<Config> configList = new ArrayList<>();
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
            if(config.getName().equals(name)) {
                return config;
            }
        }
        return null;
    }

    public void reloadAll() {
        int index=0;
        for(Config config : configList) {
            configList.set(index,new Config(new File(config.getName())));
            index++;
        }
        plugin.reloadConfig();
    }

    public void saveAll() {
        for(Config config : configList) {
            try {
                config.getFileConfiguration().save(config.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
