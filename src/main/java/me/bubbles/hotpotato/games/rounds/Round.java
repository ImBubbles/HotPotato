package me.bubbles.hotpotato.games.rounds;

import me.bubbles.hotpotato.games.Game;
import me.bubbles.hotpotato.games.Timer;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.users.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Round {

    private Map map;
    private Game game;
    private List<User> alive;
    private List<User> dead;
    private int round;
    private Timer timer;

    public Round(Game game, Map map, int round, List<User> alive, List<User> dead) {
        this.map=map;
        this.game=game;
        this.round=round;
        this.timer=new Timer(getRoundTime()/20);
        this.alive=alive;
        this.dead=dead;
        game.broadcast("%prefix% %primary%There are %secondary%"+alive.size()+"%primary% players alive.");
    }

    public void onTick() {
        timer.onTick();
        if(timer.isOver()) {
            endRound();
        }
    }

    private void nextRound() {
        game.nextRound(map,round+1,alive,dead);
    }

    private void endRound() {
        for(User user : alive) {
            Player p = user.getPlayer();
            if(p.getInventory().contains(Material.POTATO)) {
                alive.remove(user);
                dead.add(user);
            }
        }
        game.broadcast("%prefix% %primary%There are %secondary%"+getPlayersToBeEliminated()+"%primary% players eliminated.");
        nextRound();
    }

    private int getRoundTime() {
        return map.getStartTime()-(((map.getEndTime()-map.getStartTime())/(map.getRounds()))*round);
    }

    private int getPlayersToBeEliminated() {
        int decay = map.getMaxPlayers()/map.getRounds();
        if(alive.size()/decay<1) {
            return 1;
        }
        return alive.size()/decay;
    }

}
