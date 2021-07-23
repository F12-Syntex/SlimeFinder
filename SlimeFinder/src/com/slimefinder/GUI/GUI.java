package com.slimefinder.GUI;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.slimefinder.main.SlimeFinder;
import com.slimefinder.messages.ConfigData;
import com.slimefinder.utils.MessageUtils;

public abstract class GUI extends ConfigData implements Listener{

	protected Player player;
	protected Inventory inv;
	
	public GUI(Player player) {
		this.player = player;
		SlimeFinder.instance.getServer().getPluginManager().registerEvents(this, SlimeFinder.instance);
		inv = Bukkit.createInventory(null, size(), name());
	}
	
	@EventHandler()
	public void onOpen(InventoryOpenEvent e) {
		System.out.println("INVENTORY OPEN");
		if(!e.getPlayer().hasPermission(permission())) {
			MessageUtils.sendMessage(player, messages.INAVLID_PERMISSION);
			e.setCancelled(true);
			return;
		}
		onOpenInventory(e);
	}
	
	@EventHandler()
	public void onClose(InventoryCloseEvent e) {
		this.onCloseInventory(e);
	}
	
	@EventHandler()
	public void onClick(InventoryClickEvent e) {
		e.setCancelled(!canTakeItems());
		if(e.getCurrentItem() == null) {
			return;
		}
		this.onClickInventory(e);
	}
	
	public abstract String name();
	public abstract String permission();
	
	public abstract int size();
	
	public abstract Sound sound();
	public abstract float soundLevel();
	public abstract boolean canTakeItems();
	
	public abstract void onClickInventory(InventoryClickEvent e);
	public abstract void onOpenInventory(InventoryOpenEvent e);
	public abstract void onCloseInventory(InventoryCloseEvent e);
	public abstract void Contents(Inventory inv);
	
	public void open() {
		player.getWorld().playSound(player.getLocation(), sound(), soundLevel(), soundLevel());
	    Contents(inv);
	    player.openInventory(inv);
	}
	
	public void addItem(int index, ItemStack item) {
		inv.setItem(index, item);
	}
	
	
}
