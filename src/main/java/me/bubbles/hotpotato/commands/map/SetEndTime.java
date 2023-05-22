package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetEndTime extends Argument {

    public SetEndTime(HotPotato plugin, int index, Argument base) {
        super(plugin, "setendtime", "SetEndTime <seconds>", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            User user = plugin.getUserManager().getUser((Player) sender);

            if(args.length==index-1) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            int time;

            try {
                time=Integer.parseInt(args[index]);
            } catch(NullPointerException e) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(user.getMapMaker()==null) {
                user.sendMessage("%prefix% %primary%Select a map first!");
            }

            user.getMapMaker().setStartTime(time);
            user.sendMessage("%prefix% %primary%Last round time set to %secondary%"+time+"%primary%.");

        }
    }

}
