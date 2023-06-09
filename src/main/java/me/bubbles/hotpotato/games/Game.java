package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.games.rounds.Round;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.users.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private Status status;
    private Map map;
    private List<User> userList = new ArrayList<>();

    private List<User> alive = new ArrayList<>();

    private List<User> dead = new ArrayList<>();
    private Round round;
    private Timer timer;
    private HotPotato plugin;

    public Game(HotPotato plugin, Map map) {
        this.status=Status.FILLING;
        this.map=map;
        this.timer=new Timer(plugin.getConfig().getInt("starting-time")*20);
    }

    public Map getMap() {
        return map;
    }

    public Status getStatus() {
        return status;
    }

    public void onTick() {
        if(status.equals(Status.FILLING))
            return;
        if(status.equals(Status.STARTING)) {
            starting();
            timer.onTick();
        }
        if(status.equals(Status.BUSY)) {
            round.onTick();
        }
    }

    public void starting() {
        List<Integer> announceTimes = Arrays.asList(1, 2, 3, 4, 5, 10, 15, 30, 60, 120);
        if(announceTimes.contains(timer.getTicks()/20)) {
            broadcast("%prefix% %primary%Game will start in %secondary%"+timer.getTicks()/20+"%primary% seconds.");
        }
        if(timer.isOver()) {
            onStart();
            status=Status.BUSY;
        }
    }

    public void tag(User tagger, User tagged) {
        tagger.getPlayer().getInventory().clear();
        for(int m=36;m<=44;m++) {
            tagged.getPlayer().getInventory().setItem(m,new ItemStack(Material.POTATO));
        }
        broadcast("%prefix% %secondary%"+tagger.getPlayer().getName()+"%primary% has tagged %secondary%"+tagged.getPlayer().getName()+"%primary%.");
    }

    public void onStart() {
        round=new Round(this,map,0);
    }

    public void nextRound(Map map, int round) {
        this.round=new Round(this,map,round);
    }

    public void end(User winner) {
        broadcast("%prefix% %primary%The match has concluded, the winner is %secondary%"+winner.getPlayer().getName()+"%primary%.");
        winner.setWins(winner.getWins()+1);
        winner.sendMessage("%prefix% %primary%You won the match!");
        for(User user : userList) {
            user.leave();
            showAll(user);
        }
    }

    public void end() {
        broadcast("%prefix% %primary%The match has been stopped.");
        for(User user : userList) {
            user.leave();
            showAll(user);
        }
    }

    public void showAll(User user) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            user.getPlayer().showPlayer(plugin,p);
        }
    }

    public void broadcast(String message) {
        for(User user : userList) {
            user.sendMessage(message);
        }
    }

    // GAME LOGIC
    public void addUser(User user) {
        userList.add(user);
        user.getPlayer().teleport(map.getLobby());
        if(status.equals(Status.FILLING)) {
            broadcast("%prefix% %secondary%"+user.getPlayer().getName()+"%primary% has joined the game.%secondary% ("+userList.size()+"/"+map.getMaxPlayers()+")");
            return;
        }
        if(userList.size()==map.getMaxPlayers()) {
            status=Status.STARTING;
            return;
        }
        broadcast("%prefix% %secondary%"+user.getPlayer().getName()+"%primary% has joined the game.");
        user.getPlayer().setGameMode(GameMode.ADVENTURE);

        for(Player p : Bukkit.getOnlinePlayers()) {
            User pUser = plugin.getUserManager().getUser(p);
            if(!pUser.inGame()) {
                return;
            }
            if(pUser.getGame().equals(this)) {
                return;
            }
            user.getPlayer().hidePlayer(plugin,p);
        }
    }

    public void removeUser(User user) {
        userList.remove(user);
        if(status.equals(Status.STARTING)) {
            status=Status.FILLING;
            broadcast("%prefix% %secondary%"+user.getPlayer().getName()+"%primary% has left the game.%secondary% "+userList.size()+"/"+map.getMaxPlayers()+")");
        }
        broadcast("%prefix% %secondary%"+user.getPlayer().getName()+"%primary% has left the game.");
        user.getPlayer().setGameMode(GameMode.SURVIVAL);
    }

    public List<User> getUsers() {
        return userList;
    }

    public List<User> getAlive() {
        return alive;
    }

    public List<User> getDead() {
        return dead;
    }

    // SETTERS

    public void setAlive(List<User> alive) {
        this.alive=alive;
    }

    public void setDead(List<User> dead) {
        this.alive=dead;
    }

    public enum Status {
        FILLING(1),
        STARTING(2),
        BUSY(3),
        ENDING(4);

        private int value;

        Status(int value) {
            this.value=value;
        }

        public int getNumerical() {
            return value;
        }

    }

}
