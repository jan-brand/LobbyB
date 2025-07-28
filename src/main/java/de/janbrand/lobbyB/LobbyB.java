package de.janbrand.lobbyB;

import de.janbrand.lobbyB.commands.LobbyCommand;
import de.janbrand.lobbyB.commands.ReloadCommand;
import de.janbrand.lobbyB.commands.SetLobbyCommand;
import de.janbrand.lobbyB.listeners.PlayerConnectionListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyB extends JavaPlugin {

    private FileConfiguration config; // Die Konfiguration für das Plugin

    @Override
    public void onEnable() {
        // Speichert die Standard-Konfiguration, falls sie noch nicht existiert
        saveDefaultConfig();
        config = getConfig(); // Lädt die Konfigurationsdatei

        // Befehle registrieren
        this.getCommand("setlobby").setExecutor(new SetLobbyCommand(this));
        this.getCommand("lobby").setExecutor(new LobbyCommand(this));
        this.getCommand("lobbybreload").setExecutor(new ReloadCommand(this));

        // Event Listener registrieren
        // Registriere eine neue Instanz von PlayerJoinListener beim Server
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);

        getLogger().info("LobbyB Plugin wurde aktiviert!");
    }

    @Override
    public void onDisable() {
        getLogger().info("LobbyB Plugin wurde deaktiviert!");
    }

    // Eine Methode, um auf die Konfiguration von anderen Klassen zugreifen zu können
    public FileConfiguration getPluginConfig() {
        return this.config;
    }

    // Eine Methode, um die Konfiguration von anderen Klassen speichern zu können
    public void savePluginConfig() {
        this.saveConfig();
    }
}