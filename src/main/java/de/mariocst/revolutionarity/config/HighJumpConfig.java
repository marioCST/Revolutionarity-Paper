package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class HighJumpConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public HighJumpConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("highJump")) {
            this.enabled = this.config.getConfig().getBoolean("highJump");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        this.config.getConfig().set("highJump", this.enabled);
    }
}
