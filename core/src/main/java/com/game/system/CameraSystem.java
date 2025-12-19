package com.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.component.CameraFollow;
import com.game.component.Transform;
import com.game.config.Constants;

public class CameraSystem extends IteratingSystem {
    private final Camera camera;
    private final Vector2 targetPosition;
    private float mapWidth;
    private float mapHeight;

    public CameraSystem(Camera camera) {
        super(Family.all(CameraFollow.class, Transform.class).get());
        this.camera = camera;
        this.targetPosition = new Vector2();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);

        calcTargetPosition(transform.getPosition());
        camera.position.set(targetPosition.x, targetPosition.y, camera.position.z);
    }

    private void calcTargetPosition(Vector2 entityPosition) {
        float targetX = entityPosition.x;
        float targetY = entityPosition.y;
        float cameraHalfWidth = camera.viewportWidth * .5f;
        float cameraHalfHeight = camera.viewportHeight * .5f;

        if (mapWidth > cameraHalfWidth) {
            float min = Math.min(cameraHalfWidth, mapWidth - cameraHalfWidth);
            float max = Math.max(cameraHalfWidth, mapWidth - cameraHalfWidth);
            targetX = MathUtils.clamp(targetX, min, max);
        }

        if (mapHeight > cameraHalfHeight) {
            float min = Math.min(cameraHalfHeight, mapHeight - cameraHalfHeight);
            float max = Math.max(cameraHalfHeight, mapHeight - cameraHalfHeight);
            targetY = MathUtils.clamp(targetY, min, max);
        }

        this.targetPosition.set(targetX, targetY);
    }

    public void setMap(TiledMap map) {
        int width = map.getProperties().get(Constants.MAP_WIDTH, 0, Integer.class);
        int height = map.getProperties().get(Constants.MAP_HEIGHT, 0, Integer.class);
        int tileWidth = map.getProperties().get(Constants.TILE_WIDTH, 0, Integer.class);
        int tileHeight = map.getProperties().get(Constants.TILE_HEIGHT, 0, Integer.class);

        this.mapWidth = width * tileWidth * Constants.UNIT_SCALE;
        this.mapHeight = height * tileHeight * Constants.UNIT_SCALE;

        Entity camEntity = getEntities().first();
        if (camEntity == null) return;

        processEntity(camEntity, 0);
    }
}
