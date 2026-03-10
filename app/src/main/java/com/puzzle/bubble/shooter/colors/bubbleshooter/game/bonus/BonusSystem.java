package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bonus;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bonus.BonusBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import java.util.ArrayList;
import java.util.List;

public class BonusSystem extends GameObject implements BonusBubble.BonusTimeEndListener {
    public final List<BonusBubble> mBonusBubbles = new ArrayList();
    public float mStartX;
    public float mStartY;
    public long mTotalTime;

    public BonusSystem(Game game) {
        super(game);
        this.mStartX = ((float) game.getScreenWidth()) / 2.0f;
        this.mStartY = (((float) (game.getScreenHeight() * 4)) / 5.0f) - (game.getPixelFactor() * 300.0f);
    }

    public void addBonusBubble(BubbleColor bubbleColor) {
        this.mBonusBubbles.add(new BonusBubble(this.mGame, bubbleColor));
    }

    public void onStart() {
        this.mBonusBubbles.get(this.mBonusBubbles.size() - 1).setBonusTimeEndListener(this);
    }

    public void onUpdate(long elapsedMillis) {
        this.mTotalTime += elapsedMillis;
        if (this.mTotalTime >= 100) {
            if (!this.mBonusBubbles.isEmpty()) {
                addOneBonusBubble();
            } else {
                removeFromGame();
            }
            this.mTotalTime = 0;
        }
    }

    public void addOneBonusBubble() {
        this.mBonusBubbles.remove(0).activate(this.mStartX, this.mStartY);
        gameEvent(MyGameEvent.BUBBLE_SHOT);
        this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_HIT);
    }

    public void onBonusTimeEnd() {
        gameEvent(MyGameEvent.SHOW_WIN_DIALOG);
    }
}
