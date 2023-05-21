package me.bubbles.hotpotato.commands;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.messages.Messages;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends Argument {
    public Reload(HotPotato plugin,int index) {
        super(plugin,"reload","reload",index);
        setPermission("reload");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            plugin.getUserManager().getUser(p).sendMessage("%prefix% %primary%Config reloaded.");
        }
        plugin.reloadConfig();
        plugin.setMessages(new Messages(plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration()));
        plugin.getMapManager().loadMaps();
    }

}
