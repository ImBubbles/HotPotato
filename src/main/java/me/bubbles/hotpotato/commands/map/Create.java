package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.maps.MapMaker;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create extends Argument {

    public Create(HotPotato plugin, int index, Argument base) {
        super(plugin, "create", "Create <name>", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {

            User user = plugin.getUserManager().getUser((Player) sender);

            if(args.length==index) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(plugin.getMapManager().mapFromString(args[index])!=null) {
                user.sendMessage("%prefix% %primary%Map already exists!");
                return;
            }

            MapMaker mapMaker = new MapMaker(plugin,args[index]);
            mapMaker.createMap();
            mapMaker.setWorld(user.getPlayer().getWorld());

            user.setSelectedMap(mapMaker);

            user.sendMessage("%prefix% %primary%Map created, map will be unplayable until all variables are set.");

        }
    }
}
