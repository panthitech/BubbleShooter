package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.level.LevelManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.sound.SoundManager;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {
    public static final String TAG_FRAGMENT = "content";
    public int mContainerId;
    public final Stack<GameDialog> mDialogStack = new Stack<>();
    public LevelManager mLevelManager;
    public SoundManager mSoundManager;

    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(3);
    }

    
    public void setContainerId(int containerId) {
        this.mContainerId = containerId;
    }

    
    public void setLevelManager(LevelManager levelManager) {
        this.mLevelManager = levelManager;
    }

    
    public void setSoundManager(SoundManager soundManager) {
        this.mSoundManager = soundManager;
    }

    public LevelManager getLevelManager() {
        return this.mLevelManager;
    }

    public SoundManager getSoundManager() {
        return this.mSoundManager;
    }

    public void navigateToFragment(GameFragment gameFragment) {
        navigateToFragment(gameFragment, 17432576, 17432577);
    }

    public void navigateToFragment(GameFragment gameFragment, int enterAnimationId, int exitAnimationId) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnimationId, exitAnimationId, enterAnimationId, exitAnimationId).replace(this.mContainerId, (Fragment) gameFragment, TAG_FRAGMENT).addToBackStack((String) null).commit();
    }

    public void navigateBack() {
        getSupportFragmentManager().popBackStack();
    }

    public void showDialog(GameDialog newDialog) {
        this.mDialogStack.push(newDialog);
        newDialog.show();
    }

    public void dismissDialog() {
        this.mDialogStack.pop();
    }

    
    public void onPause() {
        super.onPause();
        if (this.mSoundManager != null) {
            this.mSoundManager.pauseMusic();
        }
    }

    
    public void onResume() {
        super.onResume();
        if (this.mSoundManager != null) {
            this.mSoundManager.resumeMusic();
        }
    }

    
    public void onDestroy() {
        super.onDestroy();
        if (this.mSoundManager != null) {
            this.mSoundManager.unloadMusic();
            this.mSoundManager.unloadSound();
        }
    }

    public void onBackPressed() {
        if (!this.mDialogStack.empty()) {
            this.mDialogStack.peek().dismiss();
            return;
        }
        GameFragment fragment = (GameFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if (fragment == null || !fragment.onBackPressed()) {
            super.onBackPressed();
        }
    }

    
    public void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(5894);
        }
    }
}
