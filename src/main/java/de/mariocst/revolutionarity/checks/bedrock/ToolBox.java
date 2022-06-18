package de.mariocst.revolutionarity.checks.bedrock;

import de.mariocst.revolutionarity.Revolutionarity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;
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

        FloodgatePlayer fPlayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());

        if (fPlayer == null) return;

        if (fPlayer.getDeviceOs() != DeviceOs.GOOGLE) return;

        if (!data.getDeviceModel().equals(data.getDeviceModel().toUpperCase())) {
            Revolutionarity.getInstance().flag(player, "ToolBox", Revolutionarity.getInstance().getVelo().getMaxVelo());
        }
    }
}
