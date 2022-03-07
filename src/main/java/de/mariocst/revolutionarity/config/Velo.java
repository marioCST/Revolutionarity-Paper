package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class Velo {
    private final CheckConfig config;

    @Getter
    private final double maxVelo;

    public Velo() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("maxVelo")) {
            this.maxVelo = this.config.getConfig().getDouble("maxVelo");
        }
        else {
            this.maxVelo = 20;
        }
    }

    public void save() {
        this.config.getConfig().set("maxVelo", this.maxVelo);
    }
}
