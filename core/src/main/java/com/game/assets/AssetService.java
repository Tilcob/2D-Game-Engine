package com.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class AssetService implements Disposable {
    private final AssetManager assetManager;

    public AssetService(FileHandleResolver resolver) {
        this.assetManager = new AssetManager(resolver);
        this.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
    }

    public <T> T load(Asset<T> asset) {
        assetManager.load(asset.getAssetDescriptor());
        assetManager.finishLoading();
        return assetManager.get(asset.getAssetDescriptor());
    }

    public <T> void queue(Asset<T> asset) {
        assetManager.load(asset.getAssetDescriptor());
    }

    public <T> T get(Asset<T> asset) {
        return assetManager.get(asset.getAssetDescriptor());
    }

    public boolean update() {
        return assetManager.update();
    }

    public void debugDiagnostics() {
        Gdx.app.debug("AssetService", assetManager.getDiagnostics());
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
