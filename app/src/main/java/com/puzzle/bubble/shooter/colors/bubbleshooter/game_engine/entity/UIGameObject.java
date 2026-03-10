package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity;

import android.graphics.Canvas;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;

public abstract class UIGameObject extends GameObject implements Drawable {
    public final Runnable mRunnable = new Runnable() {
        public void run() {
            UIGameObject.this.onDrawUI();
        }
    };
    public boolean mUIHaveChanged = false;

    
    public abstract void onDrawUI();

    protected UIGameObject(Game game) {
        super(game);
    }

    public void draw(Canvas canvas) {
        this.mGame.getGameActivity().runOnUiThread(this.mRunnable);
        this.mUIHaveChanged = false;
    }

    public int getLayer() {
        return 0;
    }

    public boolean isVisible() {
        return this.mUIHaveChanged;
    }

    
    public void drawUI() {
        this.mUIHaveChanged = true;
    }
}
