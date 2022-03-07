package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class EditionFakerConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public EditionFakerConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (!Revolutionarity.getInstance().isGeyserInstalled()) {
            enabled = false;
            return;
        }

        if (this.config.getConfig().contains("editionFaker")) {
            this.enabled = this.config.getConfig().getBoolean("editionFaker");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        if (Revolutionarity.getInstance().isGeyserInstalled()) this.config.getConfig().set("editionFaker", this.enabled);
    }
}
