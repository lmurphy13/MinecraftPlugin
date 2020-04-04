package net.micrlink.core.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.micrlink.core.Core;
import net.micrlink.core.manager.UserSettingsManager;

public class togglehud implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		UserSettingsManager settings = Core.getUserSettingsManager();
		if (cmd.getName().equalsIgnoreCase("togglehud")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (args.length == 0) {
					player.sendMessage("§cIncorrect usage of the command!");
					return false;
				}
				switch (args[0]) {
				case "scoreboard":
					settings.toggleScoreboard(player);
					player.sendMessage(
							"HUD > Scoreboard is now " + (settings.showScoreboard(player) ? "visible" : "hidden"));
					break;
				case "actionbar":
					settings.toggleActionBar(player);
					break;
				default:
					player.sendMessage("§eCould not find setting for §7§o" + args[0]
							+ "§e. Valid toggles are §7scoreboard, and actionbar");
				}

			}
		}
		return false;
	}

}
