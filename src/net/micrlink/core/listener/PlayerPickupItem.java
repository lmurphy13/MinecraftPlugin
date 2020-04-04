package net.micrlink.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.jetbrains.annotations.NotNull;

import net.micrlink.core.Core;
import net.micrlink.core.manager.AFKManager;

@SuppressWarnings("deprecation")
public class PlayerPickupItem implements @NotNull Listener {

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		AFKManager afkManager = Core.getAFKManager();
		Player player = event.getPlayer();

		if (afkManager.isAFK(player))
			event.setCancelled(true);

	}
}
