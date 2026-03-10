package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.LevelType;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class LevelDialog extends GameDialog implements View.OnClickListener, TransitionEffect.OnTransitionListener {
    public final TransitionEffect mTransitionEffect;
    MyLevel mLevel;

    public LevelDialog(GameActivity activity, int level) {
        super(activity);
        setContentView(R.layout.dialog_level);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        this.mTransitionEffect = new TransitionEffect(activity);
        this.mTransitionEffect.setListener(this);
        mLevel = new MyLevel(level);
//        init(mLevel);
        init((MyLevel) activity.getLevelManager().getLevel(level));
    }

    public void init(MyLevel mLevel) {
        ((TextView) findViewById(R.id.txt_level)).setText(this.mParent.getResources().getString(R.string.txt_level, new Object[]{Integer.valueOf(mLevel.mLevel)}));
        TextView txtTarget = (TextView) findViewById(R.id.txt_target);
//        if (mLevel.mLevelType == LevelType.POP_BUBBLE) {
//            txtTarget.setText(this.mParent.getResources().getString(R.string.txt_level_type_pop));
//        } else {
//            txtTarget.setText(this.mParent.getResources().getString(R.string.txt_level_type_collect));
//        }
        switch (mLevel.mLevelType) {
            case POP_BUBBLE:
                txtTarget.setText(this.mParent.getResources().getString(R.string.txt_level_type_pop));
                break;
            case COLLECT_ITEM:
                txtTarget.setText(this.mParent.getResources().getString(R.string.txt_level_type_collect));
                break;
        }
        ImageView imgStar = (ImageView) findViewById(R.id.image_star);
        int star = ((MainActivity) this.mParent).getDatabaseHelper().getLevelStar(mLevel.mLevel);
        if (star != -1) {
            switch (star) {
                case 1:
                    imgStar.setImageResource(R.drawable.star_set_01);
                    break;
                case 2:
                    imgStar.setImageResource(R.drawable.star_set_02);
                    break;
                case 3:
                    imgStar.setImageResource(R.drawable.star_set_03);
                    break;
            }
        }
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnPlay.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnPlay);
        UIEffect.createButtonEffect(btnCancel);
        ImageView imagePlayer = (ImageView) findViewById(R.id.image_fox);
        imagePlayer.setImageResource(mLevel.mLevelType.getAnimalDrawableId());
        UIEffect.createPopUpEffect(imagePlayer);
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox_bg), 2);
        UIEffect.createPopUpEffect(txtTarget, 3);
        UIEffect.createPopUpEffect(btnPlay, 4);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_play) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            navigateToGame();
        } else if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        }
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    public void onTransition() {
        dismiss();
        startGame();
    }

    public void navigateToGame() {
        this.mTransitionEffect.show();
    }

    public void startGame() {
    }
}
