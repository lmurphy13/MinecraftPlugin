package net.micrlink.core;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

import net.micrlink.core.cmds.afkCmd;
import net.micrlink.core.cmds.creativeCmd;
import net.micrlink.core.cmds.devCmd;
import net.micrlink.core.cmds.survivalCmd;
import net.micrlink.core.cmds.homeCmd;
import net.micrlink.core.cmds.togglehud;
import net.micrlink.core.listener.PlayerBedEnter;
import net.micrlink.core.listener.PlayerChat;
import net.micrlink.core.listener.PlayerCommandPreprocess;
import net.micrlink.core.listener.PlayerJoin;
import net.micrlink.core.listener.PlayerLeave;
import net.micrlink.core.listener.PlayerPickupItem;
import net.micrlink.core.listener.SkyBlockPatches;
import net.micrlink.core.manager.AFKManager;
import net.micrlink.core.manager.ScoreboardManager;
import net.micrlink.core.manager.SurvivalManager;
import net.micrlink.core.manager.UserSettingsManager;

public class Core extends JavaPlugin {

	public static List<String> SURVIVAL_WORLDS = Arrays.asList("world", "world_nether", "world_the_end");
	public static List<String> SKYBLOCK_WORLDS = Arrays.asList("skyworld", "skyworldworld_nether");
	public static List<String> CREATIVE_WORLDS = Arrays.asList("plots");

	private static Core plugin;
	private static AFKManager afkManager;
	private static SurvivalManager survivalManager;
	private static ScoreboardManager scoreboardManager;
	private static UserSettingsManager userSettingsManager;

	public static Core get() {
		return plugin;
	}

	public static ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}

	public static AFKManager getAFKManager() {
		return afkManager;
	}

	public static SurvivalManager getSurvivalManager() {
		return survivalManager;
	}

	public static UserSettingsManager getUserSettingsManager() {
		return userSettingsManager;
	}

	public void onEnable() {

		plugin = this;
		scoreboardManager = new ScoreboardManager();
		afkManager = new AFKManager();
		survivalManager = new SurvivalManager();
		userSettingsManager = new UserSettingsManager();

		new ActionBarAPI();

		registerCommands();
		registerEvents();
		scheduleRunnable();

		Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {
				World world = Bukkit.getWorld("plots");
				world.setTime(0);
				world.setWeatherDuration(6000);
				world.setThundering(false);
			}
		}, 20, 20);

		getLogger().log(Level.INFO, "Core has loaded successfully!");
	}

	public void updateActionBar(Player player) {
		if (!userSettingsManager.showActionBar(player)) {
			ActionBarAPI.sendActionBar(player, "", 0);
			return;
		}
		Location loc = player.getLocation();

		long time = loc.getWorld().getTime();

		int hour = (int) (time / 1000);
		time %= 1000;
		int minute = (int) (time / 16.6);

		hour = (6 + hour) % 24;
		String h = new DecimalFormat("00").format(hour);
		String m = new DecimalFormat("00").format(minute);

		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		String dir = "";
		float yaw = loc.getYaw();

		if (yaw > 315 || yaw < 45)
			dir = "S";
		if (yaw > 45 && yaw < 135)
			dir = "W";
		if (yaw > 135 && yaw < 225)
			dir = "N";
		if (yaw > 225 && yaw < 315)
			dir = "E";

		String message = "�6XYZ �f" + x + " " + y + " " + z + "    �6" + dir + "    " + h + ":" + m;
		ActionBarAPI.sendActionBar(player, message);
	}

	private void scheduleRunnable() {
		getServer().getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers())
					updateActionBar(player);
			}
		}, 20, 20);
	}

	private void registerCommands() {
		getCommand("afk").setExecutor(new afkCmd());
		getCommand("creative").setExecutor(new creativeCmd());
		getCommand("dev").setExecutor(new devCmd());
		getCommand("survival").setExecutor(new survivalCmd());
		getCommand("togglehud").setExecutor(new togglehud());
		getCommand("home").setExecutor(new homeCmd());
		getCommand("sethome").setExecutor(new homeCmd());
	}

	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new PlayerBedEnter(), this);
		pm.registerEvents(new PlayerChat(), this);
		pm.registerEvents(new PlayerCommandPreprocess(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLeave(), this);
		pm.registerEvents(new PlayerPickupItem(), this);
		pm.registerEvents(new SkyBlockPatches(), this);
	}

}
