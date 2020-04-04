package net.micrlink.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocess implements Listener {

	@EventHandler
	public void onCommmandPreProcess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();

		for (Player player : Bukkit.getOnlinePlayers())
			if (player.isOp())
				player.sendMessage("§3" + p.getName() + ": §b" + e.getMessage());
	}
}
