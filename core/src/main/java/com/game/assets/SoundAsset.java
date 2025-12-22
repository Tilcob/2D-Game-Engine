package com.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound> {
    SWING("sword_hit.wav");

    private final AssetDescriptor<Sound> descriptor;
    SoundAsset(String fileName) {
        this.descriptor = new AssetDescriptor<>("audio/" + fileName, Sound.class);
    }


    @Override
    public AssetDescriptor<Sound> getAssetDescriptor() {
        return descriptor;
    }
}
