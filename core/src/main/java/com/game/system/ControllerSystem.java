package com.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.assets.SoundAsset;
import com.game.audio.AudioManager;
import com.game.component.Controller;
import com.game.component.Move;
import com.game.input.Command;

public class ControllerSystem extends IteratingSystem {
    private final AudioManager audioManager;

    public ControllerSystem(AudioManager audioManager) {
        super(Family.all(Controller.class).get());

        this.audioManager = audioManager;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);
        if (controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty()) return;

        for (Command command : controller.getPressedCommands()) {
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);

            }
        }
        controller.getPressedCommands().clear();

        for (Command command : controller.getReleasedCommands()) {
            switch (command) {
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
                case SELECT -> startEntityAttack(entity);
            }
        }
        controller.getReleasedCommands().clear();
    }

    private void startEntityAttack(Entity entity) {
        audioManager.playSound(SoundAsset.SWORD_HIT);
    }

    private void moveEntity(Entity entity, float directionX, float directionY) {
        Move move = Move.MAPPER.get(entity);
        if (move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}
