package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Argument;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetMaxPlayers extends Argument {

    public SetMaxPlayers(HotPotato plugin, int index, Argument base) {
        super(plugin, "setmaxplayers", "SetMaxPlayers <#>", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            User user = plugin.getUserManager().getUser((Player) sender);

            if(args.length==index-1) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            int players;

            try {
                players=Integer.parseInt(args[index]);
            } catch(NullPointerException e) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(user.getMapMaker()==null) {
                user.sendMessage("%prefix% %primary%Select a map first!");
            }

            user.getMapMaker().setMaxPlayers(players);
            user.sendMessage("%prefix% %primary%Max players set to %secondary%"+players+"%primary%.");

        }
    }

}
