package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.HotPotato;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private HotPotato plugin;
    private List<Game> games = new ArrayList<>();

    public GameManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public List<Game> getGames() {
        return games;
    }

    public void onTick() {
        for(Game game : games) {
            game.onTick();
        }
    }

}
