package me.bubbles.hotpotato.users;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.games.Game;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class User {

    private Player player;
    private Game game;
    private HotPotato plugin;
    private ConfigurationSection data;
    private Map selectedMap;

    public User(Player player, HotPotato plugin) {
        this.player=player;
        this.plugin=plugin;

        FileConfiguration data = plugin.getConfigManager().getConfig("data.yml").getFileConfiguration();
        ConfigurationSection configurationSection = data.getConfigurationSection("players");

        if(!(configurationSection
                .getKeys(false)
                .contains(player.getUniqueId().toString()))) {
            configurationSection.set(player.getUniqueId().toString()+".wins",0);
        }

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

    // SETTERS

    public void setGame(Game game) {
        this.game = game;
    }

    public void setSelectedMap(Map map) {
        this.selectedMap=map;
    }

    // QUEUE

    public boolean queue() {

        if(inGame())
            return false;

        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getStatus()==Game.Status.FILLING) {
                game.addUser(this);
                this.game=game;
                return true;
            }
        }
        Game game = plugin.getGameManager().createGame();
        game.addUser(this);

        return true;

    }

    public boolean queue(Map map) {
        if(inGame())
            return false;

        for(Game game : plugin.getGameManager().getGames()) {
            if(game.getStatus()==Game.Status.FILLING&&game.getMap().equals(map)) {
                game.addUser(this);
                this.game=game;
                return true;
            }
        }
        Game game = plugin.getGameManager().createGame(map);
        game.addUser(this);
        return true;
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
