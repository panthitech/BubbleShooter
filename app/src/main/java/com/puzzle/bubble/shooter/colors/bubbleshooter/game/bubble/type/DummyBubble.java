package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;

public class DummyBubble extends Bubble {
    public Bubble mTargetBubble;

    public DummyBubble(Game game) {
        super(game, BubbleColor.DUMMY);
    }

    public void popBubble() {
        if (this.mTargetBubble != null) {
            this.mTargetBubble.popBubble();
        } else {
            super.popBubble();
        }
    }

    public void popFloater() {
        if (this.mTargetBubble == null) {
            super.popFloater();
        }
    }
}
