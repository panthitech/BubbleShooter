package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class SpeedAngleInitializer implements ParticleInitializer {
    public final int mMaxAngle;
    public final int mMinAngle;
    public final float mSpeedMax;
    public final float mSpeedMin;

    public SpeedAngleInitializer(float speedMin, float speedMax, int minAngle, int maxAngle) {
        this.mSpeedMin = speedMin;
        this.mSpeedMax = speedMax;
        this.mMinAngle = minAngle;
        this.mMaxAngle = maxAngle;
    }

    public void initParticle(Particle particle, Random random) {
        float speed = (random.nextFloat() * (this.mSpeedMax - this.mSpeedMin)) + this.mSpeedMin;
        double angleInRads = Math.toRadians((double) ((random.nextFloat() * ((float) (this.mMaxAngle - this.mMinAngle))) + ((float) this.mMinAngle)));
        particle.mSpeedX = (float) ((((double) speed) * Math.cos(angleInRads)) / 1000.0d);
        particle.mSpeedY = (float) ((((double) speed) * Math.sin(angleInRads)) / 1000.0d);
    }
}
