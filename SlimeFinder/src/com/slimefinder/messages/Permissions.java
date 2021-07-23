package com.slimefinder.messages;

import org.bukkit.configuration.file.FileConfiguration;

import com.slimefinder.main.SlimeFinder;

public class Permissions {

	public static Permissions instance;
	
	public FileConfiguration permissions = SlimeFinder.instance.configManager.getConfig("permissions").configuration;
	
	public final String DEFAULT = permissions.getString("Permissions.default");
	public final String NEAR = permissions.getString("Permissions.near");
	public final String ADMIN = permissions.getString("Permissions.admin");
	public final String BYPASS = permissions.getString("Permissions.timer_bypass");
	
	public Permissions() {
		
	}
	
	public static Permissions instance() {
		return instance;
	}
	
	public static void reInitialize() {
		instance = new Permissions();
	}
	
	
}
