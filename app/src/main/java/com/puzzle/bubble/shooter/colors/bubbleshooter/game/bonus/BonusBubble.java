package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bonus;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.ScoreEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class BonusBubble extends Sprite {
    public static final int FIREWORK_PARTICLES = 30;
    public final float mAlphaSpeed;
    public BonusTimeEndListener mBonusTimeEndListener;
    public final ParticleSystem mFireworkSystem;
    public float mGravity;
    public float mMaxSpeedX;
    public float mMaxSpeedY;
    public float mMinSpeedX;
    public float mMinSpeedY;
    public boolean mPop = false;
    public final float mScaleSpeed;
    public final ScoreEffect mScoreEffect;
    public float mSpeedX;
    public float mSpeedY;

    public interface BonusTimeEndListener {
        void onBonusTimeEnd();
    }

    public BonusBubble(Game game, BubbleColor bubbleColor) {
        super(game, bubbleColor.getDrawableId());
        this.mFireworkSystem = new ParticleSystem(game, bubbleColor.getFireworkDrawableId(), 30).setDurationPerParticle(400).setSpeedAngle(2000.0f, 2000.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 200).setScale(1.0f, 0.0f, 200).setLayer(3);
        this.mScoreEffect = new ScoreEffect(game, bubbleColor.getBonusScoreDrawableId());
        this.mMinSpeedX = ((-this.mPixelFactor) * 1000.0f) / 1000.0f;
        this.mMaxSpeedX = (this.mPixelFactor * 1000.0f) / 1000.0f;
        this.mMinSpeedY = ((-this.mPixelFactor) * 9000.0f) / 1000.0f;
        this.mMaxSpeedY = ((-this.mPixelFactor) * 6000.0f) / 1000.0f;
        this.mScaleSpeed = -0.002f;
        this.mAlphaSpeed = -0.5f;
        this.mGravity = (this.mPixelFactor * 10.0f) / 1000.0f;
        this.mLayer = 2;
    }

    public void setBonusTimeEndListener(BonusTimeEndListener listener) {
        this.mBonusTimeEndListener = listener;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        this.mSpeedX = (this.mGame.getRandom().nextFloat() * (this.mMaxSpeedX - this.mMinSpeedX)) + this.mMinSpeedX;
        this.mSpeedY = (this.mGame.getRandom().nextFloat() * (this.mMaxSpeedY - this.mMinSpeedY)) + this.mMinSpeedY;
        addToGame();
    }

    public void onRemove() {
        if (this.mBonusTimeEndListener != null) {
            this.mBonusTimeEndListener.onBonusTimeEnd();
        }
    }

    public void onUpdate(long elapsedMillis) {
        updatePosition(elapsedMillis);
        updateShape(elapsedMillis);
    }

    public void updatePosition(long elapsedMillis) {
        if (!this.mPop) {
            this.mX += this.mSpeedX * ((float) elapsedMillis);
            this.mY += this.mSpeedY * ((float) elapsedMillis);
            this.mSpeedY += this.mGravity * ((float) elapsedMillis);
            if (this.mSpeedY >= 0.0f) {
                explode();
                this.mSpeedX = 0.0f;
                this.mSpeedY = 0.0f;
                this.mPop = true;
            }
        }
    }

    public void updateShape(long elapsedMillis) {
        if (this.mPop) {
            this.mScale += this.mScaleSpeed * ((float) elapsedMillis);
            this.mAlpha += this.mAlphaSpeed * ((float) elapsedMillis);
            if (this.mScale <= 0.0f) {
                removeFromGame();
            }
        }
    }

    public void explode() {
        this.mFireworkSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 30);
        this.mScoreEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
        for (int i = 0; i < 3; i++) {
            gameEvent(MyGameEvent.BUBBLE_POP);
        }
        this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_POP);
    }
}
