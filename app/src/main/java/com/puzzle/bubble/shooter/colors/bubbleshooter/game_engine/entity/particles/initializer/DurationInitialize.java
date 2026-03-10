package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.Particle;
import java.util.Random;

public class DurationInitialize implements ParticleInitializer {
    public final long mDuration;

    public DurationInitialize(long duration) {
        this.mDuration = duration;
    }

    public void initParticle(Particle particle, Random random) {
        particle.mDuration = this.mDuration;
    }
}
