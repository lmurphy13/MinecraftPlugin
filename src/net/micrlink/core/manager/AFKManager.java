package net.micrlink.core.manager;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.micrlink.core.Core;
import net.micrlink.core.cmds.afkCmd;

public class AFKManager implements Listener {
	private ArrayList<String> afkPlayers = new ArrayList<>();
	private HashMap<String, Integer> afkTimer;

	public AFKManager() {

		afkPlayers = new ArrayList<>();
		afkTimer = new HashMap<>();

		Core core = Core.get();
		core.getCommand("afk").setExecutor(new afkCmd());
		core.getServer().getPluginManager().registerEvents(this, core);
		core.getServer().getScheduler().scheduleSyncRepeatingTask(core, new Runnable() {

			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					int timer = 120;
					if (afkTimer.containsKey(player.getName()))
						timer = afkTimer.remove(player.getName());
					if (--timer <= 0)
						setAFK(player, true);
					else
						afkTimer.put(player.getName(), timer);
				}
			}
		}, 20, 20);

	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		AFKManager afkManager = Core.getAFKManager();
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (afkManager.isAFK(player)) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Core.get().updateActionBar(p);
		afkTimer.put(p.getName(), 120);
		if (isAFK(p))
			setAFK(p, false);

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		afkTimer.put(p.getName(), 120);
		if (isAFK(p))
			setAFK(p, false);
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		afkTimer.put(p.getName(), 120);
		if (isAFK(p))
			setAFK(p, false);

	}

	public boolean isAFK(Player player) {
		return afkPlayers.contains(player.getName());
	}

	private void setAFK(Player player, boolean afk) {

		if (afk && afkPlayers.contains(player.getName()))
			return;
		if (!afk && !afkPlayers.contains(player.getName()))
			return;

		if (afk) {
			afkPlayers.add(player.getName());
		} else {
			afkPlayers.remove(player.getName());
		}

		Bukkit.getServer().broadcastMessage("§7" + player.getName() + (afk ? " is now AFK" : " is no longer AFK"));
		player.setSleepingIgnored(afk);
	}

	public void toggleAFK(Player player) {
		setAFK(player, !isAFK(player));
	}

	public void removePlayer(Player player) {
		afkPlayers.remove(player.getName());
	}

}
