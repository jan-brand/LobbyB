package de.janbrand.lobbyB.commands;

import de.janbrand.lobbyB.LobbyB;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbyCommand implements CommandExecutor {

    private final LobbyB plugin; // Referenz zur Hauptklasse des Plugins

    // Konstruktor, der die Plugin-Instanz erhält
    public SetLobbyCommand(LobbyB plugin) {
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
        if (!player.hasPermission("lobbyb.setlobby")) {
            player.sendMessage("§cDu hast keine Berechtigung, die Lobby zu setzen.");
            return true;
        }

        Location currentLocation = player.getLocation(); // Aktuelle Position des Spielers

        // Speichere die Positionsdaten in der Konfiguration
        config.set("lobby.world", currentLocation.getWorld().getName());
        config.set("lobby.x", currentLocation.getX());
        config.set("lobby.y", currentLocation.getY());
        config.set("lobby.z", currentLocation.getZ());
        config.set("lobby.yaw", currentLocation.getYaw());
        config.set("lobby.pitch", currentLocation.getPitch());

        plugin.savePluginConfig(); // Speichere die Konfigurationsdatei
        player.sendMessage("§aDie Lobby-Position wurde gespeichert!");
        return true;
    }
}