package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class SpeedConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    @Getter
    private final int spareTicksOnHit;

    public SpeedConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("speed")) {
            this.enabled = this.config.getConfig().getBoolean("speed");
        }
        else {
            this.enabled = true;
        }

        if (this.config.getConfig().contains("speedSpareTicksOnHit")) {
            this.spareTicksOnHit = this.config.getConfig().getInt("speedSpareTicksOnHit");
        }
        else {
            this.spareTicksOnHit = 60;
        }
    }

    public void save() {
        this.config.getConfig().set("speed", this.enabled);
        this.config.getConfig().set("speedSpareTicksOnHit", this.spareTicksOnHit);
    }
}
