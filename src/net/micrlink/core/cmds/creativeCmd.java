package net.micrlink.core.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class creativeCmd implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("creative")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				player.teleport(Bukkit.getWorld("plots").getSpawnLocation());
			}
		}
		return false;
	}
}
