package de.mariocst.revolutionarity.listeners;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!Revolutionarity.getInstance().getVeloConfig().containsPlayer(player.getName())) {
            Revolutionarity.getInstance().getVeloConfig().addPlayer(player.getName());
        }
    }
}
