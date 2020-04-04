package net.micrlink.core.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

import net.micrlink.core.Core;

public class PlayerBedEnter implements Listener {

	private void broadcastWorld(World world, String string) {
		for (Player player : Bukkit.getOnlinePlayers())
			if (player.getWorld() == world)
				player.sendMessage(string);
	}

	@EventHandler
	public void onPlayerBedEnter(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		World world = player.getLocation().getWorld();

		boolean a = true;

		if (event.isCancelled())
			return;

		for (Player p : world.getPlayers())
			if (!player.equals(p)) {
				p.setSleepingIgnored(true);
				a = false;
			}
		final boolean alone = a;

		Bukkit.getScheduler().runTaskLater(Core.get(), new Runnable() {
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers())
					p.setSleepingIgnored(false);
				if (!alone)
					broadcastWorld(world, player.getName() + " §ewent to bed. Sweet Dreams");
			}
		}, 102);
	}
}
