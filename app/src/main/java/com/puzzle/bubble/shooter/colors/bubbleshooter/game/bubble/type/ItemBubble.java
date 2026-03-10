package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.BubblePopEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.ItemEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class ItemBubble extends Bubble {
    public static final float ITEM_SCALE = 1.3f;
    public final BubblePopEffect mBubblePopEffect;
    public boolean mIsItem = true;
    public final ItemEffect mItemEffect;

    public ItemBubble(Game game) {
        super(game, BubbleColor.ITEM);
        this.mItemEffect = new ItemEffect(game, R.drawable.nut);
        this.mBubblePopEffect = new BubblePopEffect(game);
        this.mLayer++;
    }

    public void onStart() {
        super.onStart();
        this.mScale = ITEM_SCALE;
        this.mItemEffect.mScale = ITEM_SCALE;
        this.mBubblePopEffect.mScale = ITEM_SCALE;
    }

    public void popBubble() {
        if (this.mIsItem) {
            popItem();
        } else {
            super.popBubble();
        }
    }

    public void popFloater() {
        if (this.mIsItem) {
            popItem();
        } else {
            super.popFloater();
        }
    }

    public void popItem() {
        this.mItemEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
        this.mBubblePopEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
        setBubbleColor(BubbleColor.BLANK);
        gameEvent(MyGameEvent.COLLECT_ITEM);
        this.mGame.getSoundManager().playSound(MySoundEvent.COLLECT_ITEM);
        this.mScale = 1.0f;
        this.mIsItem = false;
    }
}
