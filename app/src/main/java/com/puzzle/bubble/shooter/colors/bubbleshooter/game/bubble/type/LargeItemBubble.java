package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.BubblePopEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.ItemEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.CircleCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.CollidableSprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import java.util.ArrayList;
import java.util.List;

public class LargeItemBubble extends CompositeBubble {
    public static final int ITEMS_PER_BUBBLE = 5;
    public final BubblePopEffect mBubblePopEffect;
    public boolean mCollected = false;
    public boolean mIsItem = true;
    public final List<ItemEffect> mItemEffectsPool = new ArrayList(5);
    public long mTotalTime;

    public LargeItemBubble(Game game) {
        super(game, BubbleColor.LARGE_ITEM);
        this.mBubblePopEffect = new BubblePopEffect(game);
        for (int i = 0; i < 5; i++) {
            this.mItemEffectsPool.add(new ItemEffect(game, R.drawable.nut));
        }
        this.mLayer++;
    }

    public void onStart() {
        super.onStart();
        this.mX -= ((float) this.mWidth) / 3.0f;
        this.mY -= ((float) this.mHeight) / 3.0f;
        this.mBubblePopEffect.mScale = 3.0f;
        for (Bubble b : this.mEdges) {
            addDummyBubble((DummyBubble) b);
        }
    }

    public void onUpdate(long elapsedMillis) {
        super.onUpdate(elapsedMillis);
        if (this.mCollected) {
            this.mTotalTime += elapsedMillis;
            if (this.mTotalTime >= 150) {
                if (!this.mItemEffectsPool.isEmpty()) {
                    activateItemEffect();
                } else {
                    this.mCollected = false;
                }
                this.mTotalTime = 0;
            }
        }
    }

    public void popBubble() {
        if (this.mIsItem) {
            popLargeItem();
        } else {
            super.popBubble();
        }
    }

    public void popFloater() {
        if (this.mIsItem) {
            popLargeItem();
        } else {
            super.popFloater();
        }
    }

    public void popLargeItem() {
        this.mBubblePopEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
        activateItemEffect();
        setBubbleColor(BubbleColor.BLANK);
        setCollisionShape(new CircleCollisionShape(this.mWidth, this.mHeight));
        clearDummyBubble();
        for (int i = 0; i < 5; i++) {
            gameEvent(MyGameEvent.COLLECT_ITEM);
        }
        this.mGame.getSoundManager().playSound(MySoundEvent.COLLECT_ITEM);
        this.mX += (float) this.mWidth;
        this.mY += (float) this.mHeight;
        this.mLayer--;
        this.mCollected = true;
        this.mIsItem = false;
    }

    public void activateItemEffect() {
        this.mItemEffectsPool.remove(0).activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
    }

    public Bubble getCollidedBubble(CollidableSprite collidableSprite) {
        if (!this.mIsItem) {
            return super.getCollidedBubble(collidableSprite);
        }
        if (collidableSprite.mY > this.mY + (((float) (this.mHeight * 3)) / 4.0f)) {
            if (collidableSprite.mX > this.mX + (((float) (this.mWidth * 2)) / 3.0f)) {
                return this.mEdges[5].mEdges[5];
            }
            if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 3.0f)) {
                return this.mEdges[5].mEdges[4];
            }
            return this.mEdges[4].mEdges[4];
        } else if (collidableSprite.mY > this.mY + (((float) this.mHeight) / 2.0f)) {
            if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 2.0f)) {
                return this.mEdges[3].mEdges[5];
            }
            return this.mEdges[2].mEdges[4];
        } else if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 2.0f)) {
            return this.mEdges[3].mEdges[3];
        } else {
            return this.mEdges[2].mEdges[2];
        }
    }
}
