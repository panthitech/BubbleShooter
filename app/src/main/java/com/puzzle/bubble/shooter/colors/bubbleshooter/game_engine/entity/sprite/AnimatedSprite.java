package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite;

import android.graphics.Bitmap;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;

public abstract class AnimatedSprite extends Sprite {
    public Bitmap[] mAnimatedBitmaps;
    public int mFrame;
    public boolean mIsRunning = false;
    public long mTimePreFrame;
    public long mTotalTime;

    protected AnimatedSprite(Game game, int drawableId) {
        super(game, drawableId);
    }

    public final void startAnimation() {
        this.mIsRunning = true;
        this.mFrame = 0;
        this.mTotalTime = 0;
        onAnimationStart();
    }

    public final void stopAnimation() {
        this.mIsRunning = false;
        onAnimationStop();
    }

    public final void resumeAnimation() {
        this.mIsRunning = true;
        onAnimationResume();
    }

    public final void repeatAnimation() {
        this.mFrame = 0;
        this.mTotalTime = 0;
        onAnimationRepeat();
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public void onPreUpdate(long elapsedMillis) {
        if (this.mIsRunning) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= this.mTimePreFrame) {
                if (this.mFrame < this.mAnimatedBitmaps.length - 1) {
                    this.mFrame++;
                } else {
                    repeatAnimation();
                }
                this.mBitmap = this.mAnimatedBitmaps[this.mFrame];
                this.mTotalTime = 0;
            }
        }
    }

    
    public void onAnimationStart() {
    }

    
    public void onAnimationStop() {
    }

    
    public void onAnimationResume() {
    }

    
    public void onAnimationRepeat() {
    }
}
