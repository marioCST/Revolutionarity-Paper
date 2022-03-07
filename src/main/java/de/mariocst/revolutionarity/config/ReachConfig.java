package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class ReachConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    @Getter
    private final double maxReach;

    public ReachConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("reach")) {
            this.enabled = this.config.getConfig().getBoolean("reach");
        }
        else {
            this.enabled = true;
        }

        if (this.config.getConfig().contains("maxReach")) {
            this.maxReach = this.config.getConfig().getDouble("maxReach");
        }
        else {
            this.maxReach = 3.54;
        }
    }

    public void save() {
        this.config.getConfig().set("reach", this.enabled);
        this.config.getConfig().set("maxReach", this.maxReach);
    }
}
