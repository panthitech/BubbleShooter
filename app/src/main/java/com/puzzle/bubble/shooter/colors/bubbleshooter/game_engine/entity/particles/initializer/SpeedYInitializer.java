package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class SpeedYInitializer implements ParticleInitializer {
    public final float mMaxSpeedY;
    public final float mMinSpeedY;

    public SpeedYInitializer(float speedMinY, float speedMaxY) {
        this.mMinSpeedY = speedMinY;
        this.mMaxSpeedY = speedMaxY;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mSpeedY = ((random.nextFloat() * (this.mMaxSpeedY - this.mMinSpeedY)) + this.mMinSpeedY) / 1000.0f;
    }
}
