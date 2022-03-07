package de.mariocst.revolutionarity.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import de.mariocst.revolutionarity.Revolutionarity;
import de.mariocst.revolutionarity.utils.FakePlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServerListPingListener implements Listener {
    @EventHandler
    public void onPing(PaperServerListPingEvent event) {
        if (!Revolutionarity.getInstance().getCustomPlayerList().isCustomPlayerList()) return;

        int players = 0;

        for (Player ignored : Revolutionarity.getInstance().getServer().getOnlinePlayers()) {
            players++;
        }

        event.setNumPlayers(players);

        event.getPlayerSample().clear();
        event.getPlayerSample().add(new FakePlayerProfile("\u00A71\u00A7lThis server ist running"));
        event.getPlayerSample().add(new FakePlayerProfile("\u00A71\u00A7lRevolutionarity AntiCheat!"));
    }
}
