package de.mariocst.revolutionarity.checks.both;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class IllegalBlockInteraction implements Listener {
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!Revolutionarity.getInstance().getIllegalBlockInteractionConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.illegalblockinteraction") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (!player.getInventory().getItemInMainHand().getType().isBlock()) {
            Revolutionarity.getInstance().flag(player, "IllegalBlockPlace", 1.0, "Item is not a block");
            event.setCancelled(true);
        }

        Block block = event.getBlock();

        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        Location pl = player.getLocation().add(0.5, player.getEyeHeight(), 0.5);

        double dist = pl.distance(loc);

        if (dist > 5.65) {
            Revolutionarity.getInstance().flag(player, "IllegalBlockReach", 1.0, "Reach: " + dist + "/" + 5.65);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!Revolutionarity.getInstance().getIllegalBlockInteractionConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.illegalblockinteraction") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        Block block = event.getBlock();

        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        Location pl = player.getLocation().add(0.5, player.getEyeHeight(), 0.5);

        double dist = pl.distance(loc);

        if (dist > 5.65) {
            Revolutionarity.getInstance().flag(player, "IllegalBlockReach", 1.0, "Reach: " + dist + "/" + 5.65);
            event.setCancelled(true);
        }
    }
}
