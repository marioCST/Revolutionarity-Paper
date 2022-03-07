package de.mariocst.revolutionarity.utils;

import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;

public class FakePlayerTextures implements PlayerTextures {
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void clear() {

    }

    @Override
    public @Nullable URL getSkin() {
        return null;
    }

    @Override
    public void setSkin(@Nullable URL skinUrl) {

    }

    @Override
    public void setSkin(@Nullable URL skinUrl, @Nullable SkinModel skinModel) {

    }

    @Override
    public @NotNull SkinModel getSkinModel() {
        return SkinModel.CLASSIC;
    }

    @Override
    public @Nullable URL getCape() {
        return null;
    }

    @Override
    public void setCape(@Nullable URL capeUrl) {

    }

    @Override
    public long getTimestamp() {
        return 0;
    }

    @Override
    public boolean isSigned() {
        return false;
    }
}
