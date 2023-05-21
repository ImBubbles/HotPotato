package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.games.rounds.Round;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.users.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private Status status;
    private Map map;
    private List<User> userList = new ArrayList<>();
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

    public void onStart() {
        round=new Round(this,map,0,userList,new ArrayList<>());
    }

    public void nextRound(Map map, int round,List<User> alive, List<User> dead) {
        this.round=new Round(this,map,round,alive,dead);
    }

    public void broadcast(String message) {
        for(User user : userList) {
            user.getPlayer().sendMessage(message);
        }
    }

    // GAME LOGIC
    public void addUser(User user) {
        userList.add(user);
        if(status.equals(Status.FILLING)) {
            status=Status.STARTING;
        }
    }

    public void removeUser(User user) {
        userList.remove(user);
        if(status.equals(Status.STARTING)) {
            status=Status.FILLING;
        }
    }

    public List<User> getUsers() {
        return userList;
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
