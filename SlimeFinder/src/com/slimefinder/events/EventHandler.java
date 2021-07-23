package com.slimefinder.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.Plugin;

import com.slimefinder.main.SlimeFinder;

public class EventHandler {

    public static List<SubEvent> events = new ArrayList<SubEvent>();
	
    private Plugin plugin = SlimeFinder.instance;
    
	public void setup() {
		events.forEach(i -> plugin.getServer().getPluginManager().registerEvents(i, plugin));
	}
	
}
