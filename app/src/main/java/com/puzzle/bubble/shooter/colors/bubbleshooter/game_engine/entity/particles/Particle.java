package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class Particle extends Sprite {
    public float mAccelerationX;
    public float mAccelerationY;
    public float mAlphaSpeed;
    public long mAlphaStartDelay;
    public long mDuration;
    public final ParticleSystem mParent;
    public float mRotationSpeed;
    public float mScaleSpeed;
    public long mScaleStartDelay;
    public float mSpeedX;
    public float mSpeedY;
    public long mTotalTime;

    public Particle(ParticleSystem particleSystem, Game game, int drawableId) {
        super(game, drawableId);
        this.mParent = particleSystem;
    }

    public void activate(float x, float y, int layer) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        this.mLayer = layer;
        addToGame();
        this.mTotalTime = 0;
    }

    public void onRemove() {
        this.mParent.returnToPool(this);
    }

    public void onUpdate(long elapsedMillis) {
        this.mTotalTime += elapsedMillis;
        if (this.mTotalTime >= this.mDuration) {
            removeFromGame();
        } else {
            updateParticle(elapsedMillis);
        }
    }

    public void updateParticle(long elapsedMillis) {
        this.mX += this.mSpeedX * ((float) elapsedMillis);
        this.mY += this.mSpeedY * ((float) elapsedMillis);
        this.mSpeedX += this.mAccelerationX * ((float) elapsedMillis);
        this.mSpeedY += this.mAccelerationY * ((float) elapsedMillis);
        this.mRotation += this.mRotationSpeed * ((float) elapsedMillis);
        if (this.mTotalTime >= this.mAlphaStartDelay) {
            this.mAlpha += this.mAlphaSpeed * ((float) elapsedMillis);
            if (this.mAlpha < 0.0f) {
                this.mAlpha = 0.0f;
            }
        }
        if (this.mTotalTime >= this.mScaleStartDelay) {
            this.mScale += this.mScaleSpeed * ((float) elapsedMillis);
            if (this.mScale < 0.0f) {
                this.mScale = 0.0f;
            }
        }
    }
}
