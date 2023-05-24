package me.bubbles.hotpotato.users;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class UserManager {

    private HotPotato plugin;
    private HashSet<User> userList = new HashSet<>();

    public UserManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public User addPlayer(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return null;
        }
        User user = new User(p,plugin);
        userList.add(user);
        return user;
    }

    public User getUser(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return user;
        }
        return null;
    }

}
