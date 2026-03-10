package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import java.util.ArrayList;
import java.util.List;

public class BubbleQueue {
    public static final int EXTRA_MOVES = 5;
    public final char[] mBubbleChars;
    public final List<BubbleColor> mBubbleList = new ArrayList();

    public BubbleQueue(char[] array) {
        this.mBubbleChars = array;
        for (char color : array) {
            this.mBubbleList.add(getBubbleColor(color));
        }
    }

    public BubbleColor getBubbleColor(char color) {
        switch (color) {
            case 'b':
                return BubbleColor.BLUE;
            case 'g':
                return BubbleColor.GREEN;
            case 'r':
                return BubbleColor.RED;
            case 'y':
                return BubbleColor.YELLOW;
            default:
                return null;
        }
    }

    public BubbleColor popBubble() {
        if (hasBubble()) {
            return this.mBubbleList.remove(0);
        }
        return null;
    }

    public BubbleColor getBubble() {
        if (hasBubble()) {
            return this.mBubbleList.get(0);
        }
        return null;
    }

    public BubbleColor getNextBubble() {
        if (this.mBubbleList.size() >= 2) {
            return this.mBubbleList.get(1);
        }
        return null;
    }

    public void switchBubble() {
        if (this.mBubbleList.size() >= 2) {
            this.mBubbleList.set(0, this.mBubbleList.get(1));
            this.mBubbleList.set(1, this.mBubbleList.get(0));
        }
    }

    public boolean hasBubble() {
        return !this.mBubbleList.isEmpty();
    }

    public void initExtraBubble() {
        int start = this.mBubbleChars.length - 5;
        for (int i = 0; i < 5; i++) {
            this.mBubbleList.add(getBubbleColor(this.mBubbleChars[start + i]));
        }
    }
}
