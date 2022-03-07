package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class IllegalBlockInteractionConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public IllegalBlockInteractionConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("illegalBlockPlace")) {
            this.enabled = this.config.getConfig().getBoolean("illegalBlockPlace");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        this.config.getConfig().set("illegalBlockPlace", this.enabled);
    }
}
