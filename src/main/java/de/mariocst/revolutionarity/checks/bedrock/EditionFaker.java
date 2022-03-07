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

public class EditionFaker implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!Revolutionarity.getInstance().getEditionFakerConfig().isEnabled()) return;

        Player player = event.getPlayer();

        if (GeyserImpl.getInstance().connectionByUuid(player.getUniqueId()) == null) return;

        if (player.hasPermission("revolutionarity.bypass.editionfaker") ||
                player.hasPermission("revolutionarity.bypass.*") ||
                player.hasPermission("revolutionarity.*") ||
                player.hasPermission("*") ||
                player.isOp()) return;

        GeyserSession session = GeyserImpl.getInstance().connectionByUuid(player.getUniqueId());
        BedrockClientData data = session.getClientData();

        // I don't know the device models of all other versions (except windows)
        // Calling data.getDeviceOs() is causing a LinkageError for some weird reason
        if (!this.translateDeviceOSDueToGeyserBeingShit(data.getDeviceOs()).equalsIgnoreCase("android") &&
                !this.translateDeviceOSDueToGeyserBeingShit(data.getDeviceOs()).equalsIgnoreCase("ios")) return;

        if (data.getDeviceModel().equals("")) {
            Revolutionarity.getInstance().flag(player, "EditionFaker", Revolutionarity.getInstance().getVelo().getMaxVelo());
        }
    }

    private String translateDeviceOSDueToGeyserBeingShit(DeviceOs os) {
        return os.name();
    }
}
