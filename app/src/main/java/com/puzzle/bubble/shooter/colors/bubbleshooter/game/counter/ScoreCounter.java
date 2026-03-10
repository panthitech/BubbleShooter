package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter;

import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.UIGameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;

public class ScoreCounter extends UIGameObject {
    public static final int POINTS_GAINED_PER_BUBBLE = 10;
    public final MyLevel mLevel;
    public int mPoints;
    public final TextView mText;

    public ScoreCounter(Game game) {
        super(game);
        this.mLevel = (MyLevel) game.getLevel();
        this.mText = (TextView) game.getGameActivity().findViewById(R.id.txt_score);
    }

    public void onStart() {
        this.mPoints = 0;
        drawUI();
    }

    public void onUpdate(long elapsedMillis) {
    }

    
    public void onDrawUI() {
        this.mText.setText(String.valueOf(this.mPoints));
    }

    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == MyGameEvent.BUBBLE_POP) {
            this.mPoints += 10;
            this.mLevel.mScore = this.mPoints;
            drawUI();
        }
    }
}
