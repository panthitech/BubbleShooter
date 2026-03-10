package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class NewBoosterDialog extends GameDialog implements View.OnClickListener {
    public final int mDrawableResId;

    public NewBoosterDialog(GameActivity activity, int drawableResId) {
        super(activity);
        setContentView(R.layout.dialog_new_booster);
        setRootLayoutId(R.layout.dialog_container);
        this.mDrawableResId = drawableResId;
        init();
    }

    public void init() {
        ImageButton btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        UIEffect.createButtonEffect(btnNext);
        ((ImageView) findViewById(R.id.image_booster)).setImageResource(this.mDrawableResId);
        ((ImageView) findViewById(R.id.image_booster_bg)).startAnimation(AnimationUtils.loadAnimation(this.mParent, R.anim.light_rotate));
        UIEffect.createPopUpEffect(findViewById(R.id.txt_new_booster), 1);
        UIEffect.createPopUpEffect(btnNext, 2);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_next) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        }
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.PLAYER_WIN);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    public void onHide() {
        showDialog();
    }

    public void showDialog() {
    }
}
