package de.mariocst.revolutionarity.config;

import de.mariocst.revolutionarity.Revolutionarity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class VeloConfig {
    private final File file;
    private final List<String> velo = new ArrayList<>();

    public VeloConfig(File dir) {
        if (!dir.exists()) {
            try {
                Files.createDirectory(dir.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
                Revolutionarity.getInstance().warning("§cError while creating directory!");
            }
        }

        this.file = new File(dir, "velo.txt");

        if (!this.file.exists()) {
            try {
                Files.createFile(this.file.toPath());
            }
            catch (IOException e) {
                e.printStackTrace();
                Revolutionarity.getInstance().warning("§cError while creating velo file!");
            }
        }

        try {
            this.velo.addAll(Files.readAllLines(this.file.toPath()));
        }
        catch (IOException e) {
            e.printStackTrace();
            Revolutionarity.getInstance().warning("§cError while reading velo file!");
        }
    }

    public void addPlayer(String player) {
        this.addPlayer(player, 0.0);
    }

    public void addPlayer(String player, double velo) {
        this.velo.removeIf(string -> string.startsWith(player));
        this.velo.add(player + ":" + velo);
    }

    public boolean containsPlayer(String player) {
        for (String string : this.velo) {
            if (string.startsWith(player)) {
                return true;
            }
        }
        return false;
    }

    public void addVelo(String player, double amount) {
        this.addPlayer(player, this.getVelo(player) + amount);
    }

    public double getVelo(String player) {
        for (String string : this.velo) {
            if (string.startsWith(player)) {
                String[] strings = string.split(":");

                return Double.parseDouble(strings[1]);
            }
        }
        return 0.0;
    }

    public void setVelo(String player, double velo) {
        this.addPlayer(player, velo);
    }

    public void resetVelo(String player) {
        this.addPlayer(player);
    }

    public void save() {
        try {
            Files.write(this.file.toPath(), this.velo);
        }
        catch (IOException e) {
            e.printStackTrace();
            Revolutionarity.getInstance().warning("§cError while writing to velo file!");
        }
    }
}
