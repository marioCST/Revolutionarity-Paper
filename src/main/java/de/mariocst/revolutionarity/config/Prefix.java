package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;

public class Prefix {
    private final MainConfig config;

    private final String prefix;

    public Prefix() {
        this.config = Revolutionarity.getInstance().getMainConfig();

        if (this.config.getConfig().contains("prefix")) {
            this.prefix = this.config.getConfig().getString("prefix");
        }
        else {
            this.prefix = "§8[§3Revolutionarity§8] | §f";
        }

        Revolutionarity.getInstance().setPrefix(this.prefix);
    }

    public void save() {
        this.config.getConfig().set("prefix", this.prefix);
    }
}
