package com.puzzle.bubble.shooter.colors.bubbleshooter.game.input;

import android.view.View;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.input.TouchController;

public class InputController extends TouchController implements View.OnClickListener {
    public final Game mGame;
    public final float mShootThreshold;

    public InputController(Game game) {
        this.mGame = game;
        this.mShootThreshold = (((float) (game.getScreenHeight() * 4)) / 5.0f) - (game.getPixelFactor() * 500.0f);
        game.getGameActivity().findViewById(R.id.txt_move).setOnClickListener(this);
    }

    public void onClick(View view) {
        this.mGame.getGameEngine().onGameEvent(MyGameEvent.SWITCH_BUBBLE);
    }

    
    public void onActionDown() {
        if (this.mYDown < this.mShootThreshold) {
            this.mTouching = true;
        }
    }

    
    public void onActionMove() {
        if (this.mYDown < this.mShootThreshold) {
            this.mTouching = true;
        } else {
            this.mTouching = false;
        }
    }

    
    public void onActionUp() {
        this.mTouching = false;
        if (this.mYUp < this.mShootThreshold) {
            this.mGame.getGameEngine().onGameEvent(MyGameEvent.SHOOT_BUBBLE);
        }
    }
}
