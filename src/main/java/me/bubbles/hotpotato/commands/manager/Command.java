package me.bubbles.hotpotato.commands.manager;

import me.bubbles.hotpotato.HotPotato;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    public HotPotato plugin;
    private String command;
    private String permission;
    public String no_perms;

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
        String node = "hotpotato." + permission;
        this.permission=node;
        this.no_perms="&cYou do not have permission to do that: &4%node%";
        this.no_perms=no_perms.replace("%node%",node);
    }

    public void onTick() {

    }

}
