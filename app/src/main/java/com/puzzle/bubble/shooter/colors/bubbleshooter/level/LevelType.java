package com.puzzle.bubble.shooter.colors.bubbleshooter.level;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;

public enum LevelType {
    POP_BUBBLE,
    COLLECT_ITEM;

    public int getTargetDrawableId() {
        switch (this) {
            case POP_BUBBLE:
                return R.drawable.target_pop;
            case COLLECT_ITEM:
                return R.drawable.target_collect;
            default:
                return 0;
        }
    }

    public int getAnimalDrawableId() {
        switch (this) {
            case POP_BUBBLE:
                return R.drawable.panda_target_pop;
            case COLLECT_ITEM:
                return R.drawable.panda_target_collect;
            default:
                return 0;
        }
    }

    public int getTargetStringId() {
        switch (this) {
            case POP_BUBBLE:
                return R.string.txt_level_type_pop;
            case COLLECT_ITEM:
                return R.string.txt_level_type_collect;
            default:
                return 0;
        }
    }
}
