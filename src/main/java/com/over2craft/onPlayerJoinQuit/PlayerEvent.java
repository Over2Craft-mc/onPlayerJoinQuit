package com.over2craft.onPlayerJoinQuit;

import com.over2craft.onPlayerJoinQuit.config.ConfigSection;
import com.over2craft.onPlayerJoinQuit.config.EventType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PlayerEvent {

    ConfigSection cs;
    EventType eventType;
    Player p;

    private PlayerEvent(EventType eventType, ConfigSection cs, Player p) {
        this.eventType = eventType;
        this.cs = cs;
        this.p = p;
    }

    public static List<PlayerEvent> retrieve(Player player, EventType eventType) {

        List<PlayerEvent> playerEvents = new ArrayList<>();

        Main.configSections.forEach((cs -> {
            if (cs.doesSectionApply(player, eventType)) {
                PlayerEvent playerEvent = new PlayerEvent(eventType, cs, player);
                playerEvents.add(playerEvent);
            }
        }));

        return playerEvents;
    }

    public void joinMessage(PlayerJoinEvent e) {
        if (cs.doesSectionApply(p, eventType) && cs.joinMessageEnabled) {
            e.setJoinMessage(replacePlaceHolder(cs.joinMessage));
        }
    }

    public void quitMessage(PlayerQuitEvent e) {
        if (cs.doesSectionApply(p, eventType) && cs.quitMessageEnabled) {
            e.setQuitMessage(replacePlaceHolder(cs.joinMessage));
        }
    }

    public void sendConsoleCommand() {

        if (cs.doesSectionApply(p, eventType)) {
            cs.commandsAsConsole.forEach((command) -> Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), replacePlaceHolder(command)));
        }
    }

    public void sendCommandAsPlayer() {
        if (cs.doesSectionApply(p, eventType) && eventType == EventType.JOIN) {
            cs.commandsAsConsole.forEach((command) -> p.performCommand(replacePlaceHolder(command)));
        }
    }

    private String replacePlaceHolder(String str)
    {
        str = str.replaceAll(Pattern.quote("{playerName}"), p.getName());
        str = str.replaceAll(Pattern.quote("{playerDisplayName}"), p.getDisplayName());
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            str = PlaceholderAPI.setPlaceholders(p, str);
        }

        return str;
    }


}
