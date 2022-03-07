package de.mariocst.revolutionarity.checks.java;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnGroundSpoof implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!Revolutionarity.getInstance().getOnGroundSpoofConfig().isEnabled()) return;

        Player player = event.getPlayer();

        // This Check doesn't work on BE for some reason
        // Horion: NoFall on Old
        // Zephyr: NoFall on Vanilla
        if (Revolutionarity.getInstance().isGeyserInstalled()) {
            if (org.geysermc.geyser.GeyserImpl.getInstance().connectionByUuid(player.getUniqueId()) != null) return;
        }

        if (player.hasPermission("revolutionarity.bypass.ongroundspoof") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (event.getFrom().getY() > event.getTo().getY() && player.isOnGround() &&
                (   (
                        player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)).getType() == Material.AIR &&
                        !player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)).isSolid()
                    ) ||
                player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)).getType() == Material.SNOW ||
                player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)).getType() == Material.POWDER_SNOW
                )
        ) {
            Revolutionarity.getInstance().flag(player, "OnGroundSpoof", 1.0);
            Revolutionarity.getInstance().teleportToLastOnGroundLocation(player);
        }
    }
}
