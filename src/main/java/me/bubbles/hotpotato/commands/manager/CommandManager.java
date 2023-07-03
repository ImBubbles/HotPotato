package me.bubbles.hotpotato.commands.manager;

import me.bubbles.hotpotato.HotPotato;
import me.bubbles.hotpotato.commands.BaseCommand;

import java.util.ArrayList;

public class CommandManager {
    private HotPotato plugin;
    private ArrayList<Command> commandsList = new ArrayList<>();

    public CommandManager(HotPotato plugin) {
        this.plugin=plugin;
        registerCommands();
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(command);
            commandsList.add(command);
        }
    }

    public void registerCommands() {
        addCommand(new BaseCommand(plugin));
    }

    public void onTick() {
        
    }

}
