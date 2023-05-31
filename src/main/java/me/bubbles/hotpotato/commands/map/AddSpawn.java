package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Argument;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSpawn extends Argument {

    public AddSpawn(HotPotato plugin, int index, Argument base) {
        super(plugin, "addspawn", "AddSpawn", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            User user = plugin.getUserManager().getUser((Player) sender);

            if(!(args.length==index)) {
                user.sendMessage(getBase().getArgsMessage());
                return;
            }

            if(user.getMapMaker()==null) {
                user.sendMessage("%prefix% %primary%Select a map first!");
            }

            user.getMapMaker().addSpawnPoint(user.getPlayer().getLocation());
            user.sendMessage("%prefix% %primary%New spawn point set, go to maps.yml to delete any accidents.");

        }
    }

}
