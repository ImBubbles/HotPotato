package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.manager.Argument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends Argument {
    public Reload(HotPotato plugin,int index) {
        super(plugin,"reload","Reload",index);
        setPermission("reload");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(!hasPermission(p))
                return;
            plugin.getUserManager().getUser(p).sendMessage("%prefix% %primary%Config reloaded.");
        }
        plugin.reload();
    }

}
