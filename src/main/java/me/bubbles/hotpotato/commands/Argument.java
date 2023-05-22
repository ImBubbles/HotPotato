package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Argument {

    public HotPotato plugin;
    public int index;
    private List<Argument> arguments = new ArrayList<>();
    private String arg;
    private String display;
    private String permission;
    private Argument base;

    public Argument(HotPotato plugin, String arg, String display, int index) {
        this.plugin=plugin;
        this.index=index+1;
        this.arg=arg;
        this.display=display;
    }

    public Argument(HotPotato plugin, String arg, String display, int index, Argument base) {
        this(plugin,arg,display,index);
        this.base=base;
    }

    public void run(CommandSender sender, String[] args) {

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public String getArg() {
        return arg;
    }
    public String getDisplay() {
        return display;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public void setPermission(String permission) {
        String node = "hotpotato." + permission;
        this.permission=node;
    }

    public boolean hasPermission(Player player) {
        if(permission==null)
            return true;
        if(!player.hasPermission(permission)) {
            plugin.getUserManager().getUser(player).sendMessage(Messages.Message.NO_PERMS);
            return false;
        }else{
            return true;
        }
    }

    public String getArgsMessage() {

        String prefix = Messages.Message.PREFIX.getStr(); // prefix
        String pri = Messages.Message.PRIMARY.getStr(); // primary color
        String sec = Messages.Message.SECONDARY.getStr(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + arg;
        stringBuilder.append(topLine);

        for(Argument argument : arguments) {
            String command = "\n" + pri + "/" + argument.getArg() + sec + argument.getDisplay();
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public Argument getBase() {
        return base;
    }
}
