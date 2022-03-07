package de.mariocst.revolutionarity.checks.bedrock;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.util.DeviceOs;
import org.geysermc.geyser.GeyserImpl;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.session.auth.BedrockClientData;

public class ToolBox implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Revolutionarity.getInstance().getToolBoxConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (GeyserImpl.getInstance().connectionByUuid(player.getUniqueId()) == null) return;

        if (player.hasPermission("revolutionarity.bypass.toolbox") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        GeyserSession session = GeyserImpl.getInstance().connectionByUuid(player.getUniqueId());
        BedrockClientData data = session.getClientData();

        // Calling data.getDeviceOs() is causing a LinkageError for some weird reason
        if (!this.translateDeviceOSDueToGeyserBeingShit(data.getDeviceOs()).equalsIgnoreCase("android")) return;

        if (!data.getDeviceModel().equals(data.getDeviceModel().toUpperCase())) {
            Revolutionarity.getInstance().flag(player, "ToolBox", Revolutionarity.getInstance().getVelo().getMaxVelo());
        }
    }

    private String translateDeviceOSDueToGeyserBeingShit(DeviceOs os) {
        return os.name();
    }
}
