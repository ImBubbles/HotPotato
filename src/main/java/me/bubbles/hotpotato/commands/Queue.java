package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.messages.Messages;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Queue extends Argument {

    public Queue(HotPotato plugin, int index) {
        super(plugin, "queue", "queue <map>", index);
        setPermission("queue");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {

            // IF PLAYER HAS PERMISSION

            Player p = (Player) sender;
            if(!hasPermission(p))
                return;

            // IF IN GAME

            User user = plugin.getUserManager().getUser(p);
            if(user.inGame()) {
                user.sendMessage("%prefix% %primary%You are already in a game!");
                return;
            }

            // IF MAP NOT CHOSEN

            if(args.length>=index) {
                user.sendMessage("%prefix% %primary%You are now in a lobby!");
                user.queue();
                return;
            }

            // IF MAP CHOSEN + PERM

            if(!p.hasPermission("hotpotato.queue.map")) {
                user.sendMessage(Messages.Message.NO_PERMS);
                return;
            }

            if(plugin.getMapManager().mapFromString(args[index])==null) {
                user.sendMessage("%prefix% %primary%Unknown map!");
                return;
            }

            Map map = plugin.getMapManager().mapFromString(args[index]);

            user.sendMessage("%prefix% %primary%You are now in a lobby for map %secondary%"+map.getName()+"%primary%.");
            user.queue(map);

        }
    }

}
