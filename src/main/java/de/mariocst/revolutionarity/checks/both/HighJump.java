package de.mariocst.revolutionarity.checks.both;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class HighJump implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!Revolutionarity.getInstance().getHighJumpConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.highjump") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (player.isFlying()) return;

        Location old = event.getFrom();
        Location newLoc = event.getTo();

        double posY = newLoc.getY();

        double posYOld = old.getY();

        if (posY < 0) posY *= -1;

        if (posYOld < 0) posYOld *= -1;

        double speed = posY - posYOld;

        if (speed < 0) speed *= -1;

        speed *= player.getServer().getTPS()[0];

        if (Revolutionarity.getInstance().getTickTask().ticksToSpare.containsKey(player)) return;

        double maxSpeed = 8.5;

        if (event.getFrom().getY() < event.getTo().getY()) {
            if (player.getPotionEffect(PotionEffectType.JUMP) != null) {
                double multiplier = 1.0 + player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() * 0.24;

                maxSpeed *= multiplier;
            }
        }
        else {
            maxSpeed *= Double.MAX_VALUE; // Speed gets always higher
        }

        if (speed > maxSpeed) {
            Revolutionarity.getInstance().flag(player, "HighJump", 1.0, "Speed: " + speed + "/" + maxSpeed);
            player.teleport(Revolutionarity.getInstance().getTickTask().getLastOnGround().get(player));
            Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 2);
        }
    }
}
