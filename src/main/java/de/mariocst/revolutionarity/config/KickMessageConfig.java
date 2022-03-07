package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class KickMessageConfig {
    private final MainConfig config;

    @Getter
    private final String kickMessage;

    public KickMessageConfig() {
        this.config = Revolutionarity.getInstance().getMainConfig();

        if (this.config.getConfig().contains("kickMessage")) {
            this.kickMessage = this.config.getConfig().getString("kickMessage");
        }
        else {
            this.kickMessage = "§cKicked for cheating";
        }
    }

    public void save() {
        this.config.getConfig().set("kickMessage", this.kickMessage);
    }
}
