package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import java.util.ArrayList;
import java.util.List;

public class CompositeBubble extends Bubble {
    public final List<DummyBubble> mDummyBubbles = new ArrayList();

    public CompositeBubble(Game game, BubbleColor bubbleColor) {
        super(game, bubbleColor);
    }

    
    public void addDummyBubble(DummyBubble dummyBubble) {
        dummyBubble.mTargetBubble = this;
        this.mDummyBubbles.add(dummyBubble);
    }

    
    public void clearDummyBubble() {
        for (DummyBubble d : this.mDummyBubbles) {
            d.setBubbleColor(BubbleColor.BLANK);
            d.mTargetBubble = null;
        }
    }
}
