package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class ToolBoxConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public ToolBoxConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (!Revolutionarity.getInstance().isGeyserInstalled()) {
            enabled = false;
            return;
        }

        if (this.config.getConfig().contains("toolBox")) {
            this.enabled = this.config.getConfig().getBoolean("toolBox");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        if (Revolutionarity.getInstance().isGeyserInstalled()) this.config.getConfig().set("toolBox", this.enabled);
    }
}
