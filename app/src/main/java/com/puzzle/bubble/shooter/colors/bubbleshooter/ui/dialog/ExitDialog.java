package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class ExitDialog extends GameDialog implements View.OnClickListener {
    public int mSelectedId;

    public ExitDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_exit);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        init();
    }

    public void init() {
        ImageButton btnExit = (ImageButton) findViewById(R.id.btn_exit);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnExit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnExit);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createPopUpEffect(btnExit);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        } else if (id == R.id.btn_exit) {
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
        if (this.mSelectedId == R.id.btn_exit) {
            exit();
        }
    }

    public void exit() {
    }
}
