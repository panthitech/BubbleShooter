package com.puzzle.bubble.shooter.colors.bubbleshooter.ui;

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

public class UIEffect {
    
    public static final BounceInterpolator BOUNCE_INTERPOLATOR = new BounceInterpolator();
    public static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator();

    public UIEffect() {
    }

    public static void createColorFilter(View view) {
        view.getBackground().setColorFilter(1996488704, PorterDuff.Mode.SRC_ATOP);
        view.invalidate();
    }

    public static void clearColorFilter(View view) {
        view.getBackground().clearColorFilter();
        view.invalidate();
    }

    public static void createButtonEffect(final View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        button.animate().setStartDelay(0).setDuration(300).scaleX(0.8f).scaleY(0.8f).setInterpolator(UIEffect.BOUNCE_INTERPOLATOR);
                        UIEffect.createColorFilter(view);
                        return false;
                    case 1:
                    case 2:
                        button.animate().setStartDelay(0).setDuration(300).scaleX(1.0f).scaleY(1.0f).setInterpolator(UIEffect.BOUNCE_INTERPOLATOR);
                        UIEffect.clearColorFilter(view);
                        return false;
                    default:
                        return false;
                }
            }
        });
    }

    public static void createPopUpEffect(View view) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().setStartDelay(300).setDuration(200).scaleX(1.0f).scaleY(1.0f).setInterpolator(OVERSHOOT_INTERPOLATOR);
    }

    public static void createPopUpEffect(View view, long popOrder) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.animate().setStartDelay((75 * popOrder) + 300).setDuration(200).scaleX(1.0f).scaleY(1.0f).setInterpolator(OVERSHOOT_INTERPOLATOR);
    }
}
