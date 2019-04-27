package net.saikatsune.veranylobby.manager;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    private String main = veranyLobby.getMainColorColor();
    private String secondary = veranyLobby.getSecondaryColor();

    public void resetPlayer(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.setHealth(20);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setTotalExperience(0);
        p.setExp(0);
        p.setGameMode(GameMode.SURVIVAL);
    }

    public void sendJoinMessage(Player p) {
        for (int i = 0; i < 100; i++) {
            p.sendMessage("");
        }

        p.sendMessage(veranyLobby.getPrefix() + secondary + "Welcome to " + main + veranyLobby.getServerName() + secondary + "!");

    }

}
