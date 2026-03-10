package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.sound;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import java.util.HashMap;

public abstract class SoundManager {
    public static final float DEFAULT_MUSIC_VOLUME = 0.6f;
    public static final int MAX_STREAMS = 10;
    public static final String MUSIC_PREF_KEY = "music";
    public static final String PREFS_NAME = "prefs_sound";
    public static final String SOUNDS_PREF_KEY = "sound";
    public final Context mContext;
    public boolean mMusicEnabled;
    public MediaPlayer mMusicPlayer;
    public final SharedPreferences mPrefs;
    public boolean mSoundEnabled;
    public SoundPool mSoundPool;
    public final HashMap<SoundEvent, Sound> mSoundsMap = new HashMap<>();

    protected SoundManager(Context context) {
        this.mContext = context;
        this.mPrefs = context.getSharedPreferences(PREFS_NAME, 0);
        this.mSoundEnabled = this.mPrefs.getBoolean(SOUNDS_PREF_KEY, true);
        this.mMusicEnabled = this.mPrefs.getBoolean(MUSIC_PREF_KEY, true);
        initSoundPool();
    }

    public void initSoundPool() {
        this.mSoundPool = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(14).setContentType(2).build()).setMaxStreams(10).build();
    }

    public void loadSound(SoundEvent event, int soundId) {
        this.mSoundsMap.put(event, new Sound(this.mSoundPool.load(this.mContext, soundId, 1)));
    }

    public void unloadSound() {
        this.mSoundPool.release();
        this.mSoundPool = null;
        this.mSoundsMap.clear();
    }

    public void playSound(SoundEvent event) {
        Sound sound;
        if (this.mSoundEnabled && (sound = this.mSoundsMap.get(event)) != null) {
            sound.play(this.mSoundPool);
        }
    }

    public void loadMusic(int musicId) {
        this.mMusicPlayer = MediaPlayer.create(this.mContext, musicId);
        this.mMusicPlayer.setLooping(true);
        this.mMusicPlayer.setVolume(DEFAULT_MUSIC_VOLUME, DEFAULT_MUSIC_VOLUME);
        if (this.mMusicEnabled) {
            this.mMusicPlayer.start();
        }
    }

    public void pauseMusic() {
        if (this.mMusicPlayer != null && this.mMusicEnabled) {
            this.mMusicPlayer.pause();
        }
    }

    public void resumeMusic() {
        if (this.mMusicPlayer != null && this.mMusicEnabled) {
            this.mMusicPlayer.start();
        }
    }

    public void unloadMusic() {
        if (this.mMusicPlayer != null) {
            this.mMusicPlayer.stop();
            this.mMusicPlayer.release();
            this.mMusicPlayer = null;
        }
    }

    public void switchMusicState() {
        this.mMusicEnabled = !this.mMusicEnabled;
        if (this.mMusicEnabled) {
            if (this.mMusicPlayer != null) {
                this.mMusicPlayer.start();
            }
        } else if (this.mMusicPlayer != null) {
            this.mMusicPlayer.pause();
        }
        this.mPrefs.edit().putBoolean(MUSIC_PREF_KEY, this.mMusicEnabled).apply();
    }

    public void switchSoundState() {
        this.mSoundEnabled = !this.mSoundEnabled;
        this.mPrefs.edit().putBoolean(SOUNDS_PREF_KEY, this.mSoundEnabled).apply();
    }

    public boolean getMusicState() {
        return this.mMusicEnabled;
    }

    public boolean getSoundState() {
        return this.mSoundEnabled;
    }
}
