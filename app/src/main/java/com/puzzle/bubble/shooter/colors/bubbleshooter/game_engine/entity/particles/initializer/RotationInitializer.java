package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class RotationInitializer implements ParticleInitializer {
    public final int mMaxAngle;
    public final int mMinAngle;

    public RotationInitializer(int minAngle, int maxAngle) {
        this.mMinAngle = minAngle;
        this.mMaxAngle = maxAngle;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mRotation = (random.nextFloat() * ((float) (this.mMaxAngle - this.mMinAngle))) + ((float) this.mMinAngle);
    }
}
