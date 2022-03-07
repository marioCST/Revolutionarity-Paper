package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class CustomPlayerList {
    private final MainConfig config;

    @Getter
    private final boolean customPlayerList;

    public CustomPlayerList() {
        this.config = Revolutionarity.getInstance().getMainConfig();

        if (this.config.getConfig().contains("customPlayerList")) {
            this.customPlayerList = this.config.getConfig().getBoolean("customPlayerList");
        }
        else {
            this.customPlayerList = false;
        }
    }

    public void save() {
        this.config.getConfig().set("customPlayerList", this.customPlayerList);
    }
}
