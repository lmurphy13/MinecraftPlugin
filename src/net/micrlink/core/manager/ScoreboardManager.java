package net.micrlink.core.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Guild;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Member;
import github.scarsz.discordsrv.dependencies.jda.api.entities.VoiceChannel;
import net.micrlink.core.Core;

public class ScoreboardManager {

	private Guild mainGuild;
	private final Scoreboard emptyScoreboard;

	public ScoreboardManager() {
		this.mainGuild = DiscordSRV.getPlugin().getMainGuild();
		this.emptyScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

		Core plugin = Core.get();
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					Scoreboard scoreboard = createScoreboard(player);
					player.setScoreboard(scoreboard);
				}
			}
		}, 20, 20);
	}

	private List<VoiceChannel> getVoiceChannels() {
		if (mainGuild == null)
			return new ArrayList<VoiceChannel>();
		return mainGuild.getVoiceChannels();
	}

	private List<Member> getMembers() {
		if (mainGuild == null)
			return new ArrayList<Member>();
		return mainGuild.getMembers();
	}

	private void createScores(Objective objective) {
		int i = 16;
		List<String> list = Arrays.asList("§a", "§b", "§c", "§d", "§e", "§f", "§1", "§2", "§3", "§4");
		int spaces = 0;

		for (VoiceChannel channel : getVoiceChannels()) {

			List<Member> members = channel.getMembers();

			if (members.isEmpty())
				continue;

			objective.getScore(list.get(spaces++)).setScore(i--);
			objective.getScore(channel.getName()).setScore(i--);

			for (Member member : members) {
				String prefix = "§f";
				switch (member.getOnlineStatus()) {
				case DO_NOT_DISTURB:
					prefix = "§c";
					break;
				case ONLINE:
					prefix = "§a";
					break;
				case IDLE:
					prefix = "§e";
					break;
				case INVISIBLE:
				case UNKNOWN:
					prefix = "§7";
				default:
					break;
				}
				UUID uuid = DiscordSRV.getPlugin().getAccountLinkManager().getUuid(member.getId());
				if (uuid != null) {
					Player player = Bukkit.getPlayer(uuid);
					if (player != null)
						if (Core.getAFKManager().isAFK(Bukkit.getPlayer(uuid)))
							prefix = "[AFK] " + prefix;
				}
				objective.getScore(prefix + member.getUser().getName()).setScore(i--);

			}
		}

	}

	private Scoreboard createScoreboard(Player player) {
		if (!DiscordSRV.isReady || getMembers().isEmpty())
			return emptyScoreboard;

		if (!Core.getUserSettingsManager().showScoreboard(player))
			return emptyScoreboard;

		org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();

		Objective objective = board.registerNewObjective("serverInfo", "dummy", "§lDiscord");

		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		createScores(objective);

		return board;

	}
}
