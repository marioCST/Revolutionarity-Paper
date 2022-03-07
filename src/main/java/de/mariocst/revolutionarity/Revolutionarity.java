package de.mariocst.revolutionarity;

import de.mariocst.revolutionarity.checks.both.*;
import de.mariocst.revolutionarity.checks.java.*;
import de.mariocst.revolutionarity.commands.RevolutionarityCommand;
import de.mariocst.revolutionarity.config.*;
import de.mariocst.revolutionarity.listeners.JoinListener;
import de.mariocst.revolutionarity.listeners.ServerListPingListener;
import de.mariocst.revolutionarity.tasks.TickTask;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Revolutionarity extends JavaPlugin {
    @Getter
    private static Revolutionarity instance;

    @Getter
    @Setter
    private String prefix;

    @Getter
    private MainConfig mainConfig;

    @Getter
    private CustomPlayerList customPlayerList;

    @Getter
    private KickMessageConfig kickMessageConfig;

    @Getter
    private TickTaskPeriod tickTaskPeriod;

    @Getter
    private CheckConfig checkConfig;

    @Getter
    private VeloConfig veloConfig;

    @Getter
    private AirJumpConfig airJumpConfig;

    @Getter
    private EditionFakerConfig editionFakerConfig;

    @Getter
    private FlySpeedConfig flySpeedConfig;

    @Getter
    private HighJumpConfig highJumpConfig;

    @Getter
    private IllegalBlockInteractionConfig illegalBlockInteractionConfig;

    @Getter
    private OnGroundSpoofConfig onGroundSpoofConfig;

    @Getter
    private ReachConfig reachConfig;

    @Getter
    private SpeedConfig speedConfig;

    @Getter
    private ToolBoxConfig toolBoxConfig;

    @Getter
    private Velo velo;

    private Prefix prefixConfig;

    @Getter
    private TickTask tickTask;

    @Getter
    private boolean isGeyserInstalled;

    @Getter
    private boolean isFloodgateInstalled;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        if (this.getServer().getPluginManager().getPlugin("Geyser-Spigot") != null) {
            isGeyserInstalled = true;
            this.getLogger().warning("Geyser is installed! Bedrock functionalities are enabled!");
        }
        else {
            isGeyserInstalled = false;
            this.getLogger().warning("Geyser is not installed! No bedrock functionalities will be enabled!");
        }

        if (isGeyserInstalled) {
            if (this.getServer().getPluginManager().getPlugin("floodgate") != null) {
                isFloodgateInstalled = true;
                this.getLogger().warning("Floodgate is installed! Additional Bedrock functionalities are enabled!");
            }
            else {
                isFloodgateInstalled = false;
                this.getLogger().warning("Floodgate is not installed! No additional bedrock functionalities will be enabled!");
            }
        }

        this.loadConfigs();

        Objects.requireNonNull(this.getCommand("revolutionarity")).setExecutor(new RevolutionarityCommand(this));
        Objects.requireNonNull(this.getCommand("revolutionarity")).setTabCompleter(new RevolutionarityCommand(this));

        this.registerListener();

        this.tickTask = new TickTask();

        this.log("Enabled Revolutionarity!");
    }

    @Override
    public void onDisable() {
        this.saveConfigs();

        this.tickTask.runnable.cancel();
        this.tickTask.spare.cancel();

        this.log("Disabled Revolutionarity!");
    }

    public void loadConfigs() {
        this.mainConfig = new MainConfig();
        this.customPlayerList = new CustomPlayerList();
        this.kickMessageConfig = new KickMessageConfig();
        this.prefixConfig = new Prefix();
        this.tickTaskPeriod = new TickTaskPeriod();

        this.checkConfig = new CheckConfig();
        this.airJumpConfig = new AirJumpConfig();
        this.editionFakerConfig = new EditionFakerConfig();
        this.flySpeedConfig = new FlySpeedConfig();
        this.highJumpConfig = new HighJumpConfig();
        this.illegalBlockInteractionConfig = new IllegalBlockInteractionConfig();
        this.onGroundSpoofConfig = new OnGroundSpoofConfig();
        this.reachConfig = new ReachConfig();
        this.speedConfig = new SpeedConfig();
        this.toolBoxConfig = new ToolBoxConfig();
        this.velo = new Velo();

        this.veloConfig = new VeloConfig(this.getDataFolder());
    }

    public void saveConfigs() {
        this.customPlayerList.save();
        this.kickMessageConfig.save();
        this.prefixConfig.save();
        this.tickTaskPeriod.save();
        this.mainConfig.save();

        this.airJumpConfig.save();
        this.editionFakerConfig.save();
        this.flySpeedConfig.save();
        this.highJumpConfig.save();
        this.illegalBlockInteractionConfig.save();
        this.onGroundSpoofConfig.save();
        this.reachConfig.save();
        this.speedConfig.save();
        this.toolBoxConfig.save();
        this.velo.save();
        this.checkConfig.save();

        this.veloConfig.save();
    }

    private void registerListener() {
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);
        this.getServer().getPluginManager().registerEvents(new ServerListPingListener(), this);

        this.getServer().getPluginManager().registerEvents(new AirJump(), this);
        this.getServer().getPluginManager().registerEvents(new FlySpeed(), this);
        this.getServer().getPluginManager().registerEvents(new HighJump(), this);
        this.getServer().getPluginManager().registerEvents(new IllegalBlockInteraction(), this);
        this.getServer().getPluginManager().registerEvents(new OnGroundSpoof(), this);
        this.getServer().getPluginManager().registerEvents(new Reach(), this);
        this.getServer().getPluginManager().registerEvents(new Speed(), this);

        if (!this.isGeyserInstalled) return;

        /*this.getServer().getPluginManager().registerEvents(new EditionFaker(), this);
        this.getServer().getPluginManager().registerEvents(new ToolBox(), this);*/
    }

    public void log(String msg) {
        this.getLogger().info(this.prefix + msg);
    }

    public void warning(String msg) {
        this.getLogger().warning(this.prefix + msg);
    }

    public void teleportToLastOnGroundLocation(Player player) {
        if (this.getTickTask().getLastOnGround().containsKey(player)) {
            player.teleport(this.getTickTask().getLastOnGround().get(player));
        }
    }

    public void flag(Player flagged, String check, double velo) {
        this.flag(flagged, check, velo, "");
    }

    public void flag(Player flagged, String check, double velo, String details) {
        String det = details.equals("") ? "" : " Details: " + details;

        this.veloConfig.addVelo(flagged.getName(), velo);
        this.veloConfig.save();

        this.log("The player " + flagged.getName() + " got flagged for " + check + "!" + det);

        for (Player player : this.getServer().getOnlinePlayers()) {
            if (player.hasPermission("revolutionarity.staff") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) {
                player.sendMessage(this.prefix + "The player " + flagged.getName() + " got flagged for " + check + "!" + det);
            }
        }

        if (this.veloConfig.getVelo(flagged.getName()) >= this.velo.getMaxVelo()) {
            flagged.kick(Component.text(this.kickMessageConfig.getKickMessage().replaceAll("%newline%", "\n")), PlayerKickEvent.Cause.UNKNOWN);

            this.veloConfig.resetVelo(flagged.getName());

            this.log("The player " + flagged.getName() + " got kicked for cheating!");

            for (Player player : this.getServer().getOnlinePlayers()) {
                if (player.hasPermission("revolutionarity.staff") || player.hasPermission("revolutionarity.*") || player.hasPermission("*") || player.isOp()) {
                    player.sendMessage(this.prefix + "The player " + flagged.getName() + " got kicked for cheating!");
                }
            }
        }
    }
}
