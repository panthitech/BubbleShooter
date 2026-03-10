package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.combo;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;

public enum Combo {
    WOW,
    GOOD,
    WONDERFUL;

    public int getDrawableId() {
        switch (this) {
            case WOW:
                return R.drawable.text_wow;
            case GOOD:
                return R.drawable.text_good;
            case WONDERFUL:
                return R.drawable.text_wonderful;
            default:
                return 0;
        }
    }
}
