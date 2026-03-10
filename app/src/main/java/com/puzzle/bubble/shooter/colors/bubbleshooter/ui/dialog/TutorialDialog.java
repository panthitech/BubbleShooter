package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.LevelTutorial;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class TutorialDialog extends GameDialog implements View.OnClickListener {
    public final LevelTutorial mTutorial;

    public TutorialDialog(GameActivity activity, MyLevel level) {
        super(activity);
        setContentView(R.layout.dialog_tutorial);
        setRootLayoutId(R.layout.dialog_game_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        this.mTutorial = level.mLevelTutorial;
        init();
    }

    public void init() {
        ((TextView) findViewById(R.id.txt_tutorial)).setText(this.mTutorial.getStringId());
        ((ImageView) findViewById(R.id.image_tutorial)).setImageResource(this.mTutorial.getDrawableId());
        ImageButton btnPlay = (ImageButton) findViewById(R.id.btn_play);
        btnPlay.setOnClickListener(this);
        UIEffect.createButtonEffect(btnPlay);
        UIEffect.createPopUpEffect(btnPlay);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_play) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
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
        switch (this.mTutorial) {
            case COLOR_BUBBLE:
                addBooster(Item.COLOR_BALL);
                updateBooster();
                this.mParent.findViewById(R.id.btn_color_bubble).performClick();
                return;
            case FIRE_BUBBLE:
                addBooster(Item.FIREBALL);
                updateBooster();
                this.mParent.findViewById(R.id.btn_fire_bubble).performClick();
                return;
            case BOMB_BUBBLE:
                addBooster(Item.BOMB);
                updateBooster();
                this.mParent.findViewById(R.id.btn_bomb_bubble).performClick();
                return;
            default:
                return;
        }
    }

    public void addBooster(String name) {
        DatabaseHelper databaseHelper = ((MainActivity) this.mParent).getDatabaseHelper();
        databaseHelper.updateItemNum(name, databaseHelper.getItemNum(name) + 1);
    }

    public void updateBooster() {
    }
}
