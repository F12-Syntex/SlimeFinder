
package com.slimefinder.commands;

import org.bukkit.entity.Player;

import com.slimefinder.main.SlimeFinder;
import com.slimefinder.utils.MessageUtils;

public class Help extends SubCommand {

	private SlimeFinder plugin = SlimeFinder.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {
    	MessageUtils.sendHelp(player);
    }

    @Override

    public String name() {
        return plugin.CommandManager.help;
    }

    @Override
    public String info() {
        return "displays the help command";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.DEFAULT;	
	}
	

}