package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class ScaleInitializer implements ParticleInitializer {
    public final float mFinalValue;
    public final float mInitialValue;
    public final long mStartDelay;

    public ScaleInitializer(float initialValue, float finalValue, long startDelay) {
        this.mInitialValue = initialValue;
        this.mFinalValue = finalValue;
        this.mStartDelay = startDelay;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mScale = this.mInitialValue;
        particle.mScaleSpeed = (this.mFinalValue - this.mInitialValue) / ((float) (particle.mDuration - this.mStartDelay));
        particle.mScaleStartDelay = this.mStartDelay;
    }
}
