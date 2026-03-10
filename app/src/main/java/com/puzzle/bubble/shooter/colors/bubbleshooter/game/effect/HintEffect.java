package com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;

public class HintEffect extends Sprite {
    public final HintEffectBg mHintEffectBg;

    public HintEffect(Game game) {
        super(game, R.drawable.bubble_blank_debug);
        this.mHintEffectBg = new HintEffectBg(game);
        this.mLayer = 3;
    }

    public void activate(float x, float y) {
        this.mX = x - (((float) this.mWidth) / 2.0f);
        this.mY = y - (((float) this.mHeight) / 2.0f);
        this.mHintEffectBg.activate(x, y);
        addToGame();
    }

    public void onRemove() {
        this.mHintEffectBg.removeFromGame();
    }

    public void onUpdate(long elapsedMillis) {
    }

    public static class HintEffectBg extends Sprite {
        public HintEffectBg(Game game) {
            super(game, R.drawable.bubble_bg);
            this.mLayer = 1;
        }

        public void activate(float x, float y) {
            this.mX = x - (((float) this.mWidth) / 2.0f);
            this.mY = y - (((float) this.mHeight) / 2.0f);
            addToGame();
        }

        public void onUpdate(long elapsedMillis) {
        }
    }
}
