package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Command;
import me.bubbles.hotpotato.commands.map.Map;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseCommand extends Command {

    private List<Argument> arguments = new ArrayList<>();
    private final int index=0;

    public BaseCommand(HotPotato plugin) {
        super("hotpotato", plugin);
        addArguments(new Reload(plugin,index),new Map(plugin,index),new Queue(plugin,index),new Leave(plugin,index));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : arguments) {
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args);
                    return true;
                }
            }
        }

        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgs()));
        }

        return true;
    }

    private String getArgs() {

        String prefix = Messages.Message.PREFIX.getStr();
        String pri = Messages.Message.PRIMARY.getStr(); // primary color
        String sec = Messages.Message.SECONDARY.getStr(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + " Commands:";
        stringBuilder.append(topLine);

        for(Argument arg : arguments) {
            String command = "\n" + pri + "/" + getCommand() + sec + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    private void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public List<Argument> getArguments() {
        return arguments;
    }

}
