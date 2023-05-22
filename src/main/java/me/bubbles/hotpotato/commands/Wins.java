package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Wins extends Argument {

    public Wins(HotPotato plugin, int index) {
        super(plugin, "wins", "wins", index);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        User user = plugin.getUserManager().getUser((Player) sender);
        user.sendMessage("%prefix% %primary%You have %secondary%"+user.getWins()+"%primary%.");
    }

}
