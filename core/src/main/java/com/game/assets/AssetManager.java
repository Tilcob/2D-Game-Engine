package com.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public class AssetManager implements Disposable {
    private final com.badlogic.gdx.assets.AssetManager assetManager;

    public AssetManager(FileHandleResolver resolver) {
        this.assetManager = new com.badlogic.gdx.assets.AssetManager(resolver);
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

    public <T> void unload(Asset<T> asset) {
        assetManager.unload(asset.getAssetDescriptor().fileName);
    }

    public boolean update() {
        return assetManager.update();
    }

    public void debugDiagnostics() {
        Gdx.app.debug("AssetManager", assetManager.getDiagnostics());
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
