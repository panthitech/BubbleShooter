package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster.FireBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;

public class FireDotSystem extends DotSystem {
    public FireDotSystem(FireBubble fireBubble, Game game) {
        super(fireBubble, game);
        setDotBitmap(R.drawable.dot_fire);
    }

    
    public void setDotPosition(Dot dot, float x, float y) {
        if (x <= dot.mMinX || x >= dot.mMaxX) {
            dot.setPosition((float) (-dot.mWidth), (float) (-dot.mHeight));
        } else {
            dot.setPosition(x, y);
        }
    }
}
