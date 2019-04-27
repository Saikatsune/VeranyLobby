package net.saikatsune.veranylobby.manager;

import net.saikatsune.veranylobby.data.VeranyLobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectionManager {

    private VeranyLobby veranyLobby = VeranyLobby.getInstance();

    public void connect(String server, Player p) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(veranyLobby, "BungeeCord");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException ex) {
            ex.printStackTrace();
            p.sendMessage(veranyLobby.getPrefix() + "§cAn error occurred! Can't connect to " + server + "§c!");
        }
        p.sendPluginMessage(veranyLobby, "BungeeCord", b.toByteArray());
    }

}
