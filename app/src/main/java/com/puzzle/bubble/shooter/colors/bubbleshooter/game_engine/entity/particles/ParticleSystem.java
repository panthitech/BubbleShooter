package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.AccelerationXInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.AccelerationYInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.AlphaInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.DurationInitialize;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.ParticleInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.RotationInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.RotationSpeedInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.ScaleInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.SpeedAngleInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.SpeedXInitializer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.initializer.SpeedYInitializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleSystem extends GameObject {
    public static final int RATE_HIGH = 50;
    public static final int RATE_LOW = 10;
    public static final int RATE_NORMAL = 20;
    public long mCurrentDuration;
    public long mDuration;
    public float mEmissionMaxX;
    public float mEmissionMaxY;
    public float mEmissionMinX;
    public float mEmissionMinY;
    public float mEmissionX;
    public float mEmissionY;
    public final List<ParticleInitializer> mInitializers;
    public boolean mIsEmitting;
    public int mLayer;
    public final List<Particle> mParticlePool;
    public final float mPixelFactor;
    public final Random mRandom;
    public long mTimePerParticles;
    public long mTotalTime;

    public ParticleSystem(Game game, int[] drawableIds, int size) {
        super(game);
        this.mInitializers = new ArrayList();
        this.mParticlePool = new ArrayList();
        this.mTimePerParticles = 50;
        this.mPixelFactor = game.getPixelFactor();
        this.mRandom = game.getRandom();
        for (int i = 0; i < size; i++) {
            this.mParticlePool.add(new Particle(this, game, drawableIds[(int) (this.mRandom.nextFloat() * ((float) drawableIds.length))]));
        }
    }

    public ParticleSystem(Game game, int drawableId, int size) {
        this(game, new int[]{drawableId}, size);
    }

    public ParticleSystem setLayer(int layer) {
        this.mLayer = layer;
        return this;
    }

    public ParticleSystem setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    public ParticleSystem setDurationPerParticle(long duration) {
        this.mInitializers.add(new DurationInitialize(duration));
        return this;
    }

    public ParticleSystem setEmissionRate(int particlesPerSecond) {
        this.mTimePerParticles = (long) (1000 / particlesPerSecond);
        return this;
    }

    public ParticleSystem setEmissionPosition(float x, float y) {
        this.mEmissionX = x;
        this.mEmissionY = y;
        return this;
    }

    public ParticleSystem setEmissionPositionX(float x) {
        this.mEmissionX = x;
        return this;
    }

    public ParticleSystem setEmissionPositionY(float y) {
        this.mEmissionY = y;
        return this;
    }

    public ParticleSystem setEmissionRangeX(float minX, float maxX) {
        this.mEmissionMinX = minX;
        this.mEmissionMaxX = maxX;
        return this;
    }

    public ParticleSystem setEmissionRangeY(float minY, float maxY) {
        this.mEmissionMinY = minY;
        this.mEmissionMaxY = maxY;
        return this;
    }

    public ParticleSystem setSpeedAngle(float speedMin, float speedMax) {
        this.mInitializers.add(new SpeedAngleInitializer(this.mPixelFactor * speedMin, this.mPixelFactor * speedMax, 0, 360));
        return this;
    }

    public ParticleSystem setSpeedAngle(float speedMin, float speedMax, int minAngle, int maxAngle) {
        this.mInitializers.add(new SpeedAngleInitializer(this.mPixelFactor * speedMin, this.mPixelFactor * speedMax, minAngle, maxAngle));
        return this;
    }

    public ParticleSystem setSpeedX(float speedX) {
        this.mInitializers.add(new SpeedXInitializer(this.mPixelFactor * speedX, this.mPixelFactor * speedX));
        return this;
    }

    public ParticleSystem setSpeedX(float speedMinX, float speedMaxX) {
        this.mInitializers.add(new SpeedXInitializer(this.mPixelFactor * speedMinX, this.mPixelFactor * speedMaxX));
        return this;
    }

    public ParticleSystem setSpeedY(float speedY) {
        this.mInitializers.add(new SpeedYInitializer(this.mPixelFactor * speedY, this.mPixelFactor * speedY));
        return this;
    }

    public ParticleSystem setSpeedY(float speedMinY, float speedMaxY) {
        this.mInitializers.add(new SpeedYInitializer(this.mPixelFactor * speedMinY, this.mPixelFactor * speedMaxY));
        return this;
    }

    public ParticleSystem setAccelerationX(float accelerateX) {
        this.mInitializers.add(new AccelerationXInitializer(this.mPixelFactor * accelerateX, this.mPixelFactor * accelerateX));
        return this;
    }

    public ParticleSystem setAccelerationX(float minAccelerateX, float maxAccelerateX) {
        this.mInitializers.add(new AccelerationXInitializer(this.mPixelFactor * minAccelerateX, this.mPixelFactor * maxAccelerateX));
        return this;
    }

    public ParticleSystem setAccelerationY(float accelerateY) {
        this.mInitializers.add(new AccelerationYInitializer(this.mPixelFactor * accelerateY, this.mPixelFactor * accelerateY));
        return this;
    }

    public ParticleSystem setAccelerationY(float minAccelerateY, float maxAccelerateY) {
        this.mInitializers.add(new AccelerationYInitializer(this.mPixelFactor * minAccelerateY, this.mPixelFactor * maxAccelerateY));
        return this;
    }

    public ParticleSystem setInitialRotation(int minAngle, int maxAngle) {
        this.mInitializers.add(new RotationInitializer(minAngle, maxAngle));
        return this;
    }

    public ParticleSystem setRotationSpeed(float minRotationSpeed, float maxRotationSpeed) {
        this.mInitializers.add(new RotationSpeedInitializer(minRotationSpeed, maxRotationSpeed));
        return this;
    }

    public ParticleSystem setAlpha(float initialValue, float finalValue) {
        this.mInitializers.add(new AlphaInitializer(initialValue, finalValue, 0));
        return this;
    }

    public ParticleSystem setAlpha(float initialValue, float finalValue, long startDelay) {
        this.mInitializers.add(new AlphaInitializer(initialValue, finalValue, startDelay));
        return this;
    }

    public ParticleSystem setScale(float initialValue, float finalValue) {
        this.mInitializers.add(new ScaleInitializer(initialValue, finalValue, 0));
        return this;
    }

    public ParticleSystem setScale(float initialValue, float finalValue, long startDelay) {
        this.mInitializers.add(new ScaleInitializer(initialValue, finalValue, startDelay));
        return this;
    }

    public ParticleSystem addInitializer(ParticleInitializer initializer) {
        this.mInitializers.add(initializer);
        return this;
    }

    public ParticleSystem clearInitializers() {
        this.mInitializers.clear();
        return this;
    }

    public void emit() {
        this.mIsEmitting = true;
        this.mTotalTime = 0;
        this.mCurrentDuration = 0;
    }

    public void stopEmit() {
        this.mIsEmitting = false;
    }

    public void oneShot(float x, float y, int numParticles) {
        this.mIsEmitting = false;
        int i = 0;
        while (!this.mParticlePool.isEmpty() && i < numParticles) {
            addOneParticle(x, y);
            i++;
        }
    }

    public void onUpdate(long elapsedMillis) {
        if (this.mIsEmitting) {
            this.mTotalTime += elapsedMillis;
            if (!this.mParticlePool.isEmpty() && this.mTotalTime >= this.mTimePerParticles) {
                updatePosition();
                addOneParticle(this.mEmissionX, this.mEmissionY);
                this.mTotalTime = 0;
            }
            if (this.mDuration != 0) {
                this.mCurrentDuration += elapsedMillis;
                if (this.mCurrentDuration >= this.mDuration) {
                    stopEmit();
                    this.mCurrentDuration = 0;
                }
            }
        }
    }

    public void addOneParticle(float x, float y) {
        Particle p = this.mParticlePool.remove(0);
        int size = this.mInitializers.size();
        for (int i = 0; i < size; i++) {
            this.mInitializers.get(i).initParticle(p, this.mRandom);
        }
        p.activate(x, y, this.mLayer);
    }

    public void updatePosition() {
        if (this.mEmissionMinX != this.mEmissionMaxX) {
            this.mEmissionX = (this.mRandom.nextFloat() * (this.mEmissionMaxX - this.mEmissionMinX)) + this.mEmissionMinX;
        }
        if (this.mEmissionMinY != this.mEmissionMaxY) {
            this.mEmissionY = (this.mRandom.nextFloat() * (this.mEmissionMaxY - this.mEmissionMinY)) + this.mEmissionMinY;
        }
    }

    public void returnToPool(Particle particle) {
        this.mParticlePool.add(particle);
    }
}
