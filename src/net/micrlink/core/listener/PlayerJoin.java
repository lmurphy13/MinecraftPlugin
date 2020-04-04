package net.micrlink.core.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.objects.managers.AccountLinkManager;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getDisplayName();

		name = ChatColor.stripColor(name);

		event.setJoinMessage("§2[§a+§2]§a " + name + "§2 has connected!");

		AccountLinkManager linkManager = DiscordSRV.getPlugin().getAccountLinkManager();
		String linkedId = linkManager.getDiscordId(player.getUniqueId());

		if (linkedId == null)
			event.getPlayer().performCommand("discord link");

	}
}
