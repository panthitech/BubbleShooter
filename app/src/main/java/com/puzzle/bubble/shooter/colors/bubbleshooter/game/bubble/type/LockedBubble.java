package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;

public class LockedBubble extends Bubble {
    public static final int EXPLOSION_PARTICLES = 8;
    public final ParticleSystem mExplosionParticleSystem;
    public boolean mIsLocked = true;
    public final BubbleColor mUnlockBubbleColor;

    public LockedBubble(Game game, BubbleColor bubbleColor) {
        super(game, BubbleColor.LOCKED);
        this.mUnlockBubbleColor = bubbleColor;
        this.mExplosionParticleSystem = new ParticleSystem(game, R.drawable.ice_particle, 8).setLayer(4).setDurationPerParticle(600).setSpeedX(-800.0f, 800.0f).setSpeedY(-800.0f, 800.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 300).setScale(1.0f, 0.0f, 300);
    }

    public void popBubble() {
        if (this.mIsLocked) {
            unlock();
        } else {
            super.popBubble();
        }
    }

    public void popFloater() {
        if (this.mIsLocked) {
            this.mIsLocked = false;
        }
        super.popFloater();
    }

    public void unlock() {
        this.mExplosionParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 8);
        setBubbleColor(this.mUnlockBubbleColor);
        this.mIsLocked = false;
    }
}
