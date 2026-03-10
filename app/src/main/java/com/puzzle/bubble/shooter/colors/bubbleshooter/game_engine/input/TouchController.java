package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.input;

import android.view.MotionEvent;
import android.view.View;

public class TouchController implements View.OnTouchListener {
    public boolean mTouching;
    public float mXDown;
    public float mXUp;
    public float mYDown;
    public float mYUp;

    
    public void onActionDown() {
        this.mTouching = true;
    }

    
    public void onActionMove() {
        this.mTouching = true;
    }

    
    public void onActionUp() {
        this.mTouching = false;
    }

    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mXDown = (float) ((int) event.getX());
                this.mYDown = (float) ((int) event.getY());
                onActionDown();
                return true;
            case 1:
                this.mXUp = (float) ((int) event.getX());
                this.mYUp = (float) ((int) event.getY());
                onActionUp();
                return true;
            case 2:
                this.mXDown = (float) ((int) event.getX());
                this.mYDown = (float) ((int) event.getY());
                onActionMove();
                return true;
            default:
                return true;
        }
    }
}
