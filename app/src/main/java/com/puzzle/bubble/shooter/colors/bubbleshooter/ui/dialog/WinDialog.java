package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class WinDialog extends GameDialog implements View.OnClickListener, TransitionEffect.OnTransitionListener {
    public static final int EXPLODE_DURATION = 250;
    public ConstraintLayout mDialogLayout;
    public final int mLevel;
    public final int mScore;
    public final int mStar;
    public ImageView mStar1;
    public ImageView mStar2;
    public ImageView mStar3;
    public final TransitionEffect mTransitionEffect;

    public WinDialog(GameActivity activity, MyLevel level) {
        super(activity);
        setContentView(R.layout.dialog_win);
        setRootLayoutId(R.layout.dialog_game_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(17432577);
        this.mLevel = level.mLevel;
        this.mScore = level.mScore;
        this.mStar = level.mStar;
        this.mTransitionEffect = new TransitionEffect(activity);
        this.mTransitionEffect.setListener(this);
        init();
    }

    public void init() {
        mStar1 = ((ImageView) findViewById(R.id.image_win_star_01));
        mStar2 = ((ImageView) findViewById(R.id.image_win_star_02));
        mStar3 = ((ImageView) findViewById(R.id.image_win_star_03));
        mDialogLayout = ((ConstraintLayout) findViewById(R.id.layout_win_dialog));
        ((TextView) findViewById(R.id.txt_level)).setText(this.mParent.getResources().getString(R.string.txt_level, new Object[]{Integer.valueOf(this.mLevel)}));
        ImageButton btnNext = (ImageButton) findViewById(R.id.btn_win_next);
        btnNext.setOnClickListener(this);
        UIEffect.createPopUpEffect(btnNext, 10);
        UIEffect.createButtonEffect(btnNext);
        ImageView imageBg = (ImageView) findViewById(R.id.image_fox_bg);
        imageBg.startAnimation(AnimationUtils.loadAnimation(this.mParent, R.anim.light_rotate));
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox));
        UIEffect.createPopUpEffect(imageBg, 2);
        insertOrUpdateStar();
        startAnimation();
    }

    public void insertOrUpdateStar() {
        DatabaseHelper databaseHelper = ((MainActivity) this.mParent).getDatabaseHelper();
        int oldStar = databaseHelper.getLevelStar(this.mLevel);
        if (oldStar == -1) {
            databaseHelper.insertLevelStar(this.mStar);
        } else if (this.mStar > oldStar) {
            databaseHelper.updateLevelStar(this.mLevel, this.mStar);
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_win_next) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        }
    }

    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.GAME_COMPLETE);
    }

    public void onDismiss() {
        stopGame();
        this.mTransitionEffect.show();
    }

    public void onTransition() {
        this.mParent.navigateBack();
    }

    public void stopGame() {
    }

    public void startAnimation() {
        final TextView txtScore = (TextView) findViewById(R.id.txt_win_score);
        final ValueAnimator animator = ValueAnimator.ofFloat(new float[]{(float) (this.mScore - 150), (float) this.mScore});
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                txtScore.setText(String.valueOf((int) ((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        txtScore.postDelayed(new Runnable() {
            public void run() {
                animator.start();
                WinDialog.this.mParent.getSoundManager().playSound(MySoundEvent.CALCULATE_SCORE);
            }
        }, 500);
        if (this.mStar >= 1) {
            this.mStar1.postDelayed(new Runnable() {
                public void run() {
                    WinDialog.this.createStarAnimation(WinDialog.this.mStar1);
                }
            }, 700);
        }
        if (this.mStar >= 2) {
            this.mStar2.postDelayed(new Runnable() {
                public void run() {
                    WinDialog.this.createStarAnimation(WinDialog.this.mStar2);
                }
            }, 1000);
        }
        if (this.mStar >= 3) {
            this.mStar3.postDelayed(new Runnable() {
                public void run() {
                    WinDialog.this.createStarAnimation(WinDialog.this.mStar3);
                }
            }, 1300);
        }
    }

    public void createStarAnimation(final ImageView view) {
        view.animate().setDuration(300).scaleX(2.0f).scaleY(2.0f).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                view.animate().setDuration(100).scaleX(1.0f).scaleY(1.0f).setInterpolator(new OvershootInterpolator());
            }
        });
        createExplosion(view);
        createSparkle(view);
        this.mParent.getSoundManager().playSound(MySoundEvent.ADD_STAR);
    }

    public void createExplosion(ImageView view) {
        int x = (int) view.getX();
        int y = (int) view.getY();
        int width = view.getWidth();
        final ImageView flash1 = new ImageView(this.mParent);
        flash1.setImageResource(R.drawable.flash_bar);
        flash1.setX(((float) x) + (((float) width) * 0.5f));
        flash1.setY(((float) y) + (((float) width) * 0.25f));
        flash1.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash1.setRotation(45.0f);
        flash1.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x(((float) x) + (((float) width) * 0.5f) + ((float) width)).y((((float) y) + (((float) width) * 0.25f)) - ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash1);
            }
        });
        this.mDialogLayout.addView(flash1);
        final ImageView flash2 = new ImageView(this.mParent);
        flash2.setImageResource(R.drawable.flash_bar);
        flash2.setX(((float) x) + (((float) width) * 0.5f));
        flash2.setY(((float) y) + (((float) width) * 0.5f));
        flash2.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash2.setRotation(135.0f);
        flash2.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x(((float) x) + (((float) width) * 0.5f) + ((float) width)).y(((float) y) + (((float) width) * 0.5f) + ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash2);
            }
        });
        this.mDialogLayout.addView(flash2);
        final ImageView flash3 = new ImageView(this.mParent);
        flash3.setImageResource(R.drawable.flash_bar);
        flash3.setX(((float) x) + (((float) width) * 0.25f));
        flash3.setY(((float) y) + (((float) width) * 0.5f));
        flash3.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash3.setRotation(225.0f);
        flash3.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x((((float) x) + (((float) width) * 0.25f)) - ((float) width)).y(((float) y) + (((float) width) * 0.5f) + ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash3);
            }
        });
        this.mDialogLayout.addView(flash3);
        final ImageView flash4 = new ImageView(this.mParent);
        flash4.setImageResource(R.drawable.flash_bar);
        flash4.setX(((float) x) + (((float) width) * 0.25f));
        flash4.setY(((float) y) + (((float) width) * 0.25f));
        flash4.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash4.setRotation(315.0f);
        flash4.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x((((float) x) + (((float) width) * 0.25f)) - ((float) width)).y((((float) y) + (((float) width) * 0.25f)) - ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash4);
            }
        });
        this.mDialogLayout.addView(flash4);
        final ImageView flash5 = new ImageView(this.mParent);
        flash5.setImageResource(R.drawable.flash_bar);
        flash5.setX(((float) x) + (((float) width) * 0.375f));
        flash5.setY(((float) y) + (((float) width) * 0.25f));
        flash5.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash5.setRotation(0.0f);
        flash5.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).y((((float) y) + (((float) width) * 0.25f)) - ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash5);
            }
        });
        this.mDialogLayout.addView(flash5);
        final ImageView flash6 = new ImageView(this.mParent);
        flash6.setImageResource(R.drawable.flash_bar);
        flash6.setX(((float) x) + (((float) width) * 0.5f));
        flash6.setY(((float) y) + (((float) width) * 0.375f));
        flash6.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash6.setRotation(90.0f);
        flash6.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x(((float) x) + (((float) width) * 0.5f) + ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash6);
            }
        });
        this.mDialogLayout.addView(flash6);
        final ImageView flash7 = new ImageView(this.mParent);
        flash7.setImageResource(R.drawable.flash_bar);
        flash7.setX(((float) x) + (((float) width) * 0.375f));
        flash7.setY(((float) y) + (((float) width) * 0.5f));
        flash7.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash7.setRotation(180.0f);
        flash7.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).y(((float) y) + (((float) width) * 0.5f) + ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash7);
            }
        });
        this.mDialogLayout.addView(flash7);
        final ImageView flash8 = new ImageView(this.mParent);
        flash8.setImageResource(R.drawable.flash_bar);
        flash8.setX(((float) x) + (((float) width) * 0.25f));
        flash8.setY(((float) y) + (((float) width) * 0.375f));
        flash8.setLayoutParams(new ViewGroup.LayoutParams(width / 4, width / 4));
        flash8.setRotation(270.0f);
        flash8.animate().setDuration(250).scaleX(6.0f).scaleY(6.0f).alpha(0.0f).x((((float) x) + (((float) width) * 0.25f)) - ((float) width)).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                WinDialog.this.mDialogLayout.removeView(flash8);
            }
        });
        this.mDialogLayout.addView(flash8);
    }

    public void createSparkle(ImageView view) {
        float f;
        double d;
        int width = view.getWidth() / 9;
        int height = view.getHeight() / 9;
        int x = (int) (view.getX() + ((float) ((view.getWidth() * 4) / 9)));
        int y = (int) (view.getY() + ((float) ((view.getWidth() * 4) / 9)));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final ImageView sparkle = new ImageView(this.mParent);
                sparkle.setImageResource(R.drawable.sparkle);
                sparkle.setX((float) x);
                sparkle.setY((float) y);
                sparkle.setLayoutParams(new ViewGroup.LayoutParams(width, height));
                ViewPropertyAnimator rotation = sparkle.animate().setDuration((long) ((Math.random() * 300.0d) + 300.0d)).scaleX(5.0f).scaleY(5.0f).alpha(0.0f).rotation(Math.random() > 0.5d ? 180.0f : -180.0f);
                if (j < 2) {
                    f = (float) (((double) x) - (((double) (width * 9)) * Math.random()));
                } else {
                    f = (float) (((double) x) + (((double) (width * 9)) * Math.random()));
                }
                ViewPropertyAnimator x2 = rotation.x(f);
                if (i < 2) {
                    d = ((double) y) - (((double) (height * 9)) * Math.random());
                } else {
                    d = ((double) y) + (((double) (height * 9)) * Math.random());
                }
                x2.y((float) d).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        WinDialog.this.mDialogLayout.removeView(sparkle);
                    }
                });
                this.mDialogLayout.addView(sparkle);
            }
        }
    }
}
