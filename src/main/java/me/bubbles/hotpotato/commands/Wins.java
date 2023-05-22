package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.messages.Messages;
import me.bubbles.hotpotato.users.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Wins extends Argument {

    public Wins(HotPotato plugin, int index) {
        super(plugin, "wins", "wins", index);
        setPermission("wins");
    }

    @Override
    public void run(CommandSender sender, String[] args) {

        User user = plugin.getUserManager().getUser((Player) sender);

        if(!hasPermission(user.getPlayer()))
            return;

        if(args.length==index) {
            user.sendMessage("%prefix% %primary%You have %secondary%"+user.getWins()+"%primary% wins.");
            return;
        }

        if(!user.getPlayer().hasPermission("hotpotato.wins.other")) {
            user.sendMessage(Messages.Message.NO_PERMS);
            return;
        }

        try {
            User user2 = plugin.getUserManager().getUser(Bukkit.getPlayer(args[index]));
            user.sendMessage("%prefix% %secondary%"+user2.getPlayer().getName()+"%primary% has %secondary%"+user2.getWins()+"%primary% wins.");
        } catch (Exception e) {
            user.sendMessage("%prefix% %primary%YCould not find user.");
        }

    }

}
