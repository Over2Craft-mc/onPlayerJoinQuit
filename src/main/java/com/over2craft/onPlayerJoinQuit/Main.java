package com.over2craft.onPlayerJoinQuit;

import com.over2craft.onPlayerJoinQuit.config.ConfigSection;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    public static Main plugin;

    public static List<ConfigSection> configSections;

    /**
     * Registers configurations sections and permissions
     */
    public void registerConfigSection() {

        configSections = new ArrayList<>();

        for (
                String section :
                Objects.requireNonNull(Main.plugin.getConfig().getConfigurationSection("onPlayerEvent")).getKeys(false)
        ) {
            ConfigSection cs = new ConfigSection(Objects.requireNonNull(Main.plugin.getConfig().getConfigurationSection("onPlayerEvent." + section)));
            configSections.add(cs);
            if (cs.requirePermission) {
                cs.permissions.forEach((permission) -> plugin.getServer().getPluginManager().getPermissions().add(
                        new Permission(
                        permission,
                        "A dynamic onPlayerJoinQuit permission",
                        cs.permissionDefault)
                ));
            }
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getLogger().info("[onPlayerJoinQuit] Enabling plugin");
        saveDefaultConfig();
        registerConfigSection();
        Bukkit.getPluginManager().registerEvents(new com.over2craft.onPlayerJoinQuit.Listener(), this);

        Objects.requireNonNull(getCommand("onPlayerJoinQuitReload")).setExecutor(
            (commandSender, command, s, strings) -> {
                Main.plugin.reloadConfig();
                registerConfigSection();
                commandSender.sendMessage(" ");
                commandSender.sendMessage("[onPlayerJoinQuit] Plugin reloaded.");
                commandSender.sendMessage(" ");
                return true;
            }
        );

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Bukkit.getLogger().info("[onPlayerJoinQuit] Could not find PlaceholderAPI. You won't be able to use PlaceholderAPI placeholders.");
        }
    }

}
