package com.game.ui.model;

import com.badlogic.gdx.math.Vector2;
import com.game.GdxGame;
import com.game.audio.AudioManager;
import com.game.config.Constants;

import java.util.Map;

public class GameViewModel extends ViewModel {
    private final AudioManager audioManager;
    private int lifePoints;
    private int maxLife;
    private Map.Entry<Vector2, Integer> playerDamage;
    private final Vector2 tmpVec2;

    public GameViewModel(GdxGame game) {
        super(game);
        this.audioManager = game.getAudioManager();
        this.lifePoints = 0;
        this.maxLife = 0;
        this.playerDamage = null;
        this.tmpVec2 = new Vector2();
    }

    public void playerDamage(int amount, float x, float y) {
        Vector2 position = new Vector2(x, y);
        this.playerDamage = Map.entry(position, amount);
        this.propertyChangeSupport.firePropertyChange(Constants.PLAYER_DAMAGE, null, this.playerDamage);
    }
}
