package com.puzzle.bubble.shooter.colors.bubbleshooter.timer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import java.util.Locale;

public class LivesTimer {
    public static final String END_TIME_PREF_KEY = "end_time";
    public static final long LIVES_CD = 1200000;
    public static final int LIVES_MAX = 5;
    public static final String LIVES_PREF_KEY = "lives";
    public static final String MILLIS_LEFT_PREF_KEY = "millis_left";
    public static final String PREFS_NAME = "prefs_lives";
    
    public final Activity mActivity;
    public CountDownTimer mCountDownTimer;
    
    public long mEndTime;
    
    public int mLivesNum;
    public final SharedPreferences mPrefs;
    
    public long mTimeLeftInMillis;
    public TextView mTxtLives;
    
    public TextView mTxtTime;

    static int access$208(LivesTimer x0) {
        int i = x0.mLivesNum;
        x0.mLivesNum = i + 1;
        return i;
    }

    public LivesTimer(Activity activity) {
        this.mActivity = activity;
        this.mPrefs = activity.getSharedPreferences(PREFS_NAME, 0);
    }

    public void start() {
        this.mTxtLives = (TextView) this.mActivity.findViewById(R.id.txt_lives);
        this.mTxtTime = (TextView) this.mActivity.findViewById(R.id.txt_lives_time);
        this.mLivesNum = this.mPrefs.getInt(LIVES_PREF_KEY, 5);
        if (this.mLivesNum == 5) {
            updateLivesText();
            return;
        }
        this.mEndTime = this.mPrefs.getLong(END_TIME_PREF_KEY, 0);
        this.mTimeLeftInMillis = this.mEndTime - System.currentTimeMillis();
        if (this.mTimeLeftInMillis < 0) {
            long timePass = -this.mTimeLeftInMillis;
            long timeRemaining = timePass % LIVES_CD;
            this.mLivesNum += ((int) (timePass / LIVES_CD)) + 1;
            this.mTimeLeftInMillis = LIVES_CD - timeRemaining;
            if (this.mLivesNum >= 5) {
                this.mLivesNum = 5;
                this.mTimeLeftInMillis = 0;
                this.mEndTime = 0;
            } else {
                startTimer();
            }
        } else {
            startTimer();
        }
        updateLivesText();
    }

    public void stop() {
        this.mPrefs.edit().putInt(LIVES_PREF_KEY, this.mLivesNum).putLong(MILLIS_LEFT_PREF_KEY, this.mTimeLeftInMillis).putLong(END_TIME_PREF_KEY, this.mEndTime).apply();
        if (this.mCountDownTimer != null) {
            this.mCountDownTimer.cancel();
        }
    }

    public void addLive() {
        if (this.mLivesNum < 5) {
            this.mLivesNum++;
        }
        this.mPrefs.edit().putInt(LIVES_PREF_KEY, this.mLivesNum).apply();
    }

    public void reduceLive() {
        this.mLivesNum--;
        if (this.mTimeLeftInMillis == 0) {
            this.mTimeLeftInMillis = LIVES_CD;
            this.mEndTime = System.currentTimeMillis() + this.mTimeLeftInMillis;
        }
        this.mPrefs.edit().putInt(LIVES_PREF_KEY, this.mLivesNum).putLong(MILLIS_LEFT_PREF_KEY, this.mTimeLeftInMillis).putLong(END_TIME_PREF_KEY, this.mEndTime).apply();
    }

    public boolean isEnoughLives() {
        return this.mLivesNum > 0;
    }

    public boolean isLivesFull() {
        return this.mLivesNum == 5;
    }

    
    public void updateLivesText() {
        this.mTxtLives.setText(String.valueOf(this.mLivesNum));
        if (this.mLivesNum == 0) {
            this.mTxtLives.setBackgroundResource(R.drawable.lives_lock);
            return;
        }
        this.mTxtLives.setBackgroundResource(R.drawable.lives);
        if (this.mLivesNum == 5) {
            this.mTxtTime.setText(this.mActivity.getResources().getString(R.string.txt_lives_full));
        }
    }

    
    public void startTimer() {
        this.mEndTime = System.currentTimeMillis() + this.mTimeLeftInMillis;
        if (this.mCountDownTimer != null) {
            this.mCountDownTimer.cancel();
        }
        this.mCountDownTimer = new CountDownTimer(this.mTimeLeftInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                long unused = LivesTimer.this.mTimeLeftInMillis = millisUntilFinished;
                LivesTimer.this.updateCountDownText();
            }

            public void onFinish() {
                if (LivesTimer.this.mLivesNum < 5) {
                    LivesTimer.access$208(LivesTimer.this);
                    if (LivesTimer.this.mLivesNum != 5) {
                        long unused = LivesTimer.this.mTimeLeftInMillis = LivesTimer.LIVES_CD;
                        LivesTimer.this.startTimer();
                    } else {
                        long unused2 = LivesTimer.this.mTimeLeftInMillis = 0;
                        long unused3 = LivesTimer.this.mEndTime = 0;
                        LivesTimer.this.mTxtTime.setText(LivesTimer.this.mActivity.getResources().getString(R.string.txt_lives_full));
                    }
                }
                LivesTimer.this.updateLivesText();
            }
        }.start();
    }

    
    public void updateCountDownText() {
        this.mTxtTime.setText(String.format(Locale.getDefault(), "%02d:%02d", new Object[]{Integer.valueOf(((int) (this.mTimeLeftInMillis / 1000)) / 60), Integer.valueOf(((int) (this.mTimeLeftInMillis / 1000)) % 60)}));
    }
}
