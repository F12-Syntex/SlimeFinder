
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

public class SlimeNearUser extends SubCommand {

    @Override
    public void onCommand(Player player, String[] args) {
    	
    	Chunk[] chunks = player.getWorld().getLoadedChunks();
    	
    	Chunk closestChunk = null;
    	int slimesNearMe = 0;
    	int Y = 256;
    	int smallestDistance = -1;
    	
    	for(Chunk i : chunks) {
    		
    		if(!i.isSlimeChunk()) continue;
    		
			if(closestChunk == null) {
				closestChunk = i;
			}
			
			List<Entity> slimes = Arrays.asList(i.getEntities()).stream().filter(o -> o.getType() == EntityType.SLIME).collect(Collectors.toList());
			
			slimesNearMe = slimes.size();
			
	    	int differenceX = Math.abs(player.getLocation().getBlockX() - closestChunk.getBlock(7, 127, 7).getLocation().getBlockX());
	    	int differenceZ = Math.abs(player.getLocation().getBlockZ() - closestChunk.getBlock(7, 127, 7).getLocation().getBlockZ());
			
	    	int CurrentChunkDifferenceX = Math.abs(player.getLocation().getBlockX() - i.getBlock(7, 127, 7).getLocation().getBlockX());
	    	int CurrentChunkDifferenceZ = Math.abs(player.getLocation().getBlockZ() - i.getBlock(7, 127, 7).getLocation().getBlockZ());
	    	
	    	int distance = differenceX + differenceZ;

    		if(smallestDistance < 0) {
    			smallestDistance = distance;
	    		closestChunk = i;
	    		if(slimesNearMe > 0) {
		    		Y = slimes.get(0).getLocation().getBlockY();	
	    		}
	    		continue;
    		}
	    	
	    	if((CurrentChunkDifferenceX + CurrentChunkDifferenceZ) < smallestDistance) {
	    		closestChunk = i;
	    		if(slimesNearMe > 0) {
		    		Y = slimes.get(0).getLocation().getBlockY();	
	    		}
	    		smallestDistance = (CurrentChunkDifferenceX + CurrentChunkDifferenceZ);
	    	}
	    	
    	}
    	
    	if(closestChunk == null) {
    		String message = messages.NEAR_CHUNK_EMPTY;
    		String taggedMessage = TagFactory.instance(message).parseSlime(0, 0, 0, 0);
    		player.sendMessage(taggedMessage);
    		return;
    	}
    	
		Block centerBlock = closestChunk.getBlock(7, 127, 7);
		int X = centerBlock.getLocation().getBlockX();
		int Z = centerBlock.getLocation().getBlockZ();
		
		String message = messages.NEAR_CHUNK_FOUND;
		String taggedMessage = TagFactory.instance(message).parseSlime(slimesNearMe, X, Y, Z);
		player.sendMessage(taggedMessage);
		
		
    	
    }

    @Override

    public String name() {
        return "near";
    }

    @Override
    public String info() {
        return "Looks for the nearest slime to you!";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

	@Override
	public String permission() {
		return permissions.NEAR;	
	}
	

}