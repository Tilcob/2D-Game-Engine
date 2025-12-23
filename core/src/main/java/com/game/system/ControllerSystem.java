package com.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.game.GdxGame;
import com.game.component.Attack;
import com.game.component.Controller;
import com.game.component.Move;
import com.game.input.Command;
import com.game.screen.MenuScreen;

public class ControllerSystem extends IteratingSystem {
    private final GdxGame game;

    public ControllerSystem(GdxGame game) {
        super(Family.all(Controller.class).get());
        this.game = game;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Controller controller = Controller.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        if (controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty()) return;
        if (move == null) return;
        if (move.isRooted()) return;

        float dx = move.getDirection().x;
        float dy = move.getDirection().y;

        for (Command command : controller.getPressedCommands()) {
            switch (command) {
                case UP -> dy = 1;
                case DOWN -> dy = -1;
                case LEFT -> dx = -1;
                case RIGHT -> dx = 1;
                case SELECT -> startEntityAttack(entity);
                case CANCEL -> game.setScreen(MenuScreen.class);
            }
        }

        for (Command command : controller.getReleasedCommands()) {
            switch (command) {
                case UP, DOWN -> dy = 0;
                case LEFT, RIGHT -> dx = 0;
            }
        }

        move.getDirection().set(dx, dy);

        controller.getPressedCommands().clear();
        controller.getReleasedCommands().clear();
    }

    private void startEntityAttack(Entity entity) {
        Attack attack = Attack.MAPPER.get(entity);
        if (attack != null && attack.canAttack()) {
            attack.startAttack();
        }
    }
}
