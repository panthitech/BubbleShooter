package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.PlayerBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public abstract class BoosterBubble extends PlayerBubble {
    protected boolean mConsume;

    protected BoosterBubble(BubbleSystem bubbleSystem, Game game, int drawableId) {
        super(bubbleSystem, game, drawableId);
    }

    public void onStart() {
        super.onStart();
        this.mConsume = false;
        gameEvent(MyGameEvent.BOOSTER_ADDED);
        this.mGame.getSoundManager().playSound(MySoundEvent.ADD_BOOSTER);
    }

    public void onRemove() {
        super.onRemove();
        if (this.mConsume) {
            gameEvent(MyGameEvent.BOOSTER_CONSUMED);
        } else {
            gameEvent(MyGameEvent.BOOSTER_REMOVED);
        }
    }

    
    public void onBubbleShoot() {
        gameEvent(MyGameEvent.BOOSTER_SHOT);
        this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_SHOOT);
    }

    
    public void onBubbleSwitch() {
    }

    
    public void onBubbleReset() {
        this.mBubbleSystem.popFloater();
        this.mBubbleSystem.shiftBubble();
        this.mConsume = true;
        removeFromGame();
    }
}
