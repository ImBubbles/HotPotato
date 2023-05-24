package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.maps.Map;

import java.util.HashSet;
import java.util.List;

public class GameManager {

    private HotPotato plugin;
    private HashSet<Game> games = new HashSet<>();

    public GameManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public HashSet<Game> getGames() {
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
