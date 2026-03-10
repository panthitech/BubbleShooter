package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.CircleCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.CollidableSprite;

public class Dot extends CollidableSprite {
    public Bubble mCollideBubble;
    public boolean mIsCollide;
    public float mMaxX;
    public float mMinX;
    public Dot mNextDot;
    public float mRange;

    public Dot(Game game) {
        super(game, R.drawable.dot_blue);
        int collisionBoxWidth = this.mWidth * 3;
        setCollisionShape(new CircleCollisionShape(collisionBoxWidth, this.mHeight * 3));
        this.mMinX = ((float) collisionBoxWidth) / 2.0f;
        this.mMaxX = ((float) this.mGame.getScreenWidth()) - (((float) collisionBoxWidth) / 2.0f);
        mRange = (this.mMaxX - this.mMinX);
        this.mLayer = 1;
    }

    public void setPosition(float x, float y) {
        if (x > this.mMaxX) {
            float diff = x - this.mMaxX;
            if (((int) (diff / this.mRange)) % 2 == 0) {
                this.mX = (this.mMaxX - (diff % this.mRange)) - (((float) this.mWidth) / 2.0f);
            } else {
                this.mX = (this.mMinX + (diff % this.mRange)) - (((float) this.mWidth) / 2.0f);
            }
        } else if (x < this.mMinX) {
            float diff2 = this.mMinX - x;
            if (((int) (diff2 / this.mRange)) % 2 == 0) {
                this.mX = (this.mMinX + (diff2 % this.mRange)) - (((float) this.mWidth) / 2.0f);
            } else {
                this.mX = (this.mMaxX - (diff2 % this.mRange)) - (((float) this.mWidth) / 2.0f);
            }
        } else {
            this.mX = x - (((float) this.mWidth) / 2.0f);
        }
        this.mY = y - (((float) this.mHeight) / 2.0f);
    }

    public void onUpdate(long elapsedMillis) {
        if (this.mIsCollide) {
            if (this.mNextDot != null) {
                this.mNextDot.mIsCollide = true;
            }
            this.mIsVisible = false;
        } else {
            this.mIsVisible = true;
        }
        this.mCollideBubble = null;
        this.mIsCollide = false;
    }

    public void onCollision(Collidable otherObject) {
        if (otherObject instanceof Bubble) {
            Bubble bubble = (Bubble) otherObject;
            if (bubble.mBubbleColor != BubbleColor.BLANK) {
                this.mCollideBubble = bubble;
                this.mIsCollide = true;
            }
        }
    }
}
