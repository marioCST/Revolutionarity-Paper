package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class AirJumpConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public AirJumpConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("airJump")) {
            this.enabled = this.config.getConfig().getBoolean("airJump");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        this.config.getConfig().set("airJump", this.enabled);
    }
}
