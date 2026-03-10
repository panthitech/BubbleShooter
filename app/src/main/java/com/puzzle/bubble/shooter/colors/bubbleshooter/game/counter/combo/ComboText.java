package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.combo;

import android.view.animation.OvershootInterpolator;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class ComboText extends Sprite {
    public static final int TIME_TO_SHRINK = 300;
    public final float mAlphaSpeed = -0.8f;
    public final OvershootInterpolator mInterpolator = new OvershootInterpolator();
    public long mTotalTime;

    public ComboText(Game game) {
        super(game, R.drawable.text_wow);
        this.mLayer = 4;
    }

    public void activate(Combo combo) {
        this.mX = (((float) this.mGame.getScreenWidth()) / 2.0f) - (((float) this.mWidth) / 2.0f);
        this.mY = (((float) this.mGame.getScreenHeight()) / 3.0f) - (((float) this.mHeight) / 2.0f);
        this.mScale = 5.0f;
        this.mAlpha = 255.0f;
        setSpriteBitmap(combo.getDrawableId());
        addToGame();
        this.mGame.getSoundManager().playSound(MySoundEvent.COMBO);
        this.mTotalTime = 0;
    }

    public void onUpdate(long elapsedMillis) {
        this.mTotalTime += elapsedMillis;
        if (this.mTotalTime >= 1300) {
            removeFromGame();
            this.mTotalTime = 0;
        } else if (this.mTotalTime <= 300) {
            this.mScale = 5.0f - (this.mInterpolator.getInterpolation((((float) this.mTotalTime) * 1.0f) / 300.0f) * 3.0f);
        } else if (this.mTotalTime >= 1000) {
            this.mAlpha += this.mAlphaSpeed * ((float) elapsedMillis);
        }
    }
}
