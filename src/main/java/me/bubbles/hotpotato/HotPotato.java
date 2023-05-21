package me.bubbles.hotpotato;

import me.bubbles.hotpotato.commands.manager.CommandManager;
import me.bubbles.hotpotato.events.manager.EventManager;
import me.bubbles.hotpotato.files.ConfigManager;
import me.bubbles.hotpotato.games.GameManager;
import me.bubbles.hotpotato.maps.MapManager;
import me.bubbles.hotpotato.messages.Messages;
import me.bubbles.hotpotato.ticker.Ticker;
import me.bubbles.hotpotato.users.UserManager;
import org.bukkit.plugin.java.JavaPlugin;

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
        // Instance variables
        commandManager=new CommandManager(this);
        eventManager=new EventManager(this);
        configManager=new ConfigManager(this);
        gameManager=new GameManager(this);
        mapManager=new MapManager(this);
        userManager=new UserManager(this);

        // Configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "maps.yml",
                "messages.yml",
                "data.yml"
        );

        messages=new Messages(configManager.getConfig("messages.yml").getFileConfiguration());

        // Ticker
        ticker=new Ticker(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // TICKER
    public void onSecond() {
        commandManager.onTick();
        gameManager.onTick();
    }

    // SETTERS
    public void setMessages(Messages messages) {
        this.messages=messages;
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
