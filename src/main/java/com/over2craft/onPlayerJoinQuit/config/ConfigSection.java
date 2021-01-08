package com.over2craft.onPlayerJoinQuit.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConfigSection {

    public boolean onJoin;
    public boolean onQuit;

    public boolean requirePlayerName;
    public List<String> playersNames;

    public PermissionDefault permissionDefault;
    public boolean requirePermission;
    public List<String> permissions;

    public boolean quitMessageEnabled;
    public String quitMessage;

    public boolean joinMessageEnabled;
    public String joinMessage;

    public List<String> commandsAsConsole;
    public List<String> commandAsPlayer;

    public ConfigSection (ConfigurationSection c) {
        onJoin = c.getBoolean("onJoin");
        onQuit = c.getBoolean("onQuit");

        if (requirePlayerName = c.getBoolean("requirements.playerName.required")) {
            playersNames = c.getStringList("requirements.playerName.playerNames");
        }


        if (requirePermission = c.getBoolean("requirements.permission.required")) {
            permissionDefault = PermissionDefault.getByName(Objects.requireNonNull(c.getString("requirements.permission.default")));
            permissions = c.getStringList("requirements.permission.permissions");
        }

        if (quitMessageEnabled = c.getBoolean("quitMessage.enabled")) {
            quitMessage = c.getString("quitMessage.message");
        }

        if (joinMessageEnabled = c.getBoolean("joinMessage.enabled")) {
            joinMessage = c.getString("joinMessage.message");
        }

        commandsAsConsole = c.getStringList("commands_as_console");
        commandAsPlayer = c.getStringList("commands_as_player");
    }

    public boolean doesSectionApply(Player p, EventType eventType) {

        AtomicBoolean hasPermission = new AtomicBoolean(true);
        boolean eventApply = false;
        AtomicBoolean isPlayer = new AtomicBoolean(true);

        if (requirePermission) {
            hasPermission.set(false);
            permissions.forEach((permission) -> {
                if (p.hasPermission(permission)) {
                    hasPermission.set(true);
                }
            });
        }

        if (requirePlayerName) {
            isPlayer.set(false);
            playersNames.forEach((pName) -> {
                if (pName.equalsIgnoreCase(p.getName())) {
                    isPlayer.set(true);
                }
            });
        }

        if ((eventType == EventType.QUIT && onQuit) || (eventType == EventType.JOIN && onJoin)) {
            eventApply = true;
        }

        return hasPermission.get() && isPlayer.get() && eventApply;
    }

}
