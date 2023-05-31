package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Argument;
import me.bubbles.hotpotato.maps.MapMaker;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Select extends Argument {

    public Select(HotPotato plugin, int index, Argument base) {
        super(plugin, "select", "Select <name>", index,base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            User user = plugin.getUserManager().getUser((Player) sender);
            if(args.length==index) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(plugin.getMapManager().anyMapFromString(args[index])==null) {
                user.sendMessage("%prefix% %primary%Unknown map!");
                return;
            }

            String map = plugin.getMapManager().anyMapFromString(args[index]);

            user.setSelectedMap(new MapMaker(plugin,map));
            user.sendMessage("%prefix% %primary%Selected map: %secondary%"+map.toLowerCase());

        }
    }

}
