package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import android.graphics.Bitmap;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.AnimatedSprite;

public class BubblePopEffect extends AnimatedSprite {
    public final float mScaleSpeed = 0.006f;

    public BubblePopEffect(Game game) {
        super(game, R.drawable.bubble_pop_01);
        this.mLayer = 3;
        this.mAnimatedBitmaps = new Bitmap[]{getBitmapFromId(R.drawable.bubble_pop_01), getBitmapFromId(R.drawable.bubble_pop_02), getBitmapFromId(R.drawable.bubble_pop_03), getBitmapFromId(R.drawable.bubble_pop_04), getBitmapFromId(R.drawable.bubble_pop_05)};
        this.mTimePreFrame = 100;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        addToGame();
        startAnimation();
    }

    public void onUpdate(long elapsedMillis) {
        this.mScale += this.mScaleSpeed * ((float) elapsedMillis);
    }

    
    public void onAnimationRepeat() {
        stopAnimation();
        removeFromGame();
    }
}
