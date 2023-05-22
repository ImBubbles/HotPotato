package me.bubbles.hotpotato;

import me.bubbles.hotpotato.commands.manager.CommandManager;
import me.bubbles.hotpotato.events.manager.EventManager;
import me.bubbles.hotpotato.configs.ConfigManager;
import me.bubbles.hotpotato.games.GameManager;
import me.bubbles.hotpotato.maps.MapManager;
import me.bubbles.hotpotato.messages.Messages;
import me.bubbles.hotpotato.ticker.Ticker;
import me.bubbles.hotpotato.users.UserManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class HotPotato extends JavaPlugin {

    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private GameManager gameManager;
    private MapManager mapManager;
    private UserManager userManager;
    private Ticker ticker;
    private Messages messages;

    @Override
    public void onEnable() {
        // Configs
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "maps.yml",
                "messages.yml",
                "data.yml"
        );

        // Instance variables
        commandManager=new CommandManager(this);
        eventManager=new EventManager(this);
        gameManager=new GameManager(this);
        mapManager=new MapManager(this);
        userManager=new UserManager(this);

        messages=new Messages(configManager.getConfig("messages.yml").getFileConfiguration());

        // Ticker
        ticker=new Ticker(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // RELOAD CFG
    public void reload() {
        this.mapManager=new MapManager(this);
        getConfigManager().reloadAll();
        this.messages=(new Messages(getConfigManager().getConfig("messages.yml").getFileConfiguration()));
    }

    // TICKER
    public void onTick() {
        commandManager.onTick();
        gameManager.onTick();
    }

    // GETTERS
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public Ticker getTicker() {
        return ticker;
    }

}
