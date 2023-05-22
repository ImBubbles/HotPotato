package me.bubbles.hotpotato.commands.map;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.Argument;
import me.bubbles.hotpotato.maps.Map;
import me.bubbles.hotpotato.messages.Messages;
import me.bubbles.hotpotato.users.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class List extends Argument {

    public List(HotPotato plugin, int index, Argument base) {
        super(plugin, "list", "List", index, base);
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        User user = plugin.getUserManager().getUser((Player) sender);
        user.sendMessage(getMapsMessage());
    }

    public String getMapsMessage() {

        String prefix = Messages.Message.PREFIX.getStr(); // prefix
        String pri = Messages.Message.PRIMARY.getStr(); // primary color
        String sec = Messages.Message.SECONDARY.getStr(); // secondary color

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = prefix + pri + " Map List:";
        stringBuilder.append(topLine);

        for(String string : plugin.getMapManager().getAllMaps()) {
            String line = "\n" + pri + string;
            stringBuilder.append(line);
        }

        return stringBuilder.toString();

    }

}
