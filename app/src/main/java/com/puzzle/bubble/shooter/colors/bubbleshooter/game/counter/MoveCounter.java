package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter;

import android.widget.TextView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.UIGameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;

public class MoveCounter extends UIGameObject {
    public static final int EXTRA_MOVES = 5;
    public final MyLevel mLevel;
    public int mMoves;
    public final TextView mText;

    public MoveCounter(Game game) {
        super(game);
        this.mLevel = (MyLevel) game.getLevel();
        this.mText = (TextView) game.getGameActivity().findViewById(R.id.txt_move);
    }

    public void onStart() {
        this.mMoves = this.mLevel.mMove;
        drawUI();
    }

    public void onUpdate(long elapsedMillis) {
    }

    
    public void onDrawUI() {
        this.mText.setText(String.valueOf(this.mMoves));
    }

    public void onGameEvent(GameEvent gameEvents) {
        switch ((MyGameEvent) gameEvents) {
            case BUBBLE_SHOT:
                this.mMoves--;
                this.mLevel.mMove = this.mMoves;
                drawUI();
                return;
            case ADD_EXTRA_MOVE:
                this.mMoves += 5;
                this.mLevel.mMove = this.mMoves;
                drawUI();
                return;
            default:
                return;
        }
    }
}
