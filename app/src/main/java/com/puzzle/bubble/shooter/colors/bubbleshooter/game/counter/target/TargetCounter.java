package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.target;

import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.UIGameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;

public abstract class TargetCounter extends UIGameObject {
    public boolean mMoveHaveChanged = false;
    protected int mPoints;
    protected final int mTarget;
    protected boolean mTargetHaveReached = false;
    protected final TextView mText;

    
    public abstract boolean isTargetReach();

    protected TargetCounter(Game game) {
        super(game);
        this.mText = (TextView) game.getGameActivity().findViewById(R.id.txt_target);
        this.mTarget = ((MyLevel) game.getLevel()).mTarget;
    }

    public void onStart() {
        drawUI();
    }

    public void onUpdate(long elapsedMillis) {
        if (this.mTargetHaveReached) {
            gameEvent(MyGameEvent.GAME_WIN);
            removeFromGame();
        }
        if (this.mMoveHaveChanged) {
            if (((MyLevel) this.mGame.getLevel()).mMove == 0 && !isTargetReach()) {
                gameEvent(MyGameEvent.GAME_OVER);
            }
            this.mMoveHaveChanged = false;
        }
    }

    public void onGameEvent(GameEvent gameEvents) {
        if (((MyGameEvent) gameEvents) == MyGameEvent.BUBBLE_CONSUMED) {
            this.mMoveHaveChanged = true;
        }
    }
}
