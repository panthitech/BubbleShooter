package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class RotationSpeedInitializer implements ParticleInitializer {
    public final float mMaxRotationSpeed;
    public final float mMinRotationSpeed;

    public RotationSpeedInitializer(float minRotationSpeed, float maxRotationSpeed) {
        this.mMinRotationSpeed = minRotationSpeed;
        this.mMaxRotationSpeed = maxRotationSpeed;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mRotationSpeed = ((random.nextFloat() * (this.mMaxRotationSpeed - this.mMinRotationSpeed)) + this.mMinRotationSpeed) / 1000.0f;
    }
}
