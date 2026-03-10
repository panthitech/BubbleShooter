package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.widget.ImageView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class LossDialog extends GameDialog implements TransitionEffect.OnTransitionListener {
    public final TransitionEffect mTransitionEffect;

    public LossDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_loss);
        setRootLayoutId(R.layout.dialog_game_container);
        setEnterAnimationId(R.anim.enter_from_top);
        setExitAnimationId(R.anim.exit_to_bottom);
        this.mTransitionEffect = new TransitionEffect(activity);
        this.mTransitionEffect.setListener(this);
        init();
    }

    public void init() {
        ImageView imageFox = (ImageView) findViewById(R.id.image_fox);
        UIEffect.createPopUpEffect(imageFox);
        ((MainActivity) this.mParent).getLivesTimer().reduceLive();
        imageFox.postDelayed(new Runnable() {
            public void run() {
                LossDialog.this.dismiss();
            }
        }, 1500);
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.PLAYER_LOSS);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    
    public void onHide() {
        this.mTransitionEffect.show();
    }

    public void onTransition() {
        stopGame();
        this.mParent.navigateBack();
    }

    public void stopGame() {
    }
}
