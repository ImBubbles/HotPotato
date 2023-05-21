package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.maps.MapManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {

    private HotPotato plugin;
    private List<Game> games = new ArrayList<>();

    public GameManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public List<Game> getGames() {
        return games;
    }

    public Game createGame(Map map) {
        Game game = new Game(plugin,map);
        games.add(game);
        return game;
    }

    public Game createGame() {
        List<Map> maps = plugin.getMapManager().getMaps();

        int index = ((int) (Math.random() * (maps.size() - 1)));

        return createGame(maps.get(index));
    }

    public void onTick() {
        for(Game game : games) {
            game.onTick();
        }
    }

}
