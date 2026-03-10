package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class ErrorDialog extends GameDialog implements View.OnClickListener {
    public int mSelectedId = R.id.btn_cancel;

    public ErrorDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_error);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        init();
    }

    public void init() {
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        ImageButton btnRetry = (ImageButton) findViewById(R.id.btn_retry);
        btnCancel.setOnClickListener(this);
        btnRetry.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createButtonEffect(btnRetry);
        UIEffect.createPopUpEffect(btnRetry);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            dismiss();
        } else if (id == R.id.btn_retry) {
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
        if (this.mSelectedId == R.id.btn_cancel) {
            quit();
        } else if (this.mSelectedId == R.id.btn_retry) {
            retry();
        }
    }

    public void quit() {
    }

    public void retry() {
    }
}
