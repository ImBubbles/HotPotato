package me.bubbles.hotpotato.configs;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private String name;
    private FileConfiguration fileConfiguration;

    public Config(HotPotato plugin, String name) {
        this(new File(plugin.getDataFolder(),name));
    }

    public Config(File file) {

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        fileConfiguration.options().copyDefaults(true);
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            // poop
        }

        this.file=file;
        this.name=file.getName();

    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

}
