package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class SettingDialog extends GameDialog implements View.OnClickListener {
    public static final String HINT_PREF_KEY = "hint";
    public static final String PREFS_NAME = "prefs_setting";
    public SharedPreferences mPrefs;
    public boolean mHintEnable;


    public SettingDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_setting);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        this.mPrefs = activity.getSharedPreferences(PREFS_NAME, 0);
        init();
        mHintEnable = mPrefs.getBoolean(HINT_PREF_KEY, true);
    }

    public void init() {
        ImageButton btnMusic = (ImageButton) findViewById(R.id.btn_music);
        btnMusic.setOnClickListener(this);
        UIEffect.createButtonEffect(btnMusic);
        ImageButton btnSound = (ImageButton) findViewById(R.id.btn_sound);
        btnSound.setOnClickListener(this);
        UIEffect.createButtonEffect(btnSound);
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        ImageButton btnHint = (ImageButton) findViewById(R.id.switch_hint_thumb);
        btnHint.setOnClickListener(this);
        UIEffect.createButtonEffect(btnHint);
        UIEffect.createPopUpEffect(btnMusic);
        UIEffect.createPopUpEffect(btnSound, 1);
        UIEffect.createPopUpEffect(btnHint, 2);
        UIEffect.createPopUpEffect(findViewById(R.id.switch_hint_track), 2);
        updateSoundAndMusicButtons();
        btnHint.postDelayed(new Runnable() {
            public void run() {
                SettingDialog.this.updateHintButton();
            }
        }, 10);
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

    
    public void updateHintButton() {
        ImageView imageThumb = (ImageView) findViewById(R.id.switch_hint_thumb);
        ImageView imageTrack = (ImageView) findViewById(R.id.switch_hint_track);
        if (this.mHintEnable) {
            imageThumb.setX(imageTrack.getX() + (((float) imageTrack.getWidth()) / 2.0f));
            imageTrack.setImageResource(R.drawable.switch_track_on);
            return;
        }
        imageThumb.setX(imageTrack.getX());
        imageTrack.setImageResource(R.drawable.switch_track_off);
    }

    public void toggleHintStatus() {
        this.mHintEnable = !this.mHintEnable;
        this.mPrefs.edit().putBoolean(HINT_PREF_KEY, this.mHintEnable).apply();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_sound) {
            this.mParent.getSoundManager().switchSoundState();
            updateSoundAndMusicButtons();
        } else if (id == R.id.btn_music) {
            this.mParent.getSoundManager().switchMusicState();
            updateSoundAndMusicButtons();
        } else if (view.getId() == R.id.switch_hint_thumb) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            toggleHintStatus();
            updateHintButton();
        } else if (id == R.id.btn_cancel) {
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
}
