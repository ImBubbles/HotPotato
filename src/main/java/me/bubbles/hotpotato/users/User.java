package me.bubbles.hotpotato.users;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.games.Game;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.maps.MapMaker;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class User {

    private Player player;
    private Game game;
    private HotPotato plugin;
    private ConfigurationSection data;
    private ItemStack[] inventoryContents;
    private MapMaker mapMaker;

    public User(Player player, HotPotato plugin) {
        this.player=player;
        this.plugin=plugin;
        this.inventoryContents=player.getInventory().getContents();

        FileConfiguration data = plugin.getConfigManager().getConfig("data.yml").getFileConfiguration();
        ConfigurationSection configurationSection = data.getConfigurationSection("players");

        try {
            if(!(configurationSection
                    .getKeys(false)
                    .contains(player.getUniqueId().toString()))) {
                configurationSection.set(player.getUniqueId().toString()+".wins",0);
            }
        } catch (NullPointerException e) {
            data.set("players."+player.getUniqueId()+".wins",0);
        }

        plugin.getConfigManager().saveAll();

        this.data=data.getConfigurationSection("players").getConfigurationSection(player.getUniqueId().toString());

    }

    public void sendMessage(Messages.Message message) {
        String newStr = ChatColor.translateAlternateColorCodes('&',message.getStr()
                .replace("%name%",player.getName())
                .replace("%prefix%",Messages.Message.PREFIX.getStr())
                .replace("%wins%",String.valueOf(getWins()))
                .replace("%primary%", Messages.Message.PRIMARY.getStr())
                .replace("%secondary%", Messages.Message.SECONDARY.getStr()));
        player.sendMessage(newStr);
    }

    public void sendMessage(String message) {
        String newStr = ChatColor.translateAlternateColorCodes('&',message
                .replace("%name%",player.getName())
                .replace("%prefix%",Messages.Message.PREFIX.getStr())
                .replace("%wins%",String.valueOf(getWins()))
                .replace("%primary%", Messages.Message.PRIMARY.getStr())
                .replace("%secondary%", Messages.Message.SECONDARY.getStr()));
        player.sendMessage(newStr);
    }

    // GETTERS

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public int getWins() {
        return data.getInt("wins");
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public MapMaker getMapMaker() {
        return mapMaker;
    }

    // SETTERS

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSelectedMap(MapMaker mapMaker) {
        this.mapMaker=mapMaker;
    }

    public void updateInventoryContents() {
        this.inventoryContents=player.getInventory().getContents();
    }

    public void setWins(int wins) {
        data.set("wins",wins);
    }

    // QUEUE

    public Map queue() {

        if(inGame())
            return null;

        updateInventoryContents();
        getPlayer().getInventory().clear();

        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getStatus()==Game.Status.FILLING) {
                game.addUser(this);
                this.game=game;
                return game.getMap();
            }
        }
        Game game = plugin.getGameManager().createGame();
        game.addUser(this);

        return game.getMap();

    }

    public Map queue(Map map) {
        if(inGame())
            return null;

        updateInventoryContents();
        getPlayer().getInventory().clear();

        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getStatus()==Game.Status.FILLING&&game.getMap().equals(map)) {
                game.addUser(this);
                this.game=game;
                return game.getMap();
            }
        }

        Game game = plugin.getGameManager().createGame(map);
        game.addUser(this);

        return game.getMap();
    }

    public boolean leave() {
        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getUsers().contains(this)) {
                game.removeUser(this);
                this.game=null;
                player.getInventory().setContents(inventoryContents);
                return true;
            }
        }

        return false;
    }

    public boolean inGame() {
        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getUsers().contains(this)) {
                return true;
            }
        }
        return false;
    }

}
