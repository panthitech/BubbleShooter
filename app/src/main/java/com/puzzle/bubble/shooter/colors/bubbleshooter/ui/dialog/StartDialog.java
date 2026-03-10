package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.widget.ImageView;
import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class StartDialog extends GameDialog {
    public StartDialog(GameActivity activity, MyLevel level) {
        super(activity);
        setContentView(R.layout.dialog_start);
        setRootLayoutId(R.layout.dialog_game_container);
        setEnterAnimationId(R.anim.enter_from_top);
        setExitAnimationId(R.anim.exit_to_bottom);
        init(level);
    }

    public void init(MyLevel level) {
        ((TextView) findViewById(R.id.txt_target)).setText(level.mLevelType.getTargetStringId());
        ImageView imageTarget = (ImageView) findViewById(R.id.image_start_target);
        imageTarget.setImageResource(level.mLevelType.getTargetDrawableId());
        ((TextView) findViewById(R.id.txt_start_target)).setText(String.valueOf(level.mTarget));
        ImageView imagePlayer = (ImageView) findViewById(R.id.image_fox);
        imagePlayer.setImageResource(level.mLevelType.getAnimalDrawableId());
        UIEffect.createPopUpEffect(imagePlayer);
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox_bg), 2);
        imageTarget.postDelayed(new Runnable() {
            public void run() {
                StartDialog.this.dismiss();
            }
        }, 1500);
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.START_GAME);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    
    public void onHide() {
        startGame();
    }

    public void startGame() {
    }
}
