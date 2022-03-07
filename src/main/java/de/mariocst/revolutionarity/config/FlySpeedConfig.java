package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class FlySpeedConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public FlySpeedConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("flySpeed")) {
            this.enabled = this.config.getConfig().getBoolean("flySpeed");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        this.config.getConfig().set("flySpeed", this.enabled);
    }
}
