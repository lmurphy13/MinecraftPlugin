package net.micrlink.core.cmds;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.micrlink.core.Core;
import net.micrlink.core.manager.SurvivalManager;

public class homeCmd implements CommandExecuter {
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		SurvivalManager survivalManager = Core.getSurvivalManager();

		if (cmd.getName().equalsIgnoreCase("home")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				Location location = survivalManager.getHomeLocation(p);
				if (location == null) {
					p.sendMessage(
							"§cYou have no saved home location. If you think this is an error, you have a long walk back!");
					return true;
				}
				p.teleport(location);
				return true;
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("sethome")) {
          		if (sender instanceof Player) {
               			Player p = (Player) sender;
				Location location = survivalManager.saveHomeLocation(p)
				p.sendMessage("Set home location!");
				return true;
           		}
		}
       		return false;
	}

}
