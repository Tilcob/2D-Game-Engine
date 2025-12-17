package com.game.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.game.input.Command;
import org.joml.Matrix2d;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Component {
    public static final ComponentMapper<Controller> MAPPER = ComponentMapper.getFor(Controller.class);

    private final List<Command> pressedCommand;
    private final List<Command> releasedCommand;

    public Controller() {
        this.pressedCommand = new ArrayList<>();
        this.releasedCommand = new ArrayList<>();
    }

    public List<Command> getPressedCommands() {
        return pressedCommand;
    }

    public List<Command> getReleasedCommands() {
        return releasedCommand;
    }
}
