package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.sound;

import android.media.SoundPool;

public class Sound {
    public static final long MIN_TIMEOUT = 50;
    public long mLastPlayTime;
    public final int mSoundPoolId;

    public Sound(int soundPoolId) {
        this.mSoundPoolId = soundPoolId;
    }

    public void play(SoundPool soundPool) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - this.mLastPlayTime > MIN_TIMEOUT) {
            this.mLastPlayTime = currentTime;
            soundPool.play(this.mSoundPoolId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }
}
