package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Map extends Argument {

    public Map(HotPotato plugin,int index) {
        super(plugin,"map","map",index);
        setPermission("admin");
        addArguments(
                new Select(plugin,index,this),
                new Create(plugin,index,this),
                new AddSpawn(plugin,index,this),
                new SetRounds(plugin,index,this),
                new SetLobby(plugin,index,this),
                new SetStartTime(plugin,index,this),
                new SetEndTime(plugin,index,this));
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

        if(!(args.length==index-1)) { // IF PLAYER SENDS NO ARGUMENTS
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgsMessage()));
            return;
        }

        for(Argument argument : getArguments()) { // IF PLAYER SENDS ARGUMENTS
            if(argument.getArg().equals(args[index])) {
                argument.run(sender, args);
            }
        }
    }

}
