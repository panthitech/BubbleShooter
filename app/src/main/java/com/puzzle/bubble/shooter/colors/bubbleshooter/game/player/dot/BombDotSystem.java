package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster.BombBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class BombDotSystem extends DotSystem {
    public final BoosterHint mBoosterHint;

    public BombDotSystem(BombBubble bombBubble, Game game) {
        super(bombBubble, game);
        this.mBoosterHint = new BoosterHint(game);
        setDotBitmap(R.drawable.dot_bomb);
    }

    public void onUpdate(long elapsedMillis) {
        super.onUpdate(elapsedMillis);
        checkHint();
    }

    public void checkHint() {
        if (this.mIsAddToScreen) {
            for (Dot currentDot : this.mDotPool) {
                Dot nextDot = currentDot.mNextDot;
                if (nextDot != null && nextDot.mIsCollide) {
                    Bubble bubble = nextDot.mCollideBubble.getCollidedBubble(currentDot);
                    this.mBoosterHint.setPosition(bubble.mX + (((float) bubble.mWidth) / 2.0f), bubble.mY + (((float) bubble.mHeight) / 2.0f));
                    this.mBoosterHint.mIsVisible = true;
                    return;
                }
            }
            this.mBoosterHint.mIsVisible = false;
        }
    }

    
    public void addDot() {
        super.addDot();
        this.mBoosterHint.addToGame();
    }

    
    public void removeDot() {
        super.removeDot();
        this.mBoosterHint.removeFromGame();
    }

    public static class BoosterHint extends Sprite {
        public BoosterHint(Game game) {
            super(game, R.drawable.bomb_hint);
            this.mLayer = 3;
        }

        public void setPosition(float x, float y) {
            this.mX = x - (((float) this.mWidth) / 2.0f);
            this.mY = y - (((float) this.mHeight) / 2.0f);
        }

        public void onUpdate(long elapsedMillis) {
        }
    }
}
