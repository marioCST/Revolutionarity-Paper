package de.mariocst.revolutionarity.checks.both;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Reach implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        if (!Revolutionarity.getInstance().getReachConfig().isEnabled()) return;

        if (player.hasPermission("revolutionarity.bypass.reach") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        Entity damaged = event.getEntity();

        double survivalDistance = Revolutionarity.getInstance().getReachConfig().getMaxReach();
        double maxReach = player.getGameMode() == GameMode.CREATIVE ? 6.0 : survivalDistance + this.getDistModifier(damaged);

        double reach;

        double playerY = player.getLocation().getY();

        Location hitLoc = player.getLocation().add(0.0, player.getEyeHeight(), 0.0);

        Location foot = damaged.getLocation().add(0.0, 0.5, 0.0);
        Location head = damaged.getLocation().add(0.0, 1.5, 0.0);

        if (damaged instanceof Enderman) {
            Location realHead = damaged.getLocation().add(0.0, 2.5, 0.0);

            if (playerY < head.getY() - 0.5) {
                reach = hitLoc.distance(foot);
                maxReach += 0.1;
            }
            else if (playerY >= head.getY() - 0.5 && playerY < realHead.getY() - 0.5) {
                reach = hitLoc.distance(head);
            }
            else {
                reach = hitLoc.distance(realHead);
            }
        }
        else {
            if (playerY < head.getY() - 0.5) {
                reach = hitLoc.distance(foot);
                maxReach += 0.1;
            }
            else {
                reach = hitLoc.distance(head);
            }
        }

        if (reach > maxReach) {
            Revolutionarity.getInstance().flag(player, "Reach Java", 1.0, "Reach: " + reach + "/" + maxReach);
            event.setCancelled(true);
        }
    }

    // NoCheatPlus
    private double getDistModifier(Entity damaged) {
        if (damaged instanceof EnderDragon) {
            return 6.5;
        }
        else if (damaged instanceof Giant) {
            return 1.5;
        }
        else {
            return 0.0;
        }
    }
}
