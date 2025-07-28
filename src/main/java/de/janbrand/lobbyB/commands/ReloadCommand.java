package de.janbrand.lobbyB.commands;

import de.janbrand.lobbyB.LobbyB;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final LobbyB plugin;

    public ReloadCommand(LobbyB plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Berechtigungsprüfung
        if (!sender.hasPermission("lobbyb.reload")) {
            sender.sendMessage("§cDu hast keine Berechtigung, das Plugin neu zu laden.");
            return true;
        }

        // Führe den Reload durch
        plugin.reloadConfig();
        sender.sendMessage("§aLobbyB Plugin-Konfiguration wurde neu geladen!");
        return true;
    }
}