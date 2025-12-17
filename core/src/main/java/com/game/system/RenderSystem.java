package com.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.assets.AssetService;
import com.game.assets.MapAsset;
import com.game.component.Graphic;
import com.game.component.Transform;
import com.game.config.Constants;

import java.util.Comparator;

public class RenderSystem extends SortedIteratingSystem implements Disposable {
    private final OrthogonalTiledMapRenderer mapRenderer; // TiledMapRenderer is the parent type
    private final Viewport viewport;
    private  final Batch batch;
    private final OrthographicCamera camera;

    public RenderSystem(Batch batch, Viewport viewport) {
        super(
            Family.all(Transform.class, Graphic.class).get(),
            Comparator.comparing(Transform.MAPPER::get)
        );
        this.batch = batch;
        this.viewport = viewport;
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, Constants.UNIT_SCALE, this.batch);
    }

    @Override
    public void update(float deltaTime) {
        viewport.apply();
        batch.setColor(Color.WHITE);
        mapRenderer.setView(camera);
        mapRenderer.render();

        forceSort();
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float v) {
        Transform transform = Transform.MAPPER.get(entity);
        Graphic graphic = Graphic.MAPPER.get(entity);

        if (graphic.getRegion() == null) return;

        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();

        batch.setColor(graphic.getColor());
        batch.draw(
            graphic.getRegion(),
            position.x - size.x * (1 - scaling.x) * .5f,
            position.y - size.y * (1 - scaling.y) * .5f,
            size.x * .5f, size.y * .5f,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDegrees()
        );
        batch.setColor(Color.WHITE);
    }

    public void setMap(TiledMap map) {
        mapRenderer.setMap(map);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }
}
