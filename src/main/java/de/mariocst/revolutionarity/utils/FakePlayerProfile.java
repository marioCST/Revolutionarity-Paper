package de.mariocst.revolutionarity.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class FakePlayerProfile implements PlayerProfile {
    private String name;

    public FakePlayerProfile(String name) {
        this.name = name;
    }

    @Override
    @Deprecated()
    public @Nullable UUID getUniqueId() {
        return null;
    }

    @Override
    public @Nullable String getName() {
        return name;
    }

    @Override
    @Deprecated(forRemoval = true)
    public @NotNull String setName(@Nullable String name) {
        this.name = name;
        assert name != null;
        return name;
    }

    @Override
    public @Nullable UUID getId() {
        return null;
    }

    @Override
    @Deprecated(forRemoval = true)
    public @Nullable UUID setId(@Nullable UUID uuid) {
        return null;
    }

    @Override
    public @NotNull PlayerTextures getTextures() {
        return new FakePlayerTextures();
    }

    @Override
    public void setTextures(@Nullable PlayerTextures textures) {

    }

    @Override
    public @NotNull Set<ProfileProperty> getProperties() {
        Set<ProfileProperty> set = new HashSet<>();
        set.add(new ProfileProperty("null", "null"));
        return set;
    }

    @Override
    public boolean hasProperty(@Nullable String property) {
        return false;
    }

    @Override
    public void setProperty(@NotNull ProfileProperty property) {

    }

    @Override
    public void setProperties(@NotNull Collection<ProfileProperty> properties) {

    }

    @Override
    public boolean removeProperty(@Nullable String property) {
        return false;
    }

    @Override
    public void clearProperties() {

    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public @NotNull CompletableFuture<org.bukkit.profile.PlayerProfile> update() {
        return null;
    }

    @Override
    public @NotNull PlayerProfile clone() {
        return new FakePlayerProfile(this.name);
    }

    @Override
    public boolean completeFromCache() {
        return false;
    }

    @Override
    public boolean completeFromCache(boolean onlineMode) {
        return false;
    }

    @Override
    public boolean completeFromCache(boolean lookupUUID, boolean onlineMode) {
        return false;
    }

    @Override
    public boolean complete(boolean textures) {
        return false;
    }

    @Override
    public boolean complete(boolean textures, boolean onlineMode) {
        return false;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }
}
