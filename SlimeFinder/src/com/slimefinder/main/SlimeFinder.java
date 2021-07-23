package com.slimefinder.main;
import java.io.File;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.slimefinder.config.ConfigManager;
import com.slimefinder.cooldown.CooldownManager;
import com.slimefinder.cooldown.CooldownTick;
import com.slimefinder.cooldown.Cooldowns;
import com.slimefinder.events.EventHandler;
import com.slimefinder.messages.GenericMessages;
import com.slimefinder.messages.Permissions;


public class SlimeFinder extends JavaPlugin implements Listener{


    public static SlimeFinder instance;
    public com.slimefinder.commands.CommandManager CommandManager;
    public ConfigManager configManager;
    public EventHandler eventHandler;
    public CooldownManager cooldownManager;
    public CooldownTick cooldownTick;
	public static File ParentFolder;
	
	@Override
	public void onEnable(){
		
		ParentFolder = getDataFolder();
	    instance = this;
		
	    configManager = new ConfigManager();
	    configManager.setup(this);
	    
	    GenericMessages.reInitialize();
	    Permissions.reInitialize();
	    Cooldowns.reInitialize();
	    
	    this.cooldownManager = new CooldownManager();

	    this.cooldownTick = new CooldownTick();
	    this.cooldownTick.schedule();
	    
	    this.CommandManager = new com.slimefinder.commands.CommandManager();
	    this.CommandManager.setup(this);
	    
	    eventHandler = new com.slimefinder.events.EventHandler();
	    eventHandler.setup();
	    
	}
	
	
	@Override
	public void onDisable(){
		this.configManager.dispose();
		this.eventHandler = null;
		HandlerList.getRegisteredListeners(instance);
	}

	
	@org.bukkit.event.EventHandler
	public void onStart(ServerLoadEvent e) {
	    eventHandler = new EventHandler();
	    eventHandler.setup();
	}

	public void reload() {
		this.onDisable();
		this.onEnable();
	}
		
	

	public static SlimeFinder getInstance() {
		return instance;
	}
		
	
}
