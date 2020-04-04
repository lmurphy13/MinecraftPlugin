package net.micrlink.core.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.micrlink.core.Core;

public class PlayerChat implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();

		String worldName = player.getWorld().getName();

		String prefix = "";
		if (Core.SURVIVAL_WORLDS.contains(worldName))
			prefix = "§2Survival ";
		if (Core.CREATIVE_WORLDS.contains(worldName))
			prefix = "§dCreative ";
		if (Core.SKYBLOCK_WORLDS.contains(worldName))
			prefix = "§bSkyblock ";

		String message = ChatColor.translateAlternateColorCodes('&', event.getMessage());
		event.setFormat(prefix + "§7" + player.getDisplayName() + "§8 > §f" + message);
		event.setMessage(message);
	}
}
