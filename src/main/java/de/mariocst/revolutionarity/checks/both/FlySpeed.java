package de.mariocst.revolutionarity.checks.both;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class FlySpeed implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!Revolutionarity.getInstance().getFlySpeedConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("revolutionarity.bypass.flyspeed") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        if (!player.isFlying() || player.getGameMode() == GameMode.SPECTATOR) return;

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

        double maxSpeed = 11.0;

        if (player.isSprinting()) {
            maxSpeed *= 2;
        }

        if (speed > maxSpeed) {
            Revolutionarity.getInstance().flag(player, "FlySpeed", 1.0, "Speed: " + speed + "/" + maxSpeed);
            player.teleport(Revolutionarity.getInstance().getTickTask().getLastOnGround().get(player));
            Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 2);
        }
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (event.isFlying()) return;

        Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 30);
    }

    @EventHandler
    public void onToggleSprint(PlayerToggleSprintEvent event) {
        Player player = event.getPlayer();

        if (event.isSprinting()) return;

        Revolutionarity.getInstance().getTickTask().ticksToSpare.put(player, 5);
    }
}
