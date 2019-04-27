package net.saikatsune.veranylobby.manager;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;

@SuppressWarnings("deprecation")
public class ScoreboardManager {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    /*
    Using PermissionsEX API for Scoreboard.
     */

    private HashMap<Player, Scoreboard> scoreb = new HashMap<>();

    private String main = veranyLobby.getMainColorColor();
    private String secondary = veranyLobby.getSecondaryColor();

    private void setScoreboard(Player p) {

        PermissionUser user = PermissionsEx.getUser(p.getName());

        veranyLobby.getServerCount().get("ALL");

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("practice", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(veranyLobby.getScoreboardHeader());

        Team line1 = scoreboard.registerNewTeam(ChatColor.RESET.toString());
        line1.addEntry("§1§7§m--------");
        line1.setSuffix("------");
        objective.getScore("§1§7§m--------").setScore(8);

        Team player = scoreboard.registerNewTeam(ChatColor.MAGIC.toString());
        player.addEntry(secondary + "Players: ");
        objective.getScore(secondary + "Players: ").setScore(7);

        Team count = scoreboard.registerNewTeam(ChatColor.STRIKETHROUGH.toString());
        count.addEntry(" ");
        count.setSuffix("  " + main + veranyLobby.getServerCount().get("ALL"));
        objective.getScore(" ").setScore(6);

        Team blank = scoreboard.registerNewTeam(ChatColor.BLACK.toString());
        blank.addEntry("§1");
        objective.getScore("§1").setScore(5);

        Team rank = scoreboard.registerNewTeam(ChatColor.RED.toString());
        rank.addEntry(secondary + "Your rank: ");
        objective.getScore(secondary + "Your rank: ").setScore(4);

        Team playerrank = scoreboard.registerNewTeam(ChatColor.GOLD.toString());
        playerrank.addEntry("   " + main + user.getGroups()[0].getSuffix() + user.getGroups()[0].getName());
        objective.getScore("   " + main + user.getGroups()[0].getSuffix() + user.getGroups()[0].getName()).setScore(3);

        Team blank2 = scoreboard.registerNewTeam(ChatColor.DARK_PURPLE.toString());
        blank2.addEntry("§2");
        objective.getScore("§2").setScore(2);

        Team twitter = scoreboard.registerNewTeam(ChatColor.LIGHT_PURPLE.toString());
        twitter.addEntry("§7> ");
        twitter.setSuffix(veranyLobby.getScoreboardFooter());
        objective.getScore("§7> ").setScore(1);

        /*
        Change URL to your server URL
         */

        Team line2 = scoreboard.registerNewTeam(ChatColor.BLUE.toString());
        line2.addEntry("§7§m--------");
        line2.setSuffix("------");
        objective.getScore("§7§m--------").setScore(0);

        p.setScoreboard(scoreboard);
        scoreb.put(p, scoreboard);
    }

    public void updateScoreboard() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(veranyLobby, new BukkitRunnable() {
            @Override
            public void run() {
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if(!scoreb.containsKey(all)) {
                        setScoreboard(all);
                    } else {
                        veranyLobby.getPlayerCount("ALL");
                        Scoreboard score = scoreb.get(all);
                        score.getTeam(ChatColor.STRIKETHROUGH.toString()).setSuffix("  " + main + veranyLobby.getServerCount().get("ALL"));
                    }
                }
            }
        }, 0, 3*20);
    }

    /*
    1 second = 20 ticks

    3 seconds = 3*20 ticks (60)

    Don't update it every tick, it could crash the server.
     */

}
