package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import android.view.animation.OvershootInterpolator;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class ScoreEffect extends Sprite {
    public static final int TIME_TO_ENLARGE = 500;
    public final float mAlphaSpeed = -0.35f;
    public final OvershootInterpolator mInterpolator = new OvershootInterpolator();
    public final float mSpeedY = (((-this.mPixelFactor) * 500.0f) / 1000.0f);
    public long mTotalTime;

    public ScoreEffect(Game game, int drawableId) {
        super(game, drawableId);
        this.mLayer = 3;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        this.mScale = 0.0f;
        this.mAlpha = 255.0f;
        addToGame();
        this.mTotalTime = 0;
    }

    public void onUpdate(long elapsedMillis) {
        this.mTotalTime += elapsedMillis;
        if (this.mTotalTime >= 1200) {
            removeFromGame();
            this.mTotalTime = 0;
        } else if (this.mTotalTime <= 500) {
            this.mScale = this.mInterpolator.getInterpolation((((float) this.mTotalTime) * 1.0f) / 500.0f);
        } else {
            this.mY += this.mSpeedY * ((float) elapsedMillis);
            this.mAlpha += this.mAlphaSpeed * ((float) elapsedMillis);
        }
    }
}
