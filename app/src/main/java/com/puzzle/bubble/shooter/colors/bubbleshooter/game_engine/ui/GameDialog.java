package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class GameDialog implements View.OnTouchListener, Animation.AnimationListener {
    public int mEnterAnimationId;
    public int mExitAnimationId;
    public boolean mIsHiding = true;
    public boolean mIsShowing = false;
    
    public final GameActivity mParent;
    public ViewGroup mRootLayout;
    public int mRootLayoutId;
    public View mRootView;

    protected GameDialog(GameActivity activity) {
        this.mParent = activity;
    }

    
    public void setContentView(int layoutId) {
        this.mRootView = LayoutInflater.from(this.mParent).inflate(layoutId, (ViewGroup) this.mParent.findViewById(16908290), false);
    }

    
    public void setRootLayoutId(int rootLayoutId) {
        this.mRootLayoutId = rootLayoutId;
    }

    
    public void setEnterAnimationId(int enterAnimationId) {
        this.mEnterAnimationId = enterAnimationId;
    }

    
    public void setExitAnimationId(int exitAnimationId) {
        this.mExitAnimationId = exitAnimationId;
    }

    public final void show() {
        if (!this.mIsShowing) {
            this.mIsShowing = true;
            this.mIsHiding = false;
            ViewGroup activityRoot = (ViewGroup) this.mParent.findViewById(16908290);
            this.mRootLayout = (ViewGroup) LayoutInflater.from(this.mParent).inflate(this.mRootLayoutId, activityRoot, false);
            activityRoot.addView(this.mRootLayout);
            this.mRootLayout.setOnTouchListener(this);
            this.mRootLayout.addView(this.mRootView);
            startShowAnimation();
            onShow();
        }
    }

    public final void dismiss() {
        if (this.mIsShowing && !this.mIsHiding) {
            this.mIsHiding = true;
            this.mParent.dismissDialog();
            startHideAnimation();
            onDismiss();
        }
    }

    
    public void onShow() {
    }

    
    public void onDismiss() {
    }

    
    public void onHide() {
    }

    public boolean isShowing() {
        return this.mIsShowing;
    }

    public void startShowAnimation() {
        if (this.mEnterAnimationId != 0) {
            this.mRootView.startAnimation(AnimationUtils.loadAnimation(this.mParent, this.mEnterAnimationId));
        }
    }

    public void startHideAnimation() {
        if (this.mExitAnimationId == 0) {
            hideViews();
            return;
        }
        Animation exitAnimation = AnimationUtils.loadAnimation(this.mParent, this.mExitAnimationId);
        exitAnimation.setAnimationListener(this);
        this.mRootView.startAnimation(exitAnimation);
    }

    public void hideViews() {
        this.mIsShowing = false;
        this.mRootLayout.removeView(this.mRootView);
        ((ViewGroup) this.mParent.findViewById(16908290)).removeView(this.mRootLayout);
        onHide();
    }

    
    public View findViewById(int id) {
        return this.mRootView.findViewById(id);
    }

    public boolean onTouch(View view, MotionEvent event) {
        return true;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        hideViews();
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
