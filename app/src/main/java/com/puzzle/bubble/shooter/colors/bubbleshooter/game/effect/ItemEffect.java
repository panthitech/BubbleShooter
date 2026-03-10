package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class ItemEffect extends Sprite {
    public final float mScaleSpeed = 0.002f;
    public final float mSpeed = ((this.mPixelFactor * 3000.0f) / 1000.0f);
    public float mSpeedX;
    public float mSpeedY;
    public final float mTargetX = ((((float) this.mGame.getScreenWidth()) * 0.84f) - (((float) this.mWidth) / 2.0f));
    public final float mTargetY = (((float) (-this.mHeight)) / 2.0f);
    public long mTotalTime;

    public ItemEffect(Game game, int drawableId) {
        super(game, drawableId);
        this.mLayer = 3;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        double angle = Math.atan2((double) (this.mTargetY - y), (double) (this.mTargetX - x));
        this.mSpeedX = (float) (((double) this.mSpeed) * Math.cos(angle));
        this.mSpeedY = (float) (((double) this.mSpeed) * Math.sin(angle));
        addToGame();
        this.mTotalTime = 0;
    }

    public void onUpdate(long elapsedMillis) {
        this.mTotalTime += elapsedMillis;
        if (this.mTotalTime < 200) {
            this.mScale += this.mScaleSpeed * 2.0f * ((float) elapsedMillis);
        } else if (this.mTotalTime >= 400) {
            this.mX += this.mSpeedX * ((float) elapsedMillis);
            this.mY += this.mSpeedY * ((float) elapsedMillis);
            if (this.mScale > 1.0f) {
                this.mScale -= this.mScaleSpeed * ((float) elapsedMillis);
            }
            if (this.mX >= this.mTargetX - (((float) this.mWidth) / 2.0f) && this.mY <= this.mTargetY - (((float) this.mHeight) / 2.0f)) {
                removeFromGame();
                this.mTotalTime = 0;
            }
        }
    }
}
