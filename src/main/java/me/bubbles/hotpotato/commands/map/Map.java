package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Map extends Argument {

    public Map(HotPotato plugin,int index) {
        super(plugin,"map","Map [args]",index);
        setPermission("admin");
        addArguments(
                new Select(plugin,getIndex(),this),
                new Create(plugin,getIndex(),this),
                new AddSpawn(plugin,getIndex(),this),
                new SetRounds(plugin,getIndex(),this),
                new SetLobby(plugin,getIndex(),this),
                new SetStartTime(plugin,getIndex(),this),
                new SetEndTime(plugin,getIndex(),this),
                new List(plugin,getIndex(),this));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(!(sender instanceof Player)) {
            return;
        }

        Player p = (Player) sender;

        if(!hasPermission(p)) {
            plugin.getUserManager().getUser(p).sendMessage(Messages.Message.NO_PERMS);
        }

        if(args.length==index) { // IF PLAYER SENDS NO ARGUMENTS
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgsMessage()));
            return;
        }

        for(Argument argument : getArguments()) { // IF PLAYER SENDS ARGUMENTS
            if(argument.getArg().equalsIgnoreCase(args[index])) {
                argument.run(sender, args);
            }
        }
    }

}
