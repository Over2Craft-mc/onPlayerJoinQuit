package com.over2craft.onPlayerJoinQuit;

import com.over2craft.onPlayerJoinQuit.config.EventType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void PlayerQuitEvent(PlayerQuitEvent e) {
        PlayerEvent.retrieve(e.getPlayer(), EventType.QUIT).forEach((playerEvent -> {
            playerEvent.quitMessage(e);
            playerEvent.sendCommandAsPlayer();
            playerEvent.sendConsoleCommand();
        }));
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent e) {
        PlayerEvent.retrieve(e.getPlayer(), EventType.JOIN).forEach(playerEvent -> {
            playerEvent.joinMessage(e);
            playerEvent.sendCommandAsPlayer();
            playerEvent.sendConsoleCommand();
        });
    }

}
