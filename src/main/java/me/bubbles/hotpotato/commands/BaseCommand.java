package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Command;
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
        addArguments(new Reload(plugin,index));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        for(Argument argument : arguments) {
            if(argument.getArg().equals(args[index])) {
                argument.run(sender, args);
                return true;
            }
        }
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgs()));
        }
        return true;
    }

    private String getArgs() {

        String prefix = plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration().getString("PREFIX");
        String pri = plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration().getString("COLOR_PRIMARY"); // primary color
        String sec = plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration().getString("COLOR_SECONDARY"); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + "Commands:";
        stringBuilder.append(topLine);
        stringBuilder.append("\n");

        for(Argument arg : arguments) {
            String command = pri + "/" + getCommand() + sec + arg.getDisplay();
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
