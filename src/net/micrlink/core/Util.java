package net.micrlink.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Util {

	public static String locToString(Location location) {
		if (location == null)
			return null;

		String world = location.getWorld().getName();

		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		float yaw = location.getYaw();
		float pitch = location.getPitch();

		return world + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
	}

	public static Location getStringLocation(String string) {
		if (string == null)
			return null;
		String[] s = string.split(":");

		World world = Bukkit.getWorld(s[0]);

		double x = Double.parseDouble(s[1]);
		double y = Double.parseDouble(s[2]);
		double z = Double.parseDouble(s[3]);

		float yaw = Float.parseFloat(s[4]);
		float pitch = Float.parseFloat(s[5]);

		return new Location(world, x, y, z, yaw, pitch);
	}
}
