
package com.slimefinder.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.slimefinder.tags.TagFactory;

public class SlimeInCurrentLocation extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	Chunk chunk = player.getLocation().getChunk();
    
    	List<Entity> entities = Arrays.asList(chunk.getEntities()).stream().filter(i -> i.getType() == EntityType.SLIME).collect(Collectors.toList());
    	
     	int slimesNearMe = entities.size();
    	Block centerBlock = chunk.getBlock(7, 127, 7);
    	int X = centerBlock.getLocation().getBlockX();
    	int Z = centerBlock.getLocation().getBlockZ();
    	int Y = 256;
    	
    	if(!chunk.isSlimeChunk()) {
    		String message = messages.SLEF_CHUNK_EMPTY;
    		String taggedMessage = TagFactory.instance(message).parseSlime(slimesNearMe, X, Y, Z);
    		player.sendMessage(taggedMessage);
    		return;
    	}
   
    	if(entities.size() > 0) {
    		Y = (int) entities.get(0).getLocation().getY();
    	}
    	
		String message = messages.SELF_CHUNK_FOUND;
		String taggedMessage = TagFactory.instance(message).parseSlime(slimesNearMe, X, Y, Z);
		player.sendMessage(taggedMessage);
		
    }

    @Override

    public String name() {
        return "here";
    }

    @Override
    public String info() {
        return "Checks if a slime is in your chunk";
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