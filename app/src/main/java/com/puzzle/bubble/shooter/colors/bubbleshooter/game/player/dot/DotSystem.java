package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.PlayerBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.input.TouchController;

import java.util.ArrayList;
import java.util.List;

public class DotSystem extends GameObject {
    public static final int MAX_DOT = 50;
    public List<Dot> mDotPool;
    public final float mInterval;
    public boolean mIsAddToScreen = false;
    public PlayerBubble mParent;
    public float mStartX;
    public float mStartY;

    public DotSystem(PlayerBubble playerBubble, Game game) {
        super(game);
        this.mParent = playerBubble;
        mDotPool = new ArrayList(50);
        this.mStartX = ((float) game.getScreenWidth()) / 2.0f;
        this.mStartY = (((float) (game.getScreenHeight() * 4)) / 5.0f) - (game.getPixelFactor() * 300.0f);
        this.mInterval = game.getPixelFactor() * 200.0f;
        for (int i = 0; i < 50; i++) {
            this.mDotPool.add(new Dot(game));
        }
        for (int i2 = 0; i2 < 49; i2++) {
            this.mDotPool.get(i2).mNextDot = this.mDotPool.get(i2 + 1);
        }
    }

    public void setDotBitmap(int drawableId) {
        for (int i = 0; i < 50; i++) {
            this.mDotPool.get(i).setSpriteBitmap(drawableId);
        }
    }

    public void onRemove() {
        if (this.mIsAddToScreen) {
            removeDot();
            this.mIsAddToScreen = false;
        }
    }

    public void onUpdate(long elapsedMillis) {
        checkAiming(this.mGame.getTouchController());
    }

    public void checkAiming(TouchController touchController) {
        if (touchController.mTouching && this.mParent.getEnable()) {
            if (!this.mIsAddToScreen) {
                addDot();
                this.mIsAddToScreen = true;
            }
            double angle = Math.atan2((double) (touchController.mYDown - this.mStartY), (double) (touchController.mXDown - this.mStartX));
            for (int i = 0; i < 50; i++) {
                setDotPosition(this.mDotPool.get(i), (float) (((double) this.mStartX) + (((double) (((float) i) * this.mInterval)) * Math.cos(angle))), (float) (((double) this.mStartY) + (((double) (((float) i) * this.mInterval)) * Math.sin(angle))));
            }
        } else if (this.mIsAddToScreen) {
            removeDot();
            this.mIsAddToScreen = false;
        }
    }

    
    public void setDotPosition(Dot dot, float x, float y) {
        dot.setPosition(x, y);
    }

    
    public void addDot() {
        for (int i = 0; i < 50; i++) {
            this.mDotPool.get(i).addToGame();
        }
        this.mParent.showHint();
    }

    
    public void removeDot() {
        for (int i = 0; i < 50; i++) {
            this.mDotPool.get(i).removeFromGame();
        }
        this.mParent.removeHint();
    }
}
