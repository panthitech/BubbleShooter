package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class ObstacleBubble extends Bubble {
    public static final int EXPLOSION_PARTICLES = 10;
    public final ParticleSystem mExplosionParticleSystem;
    public boolean mIsObstacle = true;

    public ObstacleBubble(Game game) {
        super(game, BubbleColor.OBSTACLE);
        this.mExplosionParticleSystem = new ParticleSystem(game, R.drawable.wood_particle_01, 10).setDurationPerParticle(600).setSpeedX(-800.0f, 800.0f).setSpeedY(-800.0f, 800.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 300).setScale(1.0f, 0.0f, 300).setLayer(3);
    }

    public void popBubble() {
        if (this.mIsObstacle) {
            popObstacle();
        } else {
            super.popBubble();
        }
    }

    public void popFloater() {
        if (this.mIsObstacle) {
            popObstacle();
        } else {
            super.popFloater();
        }
    }

    public void popObstacle() {
        this.mExplosionParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 10);
        setBubbleColor(BubbleColor.BLANK);
        this.mGame.getSoundManager().playSound(MySoundEvent.WOOD_EXPLODE);
        this.mIsObstacle = false;
    }
}
