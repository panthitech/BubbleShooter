package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class PauseDialog extends GameDialog implements View.OnClickListener, TransitionEffect.OnTransitionListener {
    public final int mLevel;
    public int mSelectedId = R.id.btn_resume;
    public final TransitionEffect mTransitionEffect;

    public PauseDialog(GameActivity activity, int level) {
        super(activity);
        setContentView(R.layout.dialog_pause);
        setRootLayoutId(R.layout.dialog_game_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        this.mLevel = level;
        this.mTransitionEffect = new TransitionEffect(activity);
        this.mTransitionEffect.setListener(this);
        init();
    }

    public void init() {
        ((TextView) findViewById(R.id.txt_level)).setText(this.mParent.getResources().getString(R.string.txt_level, new Object[]{Integer.valueOf(this.mLevel)}));
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        ImageButton btnSound = (ImageButton) findViewById(R.id.btn_sound);
        ImageButton btnQuit = (ImageButton) findViewById(R.id.btn_quit);
        ImageButton btnResume = (ImageButton) findViewById(R.id.btn_resume);
        btnMusic.setOnClickListener(this);
        btnSound.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        UIEffect.createButtonEffect(btnMusic);
        UIEffect.createButtonEffect(btnSound);
        UIEffect.createButtonEffect(btnQuit);
        UIEffect.createButtonEffect(btnResume);
        UIEffect.createPopUpEffect(btnMusic);
        UIEffect.createPopUpEffect(btnSound, 1);
        UIEffect.createPopUpEffect(btnQuit, 2);
        updateSoundAndMusicButtons();
    }

    public void updateSoundAndMusicButtons() {
        MySoundManager soundManager = (MySoundManager) this.mParent.getSoundManager();
        boolean music = soundManager.getMusicState();
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        if (music) {
            btnMusic.setBackgroundResource(R.drawable.btn_music_on);
        } else {
            btnMusic.setBackgroundResource(R.drawable.btn_music_off);
        }
        boolean sound = soundManager.getSoundState();
        ImageButton btnSounds = (ImageButton) findViewById(R.id.btn_sound);
        if (sound) {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_on);
        } else {
            btnSounds.setBackgroundResource(R.drawable.btn_sound_off);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_sound) {
            this.mParent.getSoundManager().switchSoundState();
            updateSoundAndMusicButtons();
        } else if (id == R.id.btn_music) {
            this.mParent.getSoundManager().switchMusicState();
            updateSoundAndMusicButtons();
        } else if (id == R.id.btn_quit) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            dismiss();
        } else if (id == R.id.btn_resume) {
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
        if (this.mSelectedId == R.id.btn_quit) {
            this.mTransitionEffect.show();
        } else if (this.mSelectedId == R.id.btn_resume) {
            resumeGame();
        }
    }

    public void onTransition() {
        quitGame();
    }

    public void quitGame() {
    }

    public void resumeGame() {
    }
}
