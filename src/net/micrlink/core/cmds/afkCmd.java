package net.micrlink.core.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.micrlink.core.Core;
import net.micrlink.core.manager.AFKManager;

public class afkCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		AFKManager afkManager = Core.getAFKManager();
		Player player = null;

		if (cmd.getName().equalsIgnoreCase("afk")) {
			if (!(sender instanceof Player))
				return true;

			player = (Player) sender;
			if (args.length > 0 && player.isOp())
				if ((player = Bukkit.getPlayer(args[0])) == null)
					return true;

			afkManager.toggleAFK(player);
			return true;
		}
		return false;
	}
}
