package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class AccelerationYInitializer implements ParticleInitializer {
    public final float mMaxAccelerationY;
    public final float mMinAccelerationY;

    public AccelerationYInitializer(float minAccelerationY, float maxAccelerationY) {
        this.mMinAccelerationY = minAccelerationY;
        this.mMaxAccelerationY = maxAccelerationY;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mAccelerationY = ((random.nextFloat() * (this.mMaxAccelerationY - this.mMinAccelerationY)) + this.mMinAccelerationY) / 1000.0f;
    }
}
