package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine.GameEngine;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.input.TouchController;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.level.Level;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.sound.SoundManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import java.util.Random;

public class Game {
    public static boolean sDebugMode = false;
    public final GameActivity mActivity;
    public final GameEngine mGameEngine;
    public final GameView mGameView;
    public Level mLevel;
    public float mPixelFactor;
    public final Random mRandom = new Random();
    public final int mScreenHeight;
    public final int mScreenWidth;
    public SoundManager mSoundManager;
    public TouchController mTouchController;

    public Game(GameActivity activity, GameView gameView) {
        this.mActivity = activity;
        this.mGameView = gameView;
        this.mGameEngine = new GameEngine(gameView);
        this.mScreenWidth = gameView.getWidth();
        this.mScreenHeight = gameView.getHeight();
    }

    public GameActivity getGameActivity() {
        return this.mActivity;
    }

    public GameEngine getGameEngine() {
        return this.mGameEngine;
    }

    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    public int getScreenHeight() {
        return this.mScreenHeight;
    }

    public Random getRandom() {
        return this.mRandom;
    }

    public float getPixelFactor() {
        if (this.mPixelFactor > 0.0f) {
            return this.mPixelFactor;
        }
        throw new IllegalStateException("PixelFactor must be larger than 0!");
    }

    public void setPixelFactor(float basePixel) {
        this.mPixelFactor = ((float) this.mScreenWidth) / basePixel;
    }

    public TouchController getTouchController() {
        if (this.mTouchController != null) {
            return this.mTouchController;
        }
        throw new IllegalStateException("You need to initialize the TouchController!");
    }

    public void setTouchController(TouchController touchController) {
        this.mTouchController = touchController;
        this.mGameView.setOnTouchListener(touchController);
    }

    public SoundManager getSoundManager() {
        if (this.mSoundManager != null) {
            return this.mSoundManager;
        }
        throw new IllegalStateException("You need to initialize the SoundManager!");
    }

    public void setSoundManager(SoundManager soundManager) {
        this.mSoundManager = soundManager;
    }

    public Level getLevel() {
        if (this.mLevel != null) {
            return this.mLevel;
        }
        throw new IllegalStateException("You need to initialize the Level!");
    }

    public void setLevel(Level level) {
        this.mLevel = level;
    }

    public final void start() {
        this.mGameEngine.startGame();
        onStart();
    }

    public final void stop() {
        if (this.mGameEngine.isRunning()) {
            this.mGameEngine.stopGame();
            onStop();
        }
    }

    public final void pause() {
        if (this.mGameEngine.isRunning() && !this.mGameEngine.isPaused()) {
            this.mGameEngine.pauseGame();
            onPause();
        }
    }

    public final void resume() {
        if (this.mGameEngine.isRunning() && this.mGameEngine.isPaused()) {
            this.mGameEngine.resumeGame();
            onResume();
        }
    }

    
    public void onStart() {
    }

    
    public void onStop() {
    }

    
    public void onPause() {
    }

    
    public void onResume() {
    }

    public static boolean getDebugMode() {
        return sDebugMode;
    }

    public static void setDebugMode(boolean debugMode) {
        sDebugMode = debugMode;
    }
}
