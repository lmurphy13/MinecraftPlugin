package net.micrlink.core.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class devCmd implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("dev")) {
			if (sender.isOp()) {
				sender.sendMessage("----=[ Is Sleep Ignored ]=----");
				for (Player player : Bukkit.getOnlinePlayers())
					sender.sendMessage(
							"  §7" + player.getName() + " " + (player.isSleepingIgnored() ? "§aTrue" : "§cFalse"));
			}
		}
		return false;
	}

}
