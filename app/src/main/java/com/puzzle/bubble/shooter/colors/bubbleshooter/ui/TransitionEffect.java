package com.puzzle.bubble.shooter.colors.bubbleshooter.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;

public class TransitionEffect implements View.OnTouchListener, Animation.AnimationListener {
    public boolean mIsShowing;
    public OnTransitionListener mListener;
    public final Activity mParent;
    public ViewGroup mRootLayout;

    public interface OnTransitionListener {
        void onTransition();
    }

    public TransitionEffect(Activity activity) {
        this.mParent = activity;
    }

    public void setListener(OnTransitionListener listener) {
        this.mListener = listener;
    }

    public void show() {
        this.mIsShowing = true;
        ViewGroup activityRoot = (ViewGroup) findViewById(16908290);
        this.mRootLayout = (ViewGroup) LayoutInflater.from(this.mParent).inflate(R.layout.view_transition, activityRoot, false);
        this.mRootLayout.setOnTouchListener(this);
        activityRoot.addView(this.mRootLayout);
        startShowAnimation();
    }

    public void startShowAnimation() {
        Animation left = AnimationUtils.loadAnimation(this.mParent, R.anim.transition_show_left);
        Animation right = AnimationUtils.loadAnimation(this.mParent, R.anim.transition_show_right);
        right.setAnimationListener(this);
        findViewById(R.id.image_translate_left).startAnimation(left);
        findViewById(R.id.image_translate_right).startAnimation(right);
    }

    public void startHideAnimation() {
        Animation left = AnimationUtils.loadAnimation(this.mParent, R.anim.transition_hide_left);
        Animation right = AnimationUtils.loadAnimation(this.mParent, R.anim.transition_hide_right);
        right.setAnimationListener(this);
        findViewById(R.id.image_translate_left).startAnimation(left);
        findViewById(R.id.image_translate_right).startAnimation(right);
    }

    public void hideViews() {
        ((ViewGroup) findViewById(16908290)).removeView(this.mRootLayout);
    }

    
    public View findViewById(int id) {
        return this.mParent.findViewById(id);
    }

    public boolean onTouch(View view, MotionEvent event) {
        return true;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (this.mIsShowing) {
            startHideAnimation();
            this.mListener.onTransition();
            this.mIsShowing = false;
            return;
        }
        hideViews();
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
