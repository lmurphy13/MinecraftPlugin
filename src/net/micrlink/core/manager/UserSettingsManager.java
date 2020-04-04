package net.micrlink.core.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.micrlink.core.Core;

public class UserSettingsManager {

	public boolean showActionBar(Player player) {
		return (boolean) get(player, "show_action_bar", true);
	}

	public void toggleActionBar(Player player) {
		set(player, "show_action_bar", !showActionBar(player));
	}

	public boolean showScoreboard(Player player) {
		return (boolean) get(player, "show_scoreboard", true);
	}

	public void toggleScoreboard(Player player) {
		set(player, "show_scoreboard", !showScoreboard(player));
	}

	public void set(Player player, String key, Object value) {
		File file = new File(Core.get().getDataFolder(), "users/" + player.getUniqueId() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

		config.set(key, value);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Object get(Player player, String key, Object defaultValue) {
		File file = new File(Core.get().getDataFolder(), "users/" + player.getUniqueId() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		if (config.get(key) == null)
			set(player, key, defaultValue);

		return config.get(key);
	}

	public static String getString(Player player, String key) {
		File file = new File(Core.get().getDataFolder(), "users/" + player.getUniqueId() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		return config.getString(key);
	}

}
