package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;

public enum BubbleColor {
    BLUE,
    RED,
    YELLOW,
    GREEN,
    BLANK,
    LOCKED,
    ITEM,
    LARGE_ITEM,
    OBSTACLE,
    LARGE_OBSTACLE,
    DUMMY;

    public int getDrawableId() {
        switch (this) {
            case BLUE:
                return R.drawable.bubble_blue;
            case RED:
                return R.drawable.bubble_red;
            case YELLOW:
                return R.drawable.bubble_yellow;
            case GREEN:
                return R.drawable.bubble_green;
            case BLANK:
                return Game.getDebugMode() ? R.drawable.bubble_blank_debug : R.drawable.bubble_blank;
            case LOCKED:
                return R.drawable.bubble_ice;
            case ITEM:
                return R.drawable.bubble_nut;
            case LARGE_ITEM:
                return R.drawable.bubble_large_nut;
            case OBSTACLE:
                return R.drawable.bubble_wood;
            case LARGE_OBSTACLE:
                return R.drawable.bubble_large_wood_01;
            case DUMMY:
                return R.drawable.bubble_blank;
            default:
                return 0;
        }
    }

    public int getDotDrawableId() {
        switch (this) {
            case BLUE:
                return R.drawable.dot_blue;
            case RED:
                return R.drawable.dot_red;
            case YELLOW:
                return R.drawable.dot_yellow;
            case GREEN:
                return R.drawable.dot_green;
            default:
                return R.drawable.dot_blue;
        }
    }

    public int getScoreDrawableId() {
        switch (this) {
            case BLUE:
                return R.drawable.score_blue;
            case RED:
                return R.drawable.score_red;
            case YELLOW:
                return R.drawable.score_yellow;
            case GREEN:
                return R.drawable.score_green;
            default:
                return R.drawable.score_blue;
        }
    }

    public int getBonusScoreDrawableId() {
        switch (this) {
            case BLUE:
                return R.drawable.bonus_score_blue;
            case RED:
                return R.drawable.bonus_score_red;
            case YELLOW:
                return R.drawable.bonus_score_yellow;
            case GREEN:
                return R.drawable.bonus_score_green;
            default:
                return R.drawable.bonus_score_blue;
        }
    }

    public int getFireworkDrawableId() {
        switch (this) {
            case BLUE:
                return R.drawable.sparkle_blue;
            case RED:
                return R.drawable.sparkle_red;
            case YELLOW:
                return R.drawable.sparkle_yellow;
            case GREEN:
                return R.drawable.sparkle_green;
            default:
                return R.drawable.sparkle_blue;
        }
    }
}
