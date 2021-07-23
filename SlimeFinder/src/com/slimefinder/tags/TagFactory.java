package com.slimefinder.tags;

import java.util.ArrayList;
import java.util.List;

import com.slimefinder.messages.ConfigData;
import com.slimefinder.placeholder.time.TimeFormater;
import com.slimefinder.utils.MessageUtils;

public class TagFactory extends ConfigData{

	public String item = "";
	public String name = "";
	public int slimes = 0;
	
	public int centerx = 0;
	public int centery = 0;
	public int centerz = 0;
	
	public int cooldown = 0;
	
	
	public List<String> lore = new ArrayList<String>();
	
	public static TagFactory instance(String i) {
		TagFactory factory = new TagFactory(i);
		return factory;
	}
	
	public static TagFactory instance(List<String> i) {
		TagFactory factory = new TagFactory(i);
		return factory;
	}
	
	public TagFactory(String item) {
		this.item = item;
	}
	
	public TagFactory(List<String> lore) {
		this.lore = lore;
	}
	
	
	public String parseSlime(int slimes, int centerx, int centery, int centerz) {
		this.centerx = centerx;
		this.centery = centery;
		this.centerz = centerz;
		this.slimes = slimes;
		return this.parse();
	}
	
	public String parse() {
		return this.parse(this.item);
	}
	
	public List<String> listParse(){
		
		List<String> builder = new ArrayList<String>();
		
		this.lore.forEach(i -> {
			
			builder.add(parse(i));
			
		});
		
		return builder;
	}
	
	private String parse(String parser) {
		
		if(this.centery > 255 || this.centery < 0) {
			parser = parser.replace("%chunky%", "N/A");
		}else {
			parser = parser.replace("%chunky%", ""+this.centery);
		}
	
		TimeFormater formater = new TimeFormater();
		
		parser = parser.replace("%cooldown%", formater.parse(this.cooldown)+"");
		parser = parser.replace("%chunkx%", centerx+"");
		parser = parser.replace("%chunkz%", centerz+"");
		parser = parser.replace("%slimes%", slimes+"");
		parser = parser.replace("%prefix%", messages.PREFIX);
		parser = parser.replace("%version%", "1.0");
		parser = parser.replace("%user%", this.name);
		

		
		return MessageUtils.translateAlternateColorCodes(parser);
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public TagFactory setName(String name) {
		this.name = name;
		return this;
	}

	public List<String> getLore() {
		return lore;
	}

	public TagFactory setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}

	public int getSlimes() {
		return slimes;
	}

	public void setSlimes(int slimes) {
		this.slimes = slimes;
	}

	public int getCenterx() {
		return centerx;
	}

	public void setCenterx(int centerx) {
		this.centerx = centerx;
	}

	public int getCentery() {
		return centery;
	}

	public void setCentery(int centery) {
		this.centery = centery;
	}

	public int getCenterz() {
		return centerz;
	}

	public void setCenterz(int centerz) {
		this.centerz = centerz;
	}

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

}
