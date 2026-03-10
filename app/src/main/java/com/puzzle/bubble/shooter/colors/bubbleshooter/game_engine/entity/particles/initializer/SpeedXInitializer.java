package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class SpeedXInitializer implements ParticleInitializer {
    public final float mMaxSpeedX;
    public final float mMinSpeedX;

    public SpeedXInitializer(float speedMinX, float speedMaxX) {
        this.mMinSpeedX = speedMinX;
        this.mMaxSpeedX = speedMaxX;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mSpeedX = ((random.nextFloat() * (this.mMaxSpeedX - this.mMinSpeedX)) + this.mMinSpeedX) / 1000.0f;
    }
}
