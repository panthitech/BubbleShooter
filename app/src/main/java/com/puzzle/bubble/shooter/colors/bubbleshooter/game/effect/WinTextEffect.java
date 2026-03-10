package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class WinTextEffect {
    public static final int TIME_TO_LIVE = 400;
    public final AnticipateInterpolator mAnticipateInterpolator = new AnticipateInterpolator();
    public final OvershootInterpolator mOvershootInterpolator = new OvershootInterpolator();
    public final TextBg mTextBg;
    public final WinText mWinText;
    public final YouText mYouText;

    public WinTextEffect(Game game) {
        this.mTextBg = new TextBg(game, R.drawable.light_bg);
        this.mYouText = new YouText(game, R.drawable.text_you);
        this.mWinText = new WinText(game, R.drawable.text_win);
    }

    public void activate() {
        this.mTextBg.addToGame();
        this.mYouText.addToGame();
        this.mWinText.addToGame();
    }

    public class TextBg extends Sprite {
        public final float mAlphaSpeed = -0.01f;
        public final float mRotationSpeed = 0.12f;
        public long mTotalTime;

        public TextBg(Game game, int drawableId) {
            super(game, drawableId);
            this.mLayer = 4;
        }

        public void onStart() {
            this.mX = (((float) this.mGame.getScreenWidth()) / 2.0f) - (((float) this.mWidth) / 2.0f);
            this.mY = (((float) this.mGame.getScreenHeight()) / 3.0f) - (((float) this.mHeight) / 2.0f);
            this.mScale = 4.0f;
        }

        public void onUpdate(long elapsedMillis) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= 1600) {
                removeFromGame();
                this.mTotalTime = 0;
                return;
            }
            if (this.mTotalTime <= 400) {
                this.mScale = 4.0f * WinTextEffect.this.mOvershootInterpolator.getInterpolation((((float) this.mTotalTime) * 1.0f) / 400.0f);
            } else if (this.mTotalTime >= 1200) {
                this.mScale = 4.0f - (WinTextEffect.this.mAnticipateInterpolator.getInterpolation((((float) (this.mTotalTime - 1200)) * 1.0f) / 400.0f) * 4.0f);
                this.mAlpha += this.mAlphaSpeed * ((float) elapsedMillis);
            }
            this.mRotation += this.mRotationSpeed * ((float) elapsedMillis);
        }
    }

    public class YouText extends Sprite {
        public final float mFinalX;
        public final float mInitialX = ((float) (-this.mWidth));
        public long mTotalTime;
        public final float mValueIncrement;

        public YouText(Game game, int drawableId) {
            super(game, drawableId);
            this.mFinalX = (((float) game.getScreenWidth()) / 2.0f) - (((float) this.mWidth) / 2.0f);
            this.mValueIncrement = this.mFinalX - this.mInitialX;
            this.mLayer = 4;
        }

        public void onStart() {
            this.mX = this.mInitialX;
            this.mY = (((float) this.mGame.getScreenHeight()) / 3.0f) - ((float) this.mHeight);
            this.mScale = 2.0f;
        }

        public void onUpdate(long elapsedMillis) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= 1600) {
                removeFromGame();
                this.mTotalTime = 0;
            } else if (this.mTotalTime <= 400) {
                this.mX = this.mInitialX + (this.mValueIncrement * WinTextEffect.this.mOvershootInterpolator.getInterpolation((((float) this.mTotalTime) * 1.0f) / 400.0f));
            } else if (this.mTotalTime >= 1200) {
                this.mX = this.mFinalX - (this.mValueIncrement * WinTextEffect.this.mAnticipateInterpolator.getInterpolation((((float) (this.mTotalTime - 1200)) * 1.0f) / 400.0f));
            }
        }
    }

    public class WinText extends Sprite {
        public float mFinalX = mGame.getScreenWidth();
        public float mInitialX = mGame.getScreenWidth();
        public long mTotalTime;
        public float mValueIncrement;

        public WinText(Game game, int drawableId) {
            super(game, drawableId);
            this.mInitialX = (float) game.getScreenWidth();
            this.mFinalX = (((float) game.getScreenWidth()) / 2.0f) - (((float) this.mWidth) / 2.0f);
            mValueIncrement = (this.mInitialX - this.mFinalX);
            this.mLayer = 4;
        }

        public void onStart() {
            this.mX = this.mInitialX;
            this.mY = (((float) this.mGame.getScreenHeight()) / 3.0f) + (((float) this.mHeight) / 2.0f);
            this.mScale = 2.0f;
        }

        public void onUpdate(long elapsedMillis) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= 1600) {
                removeFromGame();
                this.mTotalTime = 0;
            } else if (this.mTotalTime <= 400) {
                this.mX = this.mInitialX - (this.mValueIncrement * WinTextEffect.this.mOvershootInterpolator.getInterpolation((((float) this.mTotalTime) * 1.0f) / 400.0f));
            } else if (this.mTotalTime >= 1200) {
                this.mX = this.mFinalX + (this.mValueIncrement * WinTextEffect.this.mAnticipateInterpolator.getInterpolation((((float) (this.mTotalTime - 1200)) * 1.0f) / 400.0f));
            }
        }
    }
}
