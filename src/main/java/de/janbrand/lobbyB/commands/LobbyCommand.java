package de.janbrand.lobbyB.commands;

import de.janbrand.lobbyB.LobbyB;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    private final LobbyB plugin; // Referenz zur Hauptklasse des Plugins

    // Konstruktor, der die Plugin-Instanz erhält
    public LobbyCommand(LobbyB plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Überprüfen, ob der Befehl von einem Spieler ausgeführt wurde
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cDieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;
        FileConfiguration config = plugin.getPluginConfig(); // Greife auf die Plugin-Konfiguration zu

        // Berechtigungsprüfung
        if (!player.hasPermission("lobbyb.lobby")) {
            player.sendMessage("§cDu hast keine Berechtigung, dich zur Lobby zu teleportieren.");
            return true;
        }

        // Überprüfen, ob eine Lobby-Position in der Konfiguration existiert
        if (config.contains("lobby.world")) {
            // Lese die gespeicherten Positionsdaten
            String worldName = config.getString("lobby.world");
            double x = config.getDouble("lobby.x");
            double y = config.getDouble("lobby.y");
            double z = config.getDouble("lobby.z");
            float yaw = (float) config.getDouble("lobby.yaw");
            float pitch = (float) config.getDouble("lobby.pitch");

            // Erstelle ein Location-Objekt aus den gelesenen Daten
            Location lobbyLocation = new Location(
                    plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);

            // Überprüfen, ob die Lobby-Welt existiert
            if (lobbyLocation.getWorld() == null) {
                player.sendMessage("§cFehler: Die Lobby-Welt '" + worldName + "' existiert nicht oder wurde nicht geladen.");
                return true;
            }

            player.teleport(lobbyLocation); // Teleportiere den Spieler
            player.sendMessage("§aDu wurdest zur Lobby teleportiert!");
        } else {
            player.sendMessage("§cDie Lobby-Position wurde noch nicht gesetzt.");
        }
        return true;
    }
}