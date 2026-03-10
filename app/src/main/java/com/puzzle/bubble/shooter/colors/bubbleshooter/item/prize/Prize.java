package com.puzzle.bubble.shooter.colors.bubbleshooter.item.prize;

public class Prize {
    public int mDrawableId;
    public final String mName;
    public final int mNum;

    public Prize(String name, int num) {
        this.mName = name;
        this.mNum = num;
    }

    public String getName() {
        return this.mName;
    }

    public int getDrawableResId() {
        return this.mDrawableId;
    }

    public int getNum() {
        return this.mNum;
    }

    public void setView(int drawableId) {
        this.mDrawableId = drawableId;
    }
}
