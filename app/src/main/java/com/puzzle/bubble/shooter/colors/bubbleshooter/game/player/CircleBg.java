package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;

public class CircleBg extends Sprite {
    public final float mRotationSpeed = -0.05f;

    public CircleBg(Game game) {
        super(game, R.drawable.player_circle);
        this.mLayer = 1;
    }

    public void onStart() {
        this.mX = (((float) this.mGame.getScreenWidth()) / 2.0f) - (((float) this.mWidth) / 2.0f);
        this.mY = (((float) (this.mGame.getScreenHeight() * 4)) / 5.0f) - (((float) this.mHeight) / 2.0f);
    }

    public void onUpdate(long elapsedMillis) {
        this.mRotation += this.mRotationSpeed * ((float) elapsedMillis);
    }

    public void onGameEvent(GameEvent gameEvent) {
        if (gameEvent == MyGameEvent.SHOW_WIN_DIALOG) {
            removeFromGame();
        }
    }
}
