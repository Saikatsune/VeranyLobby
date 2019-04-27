package net.saikatsune.veranylobby.data;

import net.saikatsune.veranylobby.commands.SetupCommand;
import net.saikatsune.veranylobby.commands.StaffCommand;
import net.saikatsune.veranylobby.license.AdvancedLicense;
import net.saikatsune.veranylobby.listener.*;
import net.saikatsune.veranylobby.lms.commands.LMSCommand;
import net.saikatsune.veranylobby.lms.manager.LMSManager;
import net.saikatsune.veranylobby.manager.*;
import net.saikatsune.veranylobby.utilities.Inventories;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class VeranyLobby extends JavaPlugin implements PluginMessageListener {

    /*
    This plugin was written by Saikatsune!

    Libraries: PaperSpigot-1.8.8, PermissionsEX
    License-System: AdvancedLicense

    Discord: Saikatsune#2329
    Twitter: @SaikatsuneV2

    This plugin was made for the Verany Network.
    */

    private String prefix;

    private String mainColor;
    private String secondaryColor;

    private String scoreboardHeader;
    private String scoreboardFooter;

    private String serverName;

    private int soupHealing;

    private static VeranyLobby instance;

    private LocationManager locationManager;
    private Inventories inventories;
    private ScoreboardManager scoreboardManager;
    private PlayerManager playerManager;
    private ConnectionManager connectionManager;

    private LMSManager lmsManager;

    private boolean silenced;

    private HashMap<String, Integer> serverCount;
    private HashMap<String, String> tournamentKit;

    private ArrayList<Player> editorMode;
    private ArrayList<Player> hidePlayers;

    private ArrayList<Player> lmsPlayers;
    private ArrayList<Player> lmsSpectators;

    @Override
    public void onEnable() {

        createConfigFile();

        if(new AdvancedLicense(getConfig().getString("SECURITY.LICENSE_KEY"), "http://54.37.166.10/license/verify.php", this).register()) {

            /*
            Change prefix and color to your wish!
            */

            prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SETTINGS.PREFIX"));

            mainColor = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SETTINGS.MAIN_COLOR"));
            secondaryColor = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SETTINGS.SECONDARY_COLOR"));

            scoreboardHeader = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SETTINGS.SCOREBOARD_HEADER"));
            scoreboardFooter = ChatColor.translateAlternateColorCodes('&', getConfig().getString("SETTINGS.SCOREBOARD_FOOTER"));

            serverName = getConfig().getString("SETTINGS.SERVER_NAME");

            soupHealing = getConfig().getInt("TOURNAMENTS.SOUP_HEALING");

            instance = this;

            locationManager = new LocationManager();
            inventories = new Inventories();
            scoreboardManager = new ScoreboardManager();
            playerManager = new PlayerManager();
            connectionManager = new ConnectionManager();

            lmsManager = new LMSManager();

            serverCount = new HashMap<>();
            tournamentKit = new HashMap<>();

            editorMode = new ArrayList<>();
            hidePlayers = new ArrayList<>();

            lmsPlayers = new ArrayList<>();
            lmsSpectators = new ArrayList<>();

            silenced = false;

            init(Bukkit.getPluginManager());

            tournamentKit.put("Kit", "UHC");

            Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

            World world = Bukkit.createWorld(new WorldCreator("arena"));
            world.setGameRuleValue("naturalRegeneration", "false");

            scoreboardManager.updateScoreboard();
        } else {
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        lmsPlayers.clear();
        lmsSpectators.clear();
    }

    private void init(PluginManager pluginManager) {
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("staff").setExecutor(new StaffCommand());

        getCommand("lms").setExecutor(new LMSCommand());

        pluginManager.registerEvents(new SetupCommand(), this);
        pluginManager.registerEvents(new StaffCommand(), this);

        pluginManager.registerEvents(new LMSCommand(), this);

        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new BlockChangeListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new WeatherChangeListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerPickupAndDropListener(), this);
        pluginManager.registerEvents(new InteractionListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
    }

    private void createConfigFile() {
        FileConfiguration config = getConfig();

        config.addDefault("SETTINGS.PREFIX", "&d&oVeranyMC &7> ");
        config.addDefault("SETTINGS.MAIN_COLOR", "&d");
        config.addDefault("SETTINGS.SECONDARY_COLOR", "&f");
        config.addDefault("SETTINGS.SERVER_NAME", "Verany Network");
        config.addDefault("SETTINGS.SCOREBOARD_HEADER", "&d&lVeranyMC &7> Lobby");
        config.addDefault("SETTINGS.SCOREBOARD_FOOTER", "&fwww.veranymc.eu");

        config.addDefault("SECURITY.LICENSE_KEY", "YOUR LICENSE KEY");

        config.addDefault("TOURNAMENTS.ENABLED", true);
        config.addDefault("TOURNAMENTS.MIN_PLAYERS", 2);
        config.addDefault("TOURNAMENTS.MAX_PLAYERS", 12);
        config.addDefault("TOURNAMENTS.SOUP_HEALING", 7);

        config.options().copyDefaults(true);
        saveConfig();
    }



    public void setSilenced(boolean silenced) {
        this.silenced = silenced;
    }

    public void getPlayerCount(String server) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            dataOutputStream.writeUTF("PlayerCount");
            dataOutputStream.writeUTF(server);
            Bukkit.getServer().sendPluginMessage(this, "BungeeCord", byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        if(channel.equals("BungeeCord")) {
            try {
                DataInput dataInput = new DataInputStream(new ByteArrayInputStream(message));
                String command = dataInput.readUTF();

                try {
                    if(command.equals("PlayerCount")) {
                        String server = dataInput.readUTF();
                        int playerCount = dataInput.readInt();
                        serverCount.put(server, playerCount);
                    }
                } catch (EOFException eof) {
                    eof.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Player> getLmsSpectators() {
        return lmsSpectators;
    }

    public String getPrefix() {
        return prefix;
    }

    public static VeranyLobby getInstance() {
        return instance;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public Inventories getInventories() {
        return inventories;
    }

    public ArrayList<Player> getEditorMode() {
        return editorMode;
    }

    public boolean isSilenced() {
        return silenced;
    }

    public HashMap<String, Integer> getServerCount() {
        return serverCount;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ArrayList<Player> getHidePlayers() {
        return hidePlayers;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public String getMainColorColor() {
        return mainColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public ArrayList<Player> getLmsPlayers() {
        return lmsPlayers;
    }

    public LMSManager getLmsManager() {
        return lmsManager;
    }

    public String getScoreboardHeader() {
        return scoreboardHeader;
    }

    public String getServerName() {
        return serverName;
    }

    public String getScoreboardFooter() {
        return scoreboardFooter;
    }

    public HashMap<String, String> getTournamentKit() {
        return tournamentKit;
    }

    public int getSoupHealing() {
        return soupHealing;
    }

}
