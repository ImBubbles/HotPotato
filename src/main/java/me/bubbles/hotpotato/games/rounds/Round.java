package me.bubbles.hotpotato.games.rounds;

import me.bubbles.hotpotato.games.Game;
import me.bubbles.hotpotato.games.Timer;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.users.User;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
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

        // Teleport

        int index=0;
        for(User user : alive) {
            user.getPlayer().teleport(map.getSpawnPoints().get(index));
            index=clamp(index+1,map.getSpawnPoints().size()-1,0);
        }

        // Get players to give potato

        List<Integer> players=new ArrayList<>();
        for(int i=0; i<getPlayersToBeEliminated(); i++) {
            if(players.isEmpty()) {
                players.set(i,getRandomNumber(0,alive.size()));
                return;
            }
            int rng = getRandomNumber(0,alive.size());
            while(players.contains(rng)) {
                players.set(i,getRandomNumber(0,alive.size()));
            }
        }

        // Give the potato

        for(int i=0; i<players.size(); i++) { // set whole hotbar to POTATO
            for(int m=36;m<=44;m++) {
                alive.get(i).getPlayer().getInventory().setItem(m,new ItemStack(Material.POTATO));
            }
        }

        // Make all alive adventure

        for(User user : alive) {
            user.getPlayer().setGameMode(GameMode.ADVENTURE);
        }

        // Make all dead spectator

        for(User user : dead) {
            user.getPlayer().setGameMode(GameMode.SPECTATOR);
        }

    }

    public void onTick() {
        List<Integer> announceTimes = Arrays.asList(1, 2, 3, 4, 5, 10, 15, 30, 60, 120);
        if(announceTimes.contains(timer.getTicks()/20)) {
            game.broadcast("%prefix% %primary%Round will end in %secondary%"+timer.getTicks()/20+"%primary% seconds!");
        }
        timer.onTick();
        if(timer.isOver()) {
            endRound();
        }
    }

    private void endRound() {
        for(User user : alive) {
            Player p = user.getPlayer();
            if(p.getInventory().contains(Material.POTATO)) {
                p.getInventory().clear();
                alive.remove(user);
                dead.add(user);
            }
        }

        game.broadcast("%prefix% %primary%There are %secondary%"+getPlayersToBeEliminated()+"%primary% players eliminated.");
        if(round<map.getRounds()) {
            game.nextRound(map,round+1,alive,dead);
        }else{
            game.end(alive.get(0));
        }

    }

    private int getRoundTime() {
        return map.getStartTime()-(((map.getEndTime()-map.getStartTime())/(map.getRounds()))*round);
    }

    private int getPlayersToBeEliminated() {
        int decay = map.getMaxPlayers()/map.getRounds();
        return Math.max(alive.size() / decay, 1);
    }

    private int clamp(int now, int max, int min) {
        if(now>max)
            return min;
        if(now<min)
            return max;
        return now;
    }

    private int getRandomNumber(int min, int max) {
        return ((int) (Math.random() * (max - min + 1)+min));
    }

}
