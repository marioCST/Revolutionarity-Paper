package de.mariocst.revolutionarity.tasks;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class TickTask {
    @Getter
    private final Map<Player, Location> lastOnGround = new HashMap<>();

    public final Map<Player, Integer> ticksToSpare = new HashMap<>();

    public final BukkitTask runnable;
    public final BukkitTask spare;

    public TickTask() {
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Revolutionarity.getInstance().getServer().getOnlinePlayers()) {
                    if (player.getWorld().getBlockAt(player.getLocation().subtract(0.0, 0.2, 0.0)).getType() != Material.AIR || player.isFlying()) {
                        lastOnGround.remove(player);
                        lastOnGround.put(player, player.getLocation());
                    }
                }
            }
        }.runTaskTimer(Revolutionarity.getInstance(), Revolutionarity.getInstance().getTickTaskPeriod().getPeriod(), Revolutionarity.getInstance().getTickTaskPeriod().getPeriod());

        this.spare = new BukkitRunnable() {
            @Override
            public void run() {
                if (ticksToSpare.isEmpty()) return;

                for (Player player : ticksToSpare.keySet()) {
                    int spare = ticksToSpare.get(player) - 1;

                    ticksToSpare.remove(player);

                    if (spare == 0) return;

                    ticksToSpare.put(player, spare);
                }
            }
        }.runTaskTimer(Revolutionarity.getInstance(), 1, 1);
    }
}
