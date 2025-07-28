package de.janbrand.lobbyB.listeners;

import de.janbrand.lobbyB.LobbyB;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent; // Importiere PlayerQuitEvent
import org.bukkit.inventory.PlayerInventory;

public class PlayerConnectionListener implements Listener { // Klasse umbenannt

    private final LobbyB plugin;

    public PlayerConnectionListener(LobbyB plugin) { // Konstruktor an Klassennamen anpassen
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        FileConfiguration config = plugin.getPluginConfig();

        // Entferne die Standard-Join-Nachricht des Servers
        event.setJoinMessage(null);

        if (config.contains("lobby.world")) {
            String worldName = config.getString("lobby.world");
            double x = config.getDouble("lobby.x");
            double y = config.getDouble("lobby.y");
            double z = config.getDouble("lobby.z");
            float yaw = (float) config.getDouble("lobby.yaw");
            float pitch = (float) config.getDouble("lobby.pitch");

            Location lobbyLocation = new Location(
                    plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);

            if (lobbyLocation.getWorld() == null) {
                plugin.getLogger().warning("Die Lobby-Welt '" + worldName + "' existiert nicht oder wurde nicht geladen. Spieler " + player.getName() + " konnte nicht zur Lobby teleportiert werden.");
                player.sendMessage("§cFehler: Die Lobby-Welt existiert nicht. Bitte informiere einen Administrator.");
                return;
            }

            player.teleport(lobbyLocation);
            player.sendMessage("§aWillkommen! Du wurdest zur Lobby teleportiert.");
            event.setJoinMessage("§eDer Spieler §4§l" + player.getName() + " §eist dem Server beigetreten!");
            inventory.clear();
            // TODO: Füge Items in das Inventar ein: TpToSurvival, TpToKreativ, TpToPlotWorld, ProfilCommand
        } else {
            player.sendMessage("§cWillkommen! Die Lobby-Position wurde noch nicht gesetzt.");
            plugin.getLogger().info("Spieler " + player.getName() + " ist beigetreten, aber die Lobby-Position ist nicht gesetzt.");
        }
    }

    // NEUE METHODE: Behandelt das Verlassen des Servers
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer(); // Der Spieler, der den Server verlässt

        // Entferne die Standard-Quit-Nachricht des Servers
        event.setQuitMessage(null);

        // Sende eine Quit Message
        event.setQuitMessage("§eDer Spieler §4§l" + player.getName() + " §ehat den Server verlassen!");
    }
}