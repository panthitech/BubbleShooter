package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.target;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;

public class PopTargetCounter extends TargetCounter {
    public PopTargetCounter(Game game) {
        super(game);
    }

    
    public boolean isTargetReach() {
        return this.mPoints == 0;
    }

    public void onStart() {
        super.onStart();
        this.mPoints = this.mTarget;
    }

    
    public void onDrawUI() {
        this.mText.setText(String.valueOf(this.mPoints));
    }

    public void onGameEvent(GameEvent gameEvents) {
        super.onGameEvent(gameEvents);
        switch ((MyGameEvent) gameEvents) {
            case BUBBLE_POP:
                this.mPoints--;
                drawUI();
                if (isTargetReach()) {
                    this.mTargetHaveReached = true;
                    return;
                }
                return;
            case BUBBLE_HIT:
                this.mPoints++;
                drawUI();
                return;
            default:
                return;
        }
    }
}
