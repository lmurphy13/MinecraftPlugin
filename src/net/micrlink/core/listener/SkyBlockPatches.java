package net.micrlink.core.listener;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.micrlink.core.Core;

public class SkyBlockPatches implements Listener {

	@EventHandler
	public void onPlayerEnterBed(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		World world = player.getWorld();
		if (!Core.SURVIVAL_WORLDS.contains(world.getName())) {
			long time = world.getTime();
			event.setCancelled(true);
			if (time > 12541 && time < 23458) {
				player.sendMessage(
						"§4Sleeping is currently disabled in this world. But we will set the time to day for you.");
				world.setTime(0);
			}
		}
	}

	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent event) {
		Entity entity = event.getEntity();
		World world = event.getEntity().getWorld();

		if (Core.SKYBLOCK_WORLDS.contains(world.getName()) && entity.getType() == EntityType.PHANTOM)
			event.setCancelled(true);
	}
}
