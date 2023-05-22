package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave extends Argument{

    public Leave(HotPotato plugin, int index) {
        super(plugin, "leave", "leave", index);
        setPermission("leave");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {

            // IF PLAYER HAS PERMISSION

            Player p = (Player) sender;
            if(!hasPermission(p))
                return;

            User user = plugin.getUserManager().getUser(p);
            if(!user.inGame()) {
                user.sendMessage("%prefix% %primary%You're not in a game!");
                return;
            }

            user.leave();
            user.sendMessage("%prefix% %primary%You have left the game.");
        }
    }

}
