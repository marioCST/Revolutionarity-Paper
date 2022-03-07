package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class OnGroundSpoofConfig {
    private final CheckConfig config;

    @Getter
    private final boolean enabled;

    public OnGroundSpoofConfig() {
        this.config = Revolutionarity.getInstance().getCheckConfig();

        if (this.config.getConfig().contains("onGroundSpoof")) {
            this.enabled = this.config.getConfig().getBoolean("onGroundSpoof");
        }
        else {
            this.enabled = true;
        }
    }

    public void save() {
        this.config.getConfig().set("onGroundSpoof", this.enabled);
    }
}
