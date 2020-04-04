package net.micrlink.core.cmds;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.micrlink.core.Core;
import net.micrlink.core.manager.SurvivalManager;

public class survivalCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		SurvivalManager survivalManager = Core.getSurvivalManager();

		if (cmd.getName().equalsIgnoreCase("survival")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Location location = survivalManager.getSurvivalLocation(p);
				if (location == null) {
					p.sendMessage(
							"§cYou have no saved survival location. If you think this is an error, your screwed!");
					return true;
				}
				p.teleport(location);
				survivalManager.saveSurvivalLocation(p, null);
				return true;
			}
		}
		return false;
	}
}
