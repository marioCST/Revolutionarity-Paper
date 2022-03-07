package de.mariocst.revolutionarity.config;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainConfig {
    private final File file;

    @Getter
    private final YamlConfiguration config;

    public MainConfig() {
        File dir = new File("./plugins/Revolutionarity");

        if (!dir.exists()) {
            try {
                Files.createDirectory(dir.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.file = new File(dir, "config.yml");

        if (!this.file.exists()) {
            try {
                Files.createFile(this.file.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.config.save(this.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
