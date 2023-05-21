package me.bubbles.hotpotato.users;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private HotPotato plugin;
    private List<User> userList = new ArrayList<>();

    public UserManager(HotPotato plugin) {
        this.plugin=plugin;
    }

    public User addPlayer(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return null;
        }
        userList.add(new User(p,plugin));
        return getUser(p);
    }

    public User getUser(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return user;
        }
        return null;
    }

}
