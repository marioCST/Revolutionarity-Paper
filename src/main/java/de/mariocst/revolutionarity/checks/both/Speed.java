package de.mariocst.revolutionarity.checks.both;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class Speed implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!Revolutionarity.getInstance().getSpeedConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.speed") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (player.isFlying()) return;

        Location old = event.getFrom();
        Location newLoc = event.getTo();

        double posX = newLoc.getX();
        double posZ = newLoc.getZ();

        double posXOld = old.getX();
        double posZOld = old.getZ();

        if (posX < 0) posX *= -1;
        if (posZ < 0) posZ *= -1;

        if (posXOld < 0) posXOld *= -1;
        if (posZOld < 0) posZOld *= -1;

        double diffX = posX - posXOld;
        double diffZ = posZ - posZOld;

        if (diffX < 0) diffX *= -1;
        if (diffZ < 0) diffZ *= -1;

        diffX *= player.getServer().getTPS()[0];
        diffZ *= player.getServer().getTPS()[0];

        double speed = Math.sqrt((diffX * diffX) + (diffZ * diffZ));

        if (Revolutionarity.getInstance().getTickTask().ticksToSpare.containsKey(player)) return;

        double maxSpeed = 4.8;

        if (player.isSprinting()) {
            maxSpeed = 5.76;
        }

        if (old.getY() > newLoc.getY() && player.isOnGround()) {
            maxSpeed *= 1.38;
        }

        if (!player.isOnGround()) {
            speed /= 1.5f;
            maxSpeed *= 1.5;
        }

        if (player.getWorld().getBlockAt(player.getLocation().add(0.0, 2.2, 0.0)).getType() != Material.AIR &&
                player.getWorld().getBlockAt(player.getLocation().add(0.0, 2.2, 0.0)).isSolid()) {
            maxSpeed *= 1.1;
        }

        if (player.getPotionEffect(PotionEffectType.SPEED) != null) {
            if (player.isSprinting()) {
                double multiplier = 1.04 + player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() * 0.02;

                maxSpeed *= multiplier;
            }
            else {
                double multiplier = 1 + player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() * 0.4;

                maxSpeed *= multiplier;
            }
        }

        if (player.isOnGround() &&
                this.isIce(player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)))) {
            maxSpeed *= 1.3;
        }

        if (speed > maxSpeed) {
            Revolutionarity.getInstance().flag(player, "Speed", 1.0, "Speed: " + speed + "/" + maxSpeed);
            event.setCancelled(true);
            player.teleport(event.getFrom());
            Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 2);
        }
    }

    @EventHandler
    public void onMove2(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.getFrom().getY() > event.getTo().getY() && player.isOnGround()) {
            Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 10);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, Revolutionarity.getInstance().getSpeedConfig().getSpareTicksOnHit());
    }

    private boolean isIce(Block block) {
        return block.getType() == Material.ICE ||
                block.getType() == Material.BLUE_ICE ||
                block.getType() == Material.PACKED_ICE ||
                block.getType() == Material.FROSTED_ICE;
    }
}
