package net.micrlink.core.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.micrlink.core.Core;

public class PlayerLeave implements Listener {

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String name = player.getDisplayName();

		name = ChatColor.stripColor(name);
		event.setQuitMessage("§4[§c-§4] §c" + name + "§4 has disconnected!");

		Core.getAFKManager().removePlayer(player);
	}
}
