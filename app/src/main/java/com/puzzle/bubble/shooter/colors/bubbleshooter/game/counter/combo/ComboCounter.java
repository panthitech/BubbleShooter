package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter.combo;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;

public class ComboCounter extends GameObject {
    public static final int COMBO_GOOD = 25;
    public static final int COMBO_WONDERFUL = 30;
    public static final int COMBO_WOW = 15;
    public boolean mComboHaveChanged = false;
    public final ComboText mComboText;
    public int mConsecutiveHits;
    public long mTotalTime;

    public ComboCounter(Game game) {
        super(game);
        this.mComboText = new ComboText(game);
    }

    public void onStart() {
        this.mConsecutiveHits = 0;
        this.mTotalTime = 0;
    }

    public void onUpdate(long elapsedMillis) {
        if (this.mComboHaveChanged) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= 500) {
                this.mComboText.activate(getCombo());
                this.mComboHaveChanged = false;
                this.mConsecutiveHits = 0;
                this.mTotalTime = 0;
            }
        }
    }

    public Combo getCombo() {
        if (this.mConsecutiveHits >= 30) {
            gameEvent(MyGameEvent.EMIT_CONFETTI);
            return Combo.WONDERFUL;
        } else if (this.mConsecutiveHits >= 25) {
            return Combo.GOOD;
        } else {
            return Combo.WOW;
        }
    }

    public void onGameEvent(GameEvent gameEvents) {
        switch ((MyGameEvent) gameEvents) {
            case BUBBLE_POP:
                this.mConsecutiveHits++;
                return;
            case BUBBLE_CONSUMED:
            case BOOSTER_CONSUMED:
                if (this.mConsecutiveHits < 15) {
                    this.mConsecutiveHits = 0;
                    return;
                } else {
                    this.mComboHaveChanged = true;
                    return;
                }
            case GAME_WIN:
            case GAME_OVER:
                removeFromGame();
                return;
            default:
                return;
        }
    }
}
