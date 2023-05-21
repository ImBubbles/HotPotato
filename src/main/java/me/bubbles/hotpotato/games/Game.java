package me.bubbles.hotpotato.games;

import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.users.User;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Status status;
    private Map map;
    private List<User> userList = new ArrayList<>();
    private List<User> alive = new ArrayList<>();
    private List<User> dead = new ArrayList<>();

    public Game(Map map) {
        this.status=Status.FILLING;
        this.map=map;
    }

    public Map getMap() {
        return map;
    }

    public Status getStatus() {
        return status;
    }

    public void onTick() {

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

    public void broadcast(String message) {
        for(User user : userList) {
            user.getPlayer().sendMessage(message);
        }
    }

    // GAME LOGIC
    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser() {

    }

    public List<User> getUsers() {
        return userList;
    }

}
