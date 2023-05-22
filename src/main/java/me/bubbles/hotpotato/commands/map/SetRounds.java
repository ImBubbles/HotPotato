package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRounds extends Argument {

    public SetRounds(HotPotato plugin, int index, Argument base) {
        super(plugin, "setrounds", "setrounds <rounds>", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            User user = plugin.getUserManager().getUser((Player) sender);

            if(!(args.length==index-1)) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            int rounds;

            try {
                rounds=Integer.parseInt(args[index]);
            } catch(NullPointerException e) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(user.getMapMaker()==null) {
                user.sendMessage("%prefix% %primary%Select a map first!");
            }

            user.getMapMaker().setRounds(rounds);
            user.sendMessage("%prefix% %primary%Total rounds set to %secondary%"+rounds+"%primary.");

        }
    }

}
