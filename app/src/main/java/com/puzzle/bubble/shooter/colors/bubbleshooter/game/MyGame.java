package com.puzzle.bubble.shooter.colors.bubbleshooter.game;

import android.widget.ImageView;

import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.MoveCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.ProgressBarCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.ScoreCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.combo.ComboCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.target.CollectTargetCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.target.PopTargetCounter;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.input.InputController;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.GameView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.LevelType;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.PauseDialog;

public class MyGame extends Game {

    public MyGame(GameActivity activity, GameView gameView, int level) {
        super(activity, gameView);
        setPixelFactor(3300.0f);
        setTouchController(new InputController(this));
        setSoundManager(getGameActivity().getSoundManager());
        setLevel(getGameActivity().getLevelManager().getLevel(level));
        new GameController(this).addToGame();
        new MoveCounter(this).addToGame();
        new ComboCounter(this).addToGame();
        new ScoreCounter(this).addToGame();
        new ProgressBarCounter(this).addToGame();
        setTargetCounter(((MyLevel) getLevel()).mLevelType);
//        new Animal(this).addToGame();
    }

    public void setTargetCounter(LevelType levelType) {
        ((ImageView) getGameActivity().findViewById(R.id.image_target)).setImageResource(levelType.getTargetDrawableId());
        switch (levelType) {
            case POP_BUBBLE:
                new PopTargetCounter(this).addToGame();
                return;
            case COLLECT_ITEM:
                new CollectTargetCounter(this).addToGame();
                return;
            default:
                return;
        }
    }

    
    public void onStart() {
        getGameActivity().getSoundManager().loadMusic(R.raw.village);
    }

    
    public void onPause() {
        showPauseDialog();
    }

    
    public void onStop() {
        getGameActivity().getSoundManager().unloadMusic();
        getGameActivity().getSoundManager().loadMusic(R.raw.happy_and_joyful_children);
    }

    public void showPauseDialog() {
        getGameActivity().showDialog(new PauseDialog(getGameActivity(), getLevel().mLevel) {
            public void resumeGame() {
                MyGame.this.resume();
            }

            public void quitGame() {
                ((MainActivity) MyGame.this.getGameActivity()).getLivesTimer().reduceLive();
                MyGame.this.getGameActivity().navigateBack();
            }
        });
    }
}
