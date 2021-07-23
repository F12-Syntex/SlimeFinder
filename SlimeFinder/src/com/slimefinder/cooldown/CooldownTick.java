package com.slimefinder.cooldown;

import org.bukkit.scheduler.BukkitScheduler;

import com.slimefinder.main.SlimeFinder;

public class CooldownTick {
	
	private BukkitScheduler scheduler;

	public CooldownTick() {
		this.scheduler = SlimeFinder.getInstance().getServer().getScheduler();
	}

	public void schedule() {
		
        scheduler.scheduleSyncRepeatingTask(SlimeFinder.getInstance(), new Runnable() {
            @Override
            public void run() {
            	SlimeFinder.getInstance().cooldownManager.getUsers().forEach(i -> i.tick());
            }  	
            	
        }, 0L, 20L);

	}
	
	public void stop() {
		this.scheduler.cancelTasks(SlimeFinder.getInstance());
	}
	
}
