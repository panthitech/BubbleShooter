package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import com.puzzle.bubble.shooter.colors.bubbleshooter.AdManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.prize.Prize;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.prize.PrizeManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.timer.WheelTimer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;
import java.util.Random;

public class WheelDialog extends GameDialog implements View.OnClickListener, Animation.AnimationListener, AdManager.AdRewardListener {
    public int mDegree;
    public final PrizeManager mPrizeManager = new PrizeManager();
    public final boolean mWheelReady;
    public final WheelTimer mWheelTimer;

    public WheelDialog(GameActivity activity, WheelTimer wheelTimer) {
        super(activity);
        setContentView(R.layout.dialog_wheel);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        this.mWheelTimer = wheelTimer;
        this.mWheelReady = this.mWheelTimer.isWheelReady();
        init();
    }

    public void init() {
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnCancel.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createButtonEffect(btnPlay);
        if (!this.mWheelReady) {
            btnPlay.setBackgroundResource(R.drawable.btn_watch_ad);
        }
        UIEffect.createPopUpEffect(btnCancel);
        UIEffect.createPopUpEffect(findViewById(R.id.txt_bonus), 2);
        UIEffect.createPopUpEffect(btnPlay, 4);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        } else if (id == R.id.btn_play) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            if (this.mWheelReady) {
                this.mWheelTimer.setWheelTime(System.currentTimeMillis());
                spinWheel();
            } else {
                showAd();
            }
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void spinWheel() {
        this.mDegree = new Random().nextInt(360) + 720;
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, (float) this.mDegree, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(4000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                WheelDialog.this.dismiss();
                WheelDialog.this.showPrize();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        findViewById(R.id.image_wheel).startAnimation(rotateAnimation);
        RotateAnimation rotateAnimation2 = new RotateAnimation(0.0f, -30.0f, 1, 0.5f, 1, 0.35f);
        rotateAnimation2.setDuration(200);
        rotateAnimation2.setRepeatMode(2);
        rotateAnimation2.setRepeatCount(15);
        findViewById(R.id.image_wheel_pointer).startAnimation(rotateAnimation2);
        this.mParent.getSoundManager().playSound(MySoundEvent.WHEEL_SPIN);
    }

    
    public void showPrize() {
        this.mDegree %= 360;
        Prize prize = getPrize(this.mDegree);
        savePrizes(prize.getName(), prize.getNum());
        updateCoin();
        this.mParent.showDialog(new NewBoosterDialog(this.mParent, prize.getDrawableResId()) {
            public void showDialog() {
                WheelDialog.this.showLevel();
            }
        });
    }

    public Prize getPrize(int degree) {
        if (degree < 72) {
            return this.mPrizeManager.getPrize(PrizeManager.PRIZE_FIREBALL);
        }
        if (degree < 144) {
            return this.mPrizeManager.getPrize(PrizeManager.PRIZE_BOMB);
        }
        if (degree < 216) {
            return this.mPrizeManager.getPrize(PrizeManager.PRIZE_COIN_50);
        }
        if (degree < 288) {
            return this.mPrizeManager.getPrize(PrizeManager.PRIZE_COLOR_BALL);
        }
        return this.mPrizeManager.getPrize(PrizeManager.PRIZE_COIN_150);
    }

    public void savePrizes(String name, int amount) {
        DatabaseHelper databaseHelper = ((MainActivity) this.mParent).getDatabaseHelper();
        databaseHelper.updateItemNum(name, databaseHelper.getItemNum(name) + amount);
    }

    
    public void showAd() {
        AdManager ad = ((MainActivity) this.mParent).getAdManager();
        ad.setListener(this);
        if (!ad.showRewardAd()) {
            this.mParent.showDialog(new ErrorDialog(this.mParent) {
                public void retry() {
                    ((MainActivity) this.mParent).getAdManager().requestAd();
                    WheelDialog.this.showAd();
                }
            });
        }
    }

    public void onEarnReward() {
        spinWheel();
    }

    public void onLossReward() {
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    public void updateCoin() {
    }

    public void showLevel() {
    }
}
