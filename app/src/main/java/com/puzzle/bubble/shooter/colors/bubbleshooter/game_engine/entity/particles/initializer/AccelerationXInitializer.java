package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class AccelerationXInitializer implements ParticleInitializer {
    public final float mMaxAccelerationX;
    public final float mMinAccelerationX;

    public AccelerationXInitializer(float minAccelerationX, float maxAccelerationX) {
        this.mMinAccelerationX = minAccelerationX;
        this.mMaxAccelerationX = maxAccelerationX;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mAccelerationX = ((random.nextFloat() * (this.mMaxAccelerationX - this.mMinAccelerationX)) + this.mMinAccelerationX) / 1000.0f;
    }
}
