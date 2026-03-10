package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class AdExtraMoveDialog extends GameDialog implements View.OnClickListener {
    public int mSelectedId = R.id.btn_quit;

    public AdExtraMoveDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_ad_extra_move);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        init();
    }

    public void init() {
        ImageButton btnWatchAd = (ImageButton) findViewById(R.id.btn_watch_ad);
        ImageButton btnQuit = (ImageButton) findViewById(R.id.btn_quit);
        btnWatchAd.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        UIEffect.createButtonEffect(btnWatchAd);
        UIEffect.createButtonEffect(btnQuit);
        UIEffect.createPopUpEffect(btnWatchAd);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_quit) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            dismiss();
        } else if (id == R.id.btn_watch_ad) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            dismiss();
        }
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    
    public void onHide() {
        if (this.mSelectedId == R.id.btn_watch_ad) {
            showAd();
        } else if (this.mSelectedId == R.id.btn_quit) {
            quit();
        }
    }

    public void showAd() {
    }

    public void quit() {
    }
}
