package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;
import lombok.Getter;

public class TickTaskPeriod {
    private final MainConfig config;

    @Getter
    private final int period;

    public TickTaskPeriod() {
        this.config = Revolutionarity.getInstance().getMainConfig();

        if (this.config.getConfig().contains("tickTaskPeriod")) {
            this.period = this.config.getConfig().getInt("tickTaskPeriod");
        }
        else {
            this.period = 3;
        }
    }

    public void save() {
        this.config.getConfig().set("tickTaskPeriod", this.period);
    }
}
