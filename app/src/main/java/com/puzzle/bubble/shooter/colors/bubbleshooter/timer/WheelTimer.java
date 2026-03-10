package com.puzzle.bubble.shooter.colors.bubbleshooter.timer;

import android.app.Activity;
import android.content.SharedPreferences;

public class WheelTimer {
    public static final String LAST_PLAY_TIME_PREF_KEY = "last_play_time";
    public static final String PREFS_NAME = "prefs_setting";
    public static final long WHEEL_CD_TIME = 86400000;
    public final SharedPreferences mPrefs;

    public WheelTimer(Activity activity) {
        this.mPrefs = activity.getSharedPreferences(PREFS_NAME, 0);
    }

    public boolean isWheelReady() {
        long lastTime = this.mPrefs.getLong(LAST_PLAY_TIME_PREF_KEY, 0);
        if (lastTime == 0 || System.currentTimeMillis() - lastTime > WHEEL_CD_TIME) {
            return true;
        }
        return false;
    }

    public void setWheelTime(long time) {
        this.mPrefs.edit().putLong(LAST_PLAY_TIME_PREF_KEY, time).apply();
    }
}
