package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class FloaterEffect extends Sprite {
    public float mGravity;
    public float mMaxSpeedX;
    public float mMaxSpeedY;
    public float mMinSpeedX;
    public float mMinSpeedY;
    public float mSpeedX;
    public float mSpeedY;

    public FloaterEffect(Game game, int drawableId) {
        super(game, drawableId);
        this.mLayer = 3;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        mGravity = ((this.mPixelFactor * 20.0f) / 1000.0f);
        mMaxSpeedX = ((this.mPixelFactor * 1000.0f) / 1000.0f);
        mMaxSpeedY = (((-this.mPixelFactor) * 7000.0f) / 1000.0f);
        mMinSpeedX = (((-this.mPixelFactor) * 1000.0f) / 1000.0f);
        mMinSpeedY = (((-this.mPixelFactor) * 4000.0f) / 1000.0f);
        this.mSpeedX = (this.mGame.getRandom().nextFloat() * (this.mMaxSpeedX - this.mMinSpeedX)) + this.mMinSpeedX;
        this.mSpeedY = (this.mGame.getRandom().nextFloat() * (this.mMaxSpeedY - this.mMinSpeedY)) + this.mMinSpeedY;
        this.mIsVisible = true;
        this.mIsActive = true;
    }

    public void onStart() {
        this.mIsVisible = false;
        this.mIsActive = false;
    }

    public void onUpdate(long elapsedMillis) {
        this.mX += this.mSpeedX * ((float) elapsedMillis);
        this.mY += this.mSpeedY * ((float) elapsedMillis);
        this.mSpeedY += this.mGravity * ((float) elapsedMillis);
        if (this.mY > ((float) this.mGame.getScreenHeight())) {
            this.mIsVisible = false;
            this.mIsActive = false;
        }
    }
}
