package com.slimefinder.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.slimefinder.cooldown.CooldownUser;
import com.slimefinder.cooldown.Cooldowns;
import com.slimefinder.main.SlimeFinder;
import com.slimefinder.messages.ConfigData;
import com.slimefinder.tags.TagFactory;
import com.slimefinder.utils.MessageUtils;

public class CommandManager extends ConfigData implements CommandExecutor {

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();

    private SlimeFinder plugin;

    //Sub Commands
    public String main = "slime";
    public String help = "help";

    
    public void setup(SlimeFinder plugin) {
    	this.setPlugin(plugin);
    	plugin.getCommand(main).setExecutor(this);
        commands.add(new Help());
        commands.add(new Reload());
        commands.add(new SlimeInCurrentLocation());
        commands.add(new SlimeNearUser());
    }


    public ArrayList<SubCommand> getCommands(){
    	return commands;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (!(sender instanceof Player)) {

            sender.sendMessage(messages.INAVLID_ENTITY);

            return true;

        }

        Player player = (Player) sender;
        
    	try {

        if (command.getName().equalsIgnoreCase(main)) {

            if (args.length == 0) {
            	
            	CooldownUser user = SlimeFinder.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	SubCommand cmd = new SlimeInCurrentLocation();
            	
            	if(!player.hasPermission(cmd.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		        }
            	
            	
            	String key = cmd.name();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            	
            		cmd.onCommand(player, args);
            	  
                	user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
                return true;

            }

            SubCommand target = this.get(args[0]);

            if (target == null) {

                player.sendMessage(messages.INVALID_SYNTEX);

                return true;

            }
            
		    if(!player.hasPermission(target.permission())) {
		    		MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
		    		return true;
		    }

            ArrayList<String> arrayList = new ArrayList<String>();

            arrayList.addAll(Arrays.asList(args));

            arrayList.remove(0);
            
            try{
            	
            	CooldownUser user = SlimeFinder.getInstance().cooldownManager.getUser(player.getUniqueId());
            	
            	String key = args[0].trim();

            	int timer = user.getTime(key);
            	
            	if(timer <= 0 || player.hasPermission(permissions.BYPASS)) {
            		
            	    target.onCommand(player, args);
            	    
            	    user.reset(key);
                
            	}else {
                	
                	TagFactory tagHelper = TagFactory.instance(Cooldowns.instance().message);
                
                	tagHelper.setCooldown(timer);
                	
                	MessageUtils.sendMessage(player, tagHelper.parse());
                }
            	
            
            
            }catch (Exception e){
                player.sendMessage(messages.ERROR);
                e.printStackTrace();
            }

        }


    }catch(Throwable e) {
        player.sendMessage(messages.ERROR);
        e.printStackTrace();
    }

        return true;
    
    }



    private SubCommand get(String name) {

        Iterator<SubCommand> subcommands = commands.iterator();

        while (subcommands.hasNext()) {

            SubCommand sc = (SubCommand) subcommands.next();



            if (sc.name().equalsIgnoreCase(name)) {

                return sc;

            }



            String[] aliases;

            int length = (aliases = sc.aliases()).length;



            for (int var5 = 0; var5 < length; ++var5) {

                String alias = aliases[var5];

                if (name.equalsIgnoreCase(alias)) {

                    return sc;

                }

            }

        }

        return null;

    }


	public SlimeFinder getPlugin() {
		return plugin;
	}


	public void setPlugin(SlimeFinder plugin) {
		this.plugin = plugin;
	}

}