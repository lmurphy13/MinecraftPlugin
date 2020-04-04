package net.micrlink.core.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.micrlink.core.Core;
import net.micrlink.core.Util;
import net.micrlink.core.cmds.survivalCmd;

public class SurvivalManager implements Listener {

	public SurvivalManager() {
		Core core = Core.get();
		core.getCommand("survival").setExecutor(new survivalCmd());
		core.getServer().getPluginManager().registerEvents(this, core);
	}

	public void saveSurvivalLocation(Player player, Location location) {
		UserSettingsManager settings = Core.getUserSettingsManager();
		settings.set(player, "survival_location", Util.locToString(location));
	}

	public Location getSurvivalLocation(Player player) {
		UserSettingsManager settings = Core.getUserSettingsManager();
		return Util.getStringLocation((String) settings.get(player, "survival_location", null));

	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		String fromWorldName = e.getFrom().getWorld().getName();
		String toWorldName = e.getTo().getWorld().getName();

		if (Core.SURVIVAL_WORLDS.contains(fromWorldName) && !Core.SURVIVAL_WORLDS.contains(toWorldName)) {
			saveSurvivalLocation(p, e.getFrom());
			p.sendMessage("§7Your survival location has been saved to file. To return there type §b/survival");
		}
	}

}
