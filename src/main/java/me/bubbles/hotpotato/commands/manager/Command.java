package me.bubbles.hotpotato.commands.manager;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements CommandExecutor {

    public HotPotato plugin;
    private String command;
    private String permission;
    public String no_perms;
    private List<Argument> arguments = new ArrayList<>();
    public final int INDEX=0;

    public Command(String command, HotPotato plugin) {
        this.command=command;
        this.plugin=plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean hasPermission(Player player) {
        if(permission==null)
            return true;
        if(!player.hasPermission(permission)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',no_perms));
            return false;
        }else{
            return true;
        }
    }

    public void setPermission(String permission) {
        String node = plugin.getPluginName() + "." + permission;
        this.permission=node;
        this.no_perms="&cYou do not have permission to do that: &4%node%";
        this.no_perms=no_perms.replace("%node%",node);
    }

    public String getArgsMessage() {

        String prefix = Messages.Message.PREFIX.getStr();
        String pri = Messages.Message.PRIMARY.getStr(); // primary color
        String sec = Messages.Message.SECONDARY.getStr(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + " Commands:";
        stringBuilder.append(topLine);

        for(Argument arg : getArguments()) {
            String command = "\n" + pri + "/" + getCommand() + sec + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public List<Argument> getArguments() {
        return arguments;
    }

}
