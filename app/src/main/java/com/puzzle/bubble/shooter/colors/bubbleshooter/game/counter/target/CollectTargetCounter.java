package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.target;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;

public class CollectTargetCounter extends TargetCounter {
    public final String mTargetText = ("/" + this.mTarget);

    public CollectTargetCounter(Game game) {
        super(game);
    }

    
    public boolean isTargetReach() {
        return this.mPoints == this.mTarget;
    }

    public void onStart() {
        super.onStart();
        this.mPoints = 0;
    }

    
    public void onDrawUI() {
        this.mText.setText(this.mPoints + this.mTargetText);
    }

    public void onGameEvent(GameEvent gameEvents) {
        super.onGameEvent(gameEvents);
        if (((MyGameEvent) gameEvents) == MyGameEvent.COLLECT_ITEM) {
            this.mPoints++;
            drawUI();
            if (isTargetReach()) {
                this.mTargetHaveReached = true;
            }
        }
    }
}
