package de.mariocst.revolutionarity.checks.java;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AirJump implements Listener {
    @EventHandler
    public void onJump(PlayerJumpEvent event) {
        if (!Revolutionarity.getInstance().getAirJumpConfig().isEnabled()) return;

        Player player = event.getPlayer();

        // Different check for Geyser players required
        if (Revolutionarity.getInstance().isGeyserInstalled()) {
            if (org.geysermc.geyser.GeyserImpl.getInstance().connectionByUuid(player.getUniqueId()) != null) return;
        }

        if (player.hasPermission("revolutionarity.bypass.airjump") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        /** This is perfectly fine to use due to the OnGround spoof check
         * {@link OnGroundSpoof}
         */
        if (!player.isOnGround()) {
            Revolutionarity.getInstance().flag(player, "AirJump", 1.0);
            Revolutionarity.getInstance().teleportToLastOnGroundLocation(player);
        }
    }
}
