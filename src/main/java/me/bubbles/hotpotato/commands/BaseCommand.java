package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Argument;
import me.bubbles.hotpotato.commands.manager.Command;
import me.bubbles.hotpotato.commands.map.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommand extends Command {

    public BaseCommand(HotPotato plugin) {
        super("hotpotato", plugin);
        addArguments(new Reload(plugin,INDEX),
                new Map(plugin,INDEX),
                new Queue(plugin,INDEX),
                new Leave(plugin,INDEX),
                new Wins(plugin,INDEX)
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) {
                if(argument.getArg().equalsIgnoreCase(args[INDEX])) {
                    argument.run(sender, args);
                    return true;
                }
            }
        }

        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', getArgsMessage()));
        }

        return true;
    }

}
