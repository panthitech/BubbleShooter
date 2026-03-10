package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.DotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.CircleCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.CollidableSprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.input.TouchController;

public abstract class PlayerBubble extends CollidableSprite {
    protected final BubbleSystem mBubbleSystem;
    protected final DotSystem mDotSystem;
    public boolean mEnable = false;
    public final float mMaxX;
    public final float mMaxY;
    protected final PlayerBubbleBg mPlayerBubbleBg;
    public float mRotationSpeed;
    public boolean mShoot = false;
    public final float mSpeed;
    public float mSpeedX;
    public float mSpeedY;
    protected final float mStartX;
    protected final float mStartY;
    public boolean mSwitch = false;

    
    public abstract DotSystem getDotSystem();

    
    public abstract void onBubbleHit(Bubble bubble);

    
    public abstract void onBubbleReset();

    
    public abstract void onBubbleShoot();

    
    public abstract void onBubbleSwitch();

    protected PlayerBubble(BubbleSystem bubbleSystem, Game game, int drawableId) {
        super(game, drawableId);
        setCollisionShape(new CircleCollisionShape(this.mWidth, this.mHeight));
        this.mBubbleSystem = bubbleSystem;
        this.mDotSystem = getDotSystem();
        this.mPlayerBubbleBg = new PlayerBubbleBg(game, R.drawable.bubble_bg);
        this.mStartX = ((float) game.getScreenWidth()) / 2.0f;
        this.mStartY = (((float) (game.getScreenHeight() * 4)) / 5.0f) - (this.mPixelFactor * 300.0f);
        this.mMaxX = (float) (game.getScreenWidth() - this.mWidth);
        this.mMaxY = (float) game.getScreenHeight();
        this.mSpeed = (this.mPixelFactor * 8000.0f) / 1000.0f;
        this.mLayer = 2;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
    }

    public boolean getEnable() {
        return this.mEnable && !this.mBubbleSystem.isShifting();
    }

    public void onStart() {
        this.mX = this.mStartX - (((float) this.mWidth) / 2.0f);
        this.mY = this.mStartY - (((float) this.mHeight) / 2.0f);
        this.mDotSystem.addToGame();
        this.mPlayerBubbleBg.addToGame();
    }

    public void onRemove() {
        this.mDotSystem.removeFromGame();
        this.mPlayerBubbleBg.removeFromGame();
    }

    public void onUpdate(long elapsedMillis) {
        checkShooting(this.mGame.getTouchController());
        checkSwitching();
        updatePosition(elapsedMillis);
        this.mRotation += this.mRotationSpeed * ((float) elapsedMillis);
        this.mPlayerBubbleBg.setPosition(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
    }

    public void checkShooting(TouchController touchController) {
        if (this.mShoot) {
            double angle = Math.atan2((double) (touchController.mYUp - this.mStartY), (double) (touchController.mXUp - this.mStartX));
            this.mSpeedX = (float) (((double) this.mSpeed) * Math.cos(angle));
            this.mSpeedY = (float) (((double) this.mSpeed) * Math.sin(angle));
            this.mRotationSpeed = 0.36f;
            setEnable(false);
            onBubbleShoot();
            this.mShoot = false;
        }
    }

    public void checkSwitching() {
        if (this.mSwitch) {
            onBubbleSwitch();
            this.mSwitch = false;
        }
    }

    public void updatePosition(long elapsedMillis) {
        this.mX += this.mSpeedX * ((float) elapsedMillis);
        if (this.mX <= 0.0f) {
            bounceRight();
        }
        if (this.mX >= this.mMaxX) {
            bounceLeft();
        }
        this.mY += this.mSpeedY * ((float) elapsedMillis);
        if (this.mY <= ((float) (-this.mHeight))) {
            reset();
        }
        if (this.mY >= this.mMaxY) {
            reset();
        }
    }

    
    public void bounceRight() {
        this.mX = 0.0f;
        this.mSpeedX = -this.mSpeedX;
    }

    
    public void bounceLeft() {
        this.mX = this.mMaxX;
        this.mSpeedX = -this.mSpeedX;
    }

    public void reset() {
        this.mSpeedX = 0.0f;
        this.mSpeedY = 0.0f;
        this.mRotation = 0.0f;
        this.mRotationSpeed = 0.0f;
        setEnable(true);
        onBubbleReset();
    }

    public void showHint() {
    }

    public void removeHint() {
    }

    public void onCollision(Collidable otherObject) {
        if (otherObject instanceof Bubble) {
            onBubbleHit((Bubble) otherObject);
        }
    }

    public void onGameEvent(GameEvent gameEvent) {
        switch ((MyGameEvent) gameEvent) {
            case SHOOT_BUBBLE:
                if (getEnable()) {
                    this.mShoot = true;
                    return;
                }
                return;
            case SWITCH_BUBBLE:
                if (getEnable()) {
                    this.mSwitch = true;
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected static class PlayerBubbleBg extends Sprite {
        public PlayerBubbleBg(Game game, int drawableResId) {
            super(game, drawableResId);
        }

        public void setPosition(float x, float y) {
            this.mX = x - (((float) this.mWidth) / 2.0f);
            this.mY = y - (((float) this.mHeight) / 2.0f);
        }

        public void onStart() {
            this.mLayer = 1;
        }

        public void onUpdate(long elapsedMillis) {
        }
    }
}
