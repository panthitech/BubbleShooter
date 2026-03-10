package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.DummyBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.LargeObstacleBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.LockedBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type.ObstacleBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.FloaterEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.HintEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.ScoreEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.CollisionType;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.CircleCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.CollidableSprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import java.util.Arrays;

public class Bubble extends CollidableSprite {
    public static final int EXPLOSION_PARTICLES = 6;
    public float mAlphaSpeed;
    public BubbleColor mBubbleColor;
    public int mCol;
    public int mDepth = -1;
    public boolean mDiscover = false;
    public final Bubble[] mEdges = new Bubble[6];
    public final ParticleSystem mExplosionParticleSystem;
    public final FloaterEffect mFloaterEffect;
    public final HintEffect mHintEffect;
    public final ParticleSystem mLightParticleSystem;
    public boolean mPop = false;
    public long mPopTotalTime;
    public int mRow;
    public float mScaleSpeed;
    public final ScoreEffect mScoreEffect;
    public boolean mShiftDown = false;
    public float mShiftPosition;
    public long mShiftTotalTime;
    public boolean mShiftUp = false;
    public final float mSpeedY;

    public Bubble(Game game, BubbleColor bubbleColor) {
        super(game, bubbleColor.getDrawableId());
        setCollisionShape(new CircleCollisionShape(this.mWidth, this.mHeight));
        setCollisionType(bubbleColor);
        this.mBubbleColor = bubbleColor;
        this.mSpeedY = (this.mPixelFactor * 3000.0f) / 1000.0f;
        this.mExplosionParticleSystem = new ParticleSystem(game, R.drawable.sparkle, 6).setDurationPerParticle(800).setSpeedX(-600.0f, 600.0f).setSpeedY(-600.0f, 600.0f).setAccelerationY(1.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 400).setScale(1.0f, 0.0f, 400).setLayer(3);
        this.mLightParticleSystem = new ParticleSystem(game, R.drawable.circle_light, 1).setDurationPerParticle(400).setAlpha(255.0f, 0.0f, 200).setScale(0.0f, 2.0f).setLayer(3);
        this.mScoreEffect = new ScoreEffect(game, bubbleColor.getScoreDrawableId());
        this.mFloaterEffect = new FloaterEffect(game, bubbleColor.getDrawableId());
        this.mHintEffect = new HintEffect(game);
        this.mLayer = 2;
    }

    public void setBubbleColor(BubbleColor bubbleColor) {
        this.mBubbleColor = bubbleColor;
        setSpriteBitmap(bubbleColor.getDrawableId());
        setCollisionType(bubbleColor);
        if (bubbleColor != BubbleColor.BLANK) {
            this.mScoreEffect.setSpriteBitmap(bubbleColor.getScoreDrawableId());
            this.mFloaterEffect.setSpriteBitmap(bubbleColor.getDrawableId());
        }
    }

    public void setCollisionType(BubbleColor bubbleColor) {
        if (bubbleColor == BubbleColor.BLANK || bubbleColor == BubbleColor.DUMMY) {
            setCollisionType(CollisionType.NONE);
        } else {
            setCollisionType(CollisionType.PASSIVE);
        }
    }

    public void setPosition(float x, float y, int row, int col) {
        this.mX = x;
        this.mY = y;
        this.mRow = row;
        this.mCol = col;
    }

    public void onStart() {
        this.mFloaterEffect.addToGame();
    }

    public void onRemove() {
        this.mFloaterEffect.removeFromGame();
    }

    public void onUpdate(long elapsedMillis) {
        checkPopBubble(elapsedMillis);
        updatePosition(elapsedMillis);
        updateShape(elapsedMillis);
    }

    public void checkPopBubble(long elapsedTimeMillis) {
        if (this.mPop) {
            this.mPopTotalTime += elapsedTimeMillis;
            if (this.mPopTotalTime > ((long) this.mDepth) * 80) {
                this.mExplosionParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 6);
                this.mLightParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 1);
                this.mScoreEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
                checkLockedBubble();
                this.mScaleSpeed = -0.002f;
                this.mAlphaSpeed = -0.5f;
                this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_POP);
                this.mDepth = -1;
                this.mPop = false;
            }
        }
    }

    public void updatePosition(long elapsedTimeMillis) {
        if (this.mShiftDown) {
            this.mShiftTotalTime += elapsedTimeMillis;
            if (this.mShiftTotalTime > 800) {
                this.mY += this.mSpeedY * ((float) elapsedTimeMillis);
                if (this.mY >= this.mShiftPosition) {
                    this.mShiftPosition = 0.0f;
                    this.mShiftDown = false;
                }
            }
        }
        if (this.mShiftUp) {
            this.mY -= this.mSpeedY * ((float) elapsedTimeMillis);
            if (this.mY <= this.mShiftPosition) {
                this.mShiftPosition = 0.0f;
                this.mShiftUp = false;
            }
        }
    }

    public void updateShape(long elapsedTimeMillis) {
        this.mScale += this.mScaleSpeed * ((float) elapsedTimeMillis);
        this.mAlpha += this.mAlphaSpeed * ((float) elapsedTimeMillis);
        if (this.mScale <= 0.0f) {
            setBubbleColor(BubbleColor.BLANK);
            this.mScaleSpeed = 0.0f;
            this.mAlphaSpeed = 0.0f;
            this.mScale = 1.0f;
            this.mAlpha = 255.0f;
        }
    }

    public void popBubble() {
        if (this.mBubbleColor != BubbleColor.BLANK) {
            this.mBubbleColor = BubbleColor.BLANK;
            gameEvent(MyGameEvent.BUBBLE_POP);
            this.mPopTotalTime = 0;
            this.mPop = true;
        }
    }

    public void popFloater() {
        if (this.mBubbleColor != BubbleColor.BLANK) {
            this.mFloaterEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
            gameEvent(MyGameEvent.BUBBLE_POP);
            setBubbleColor(BubbleColor.BLANK);
        }
    }

    public void checkLockedBubble() {
        for (Bubble b : this.mEdges) {
            if (b instanceof LockedBubble) {
                LockedBubble lockedBubble = (LockedBubble) b;
                if (lockedBubble.mIsLocked) {
                    lockedBubble.popBubble();
                }
            }
        }
    }

    public void checkObstacleBubble() {
        for (Bubble b : this.mEdges) {
            if (b instanceof ObstacleBubble) {
                ObstacleBubble obstacleBubble = (ObstacleBubble) b;
                if (obstacleBubble.mIsObstacle) {
                    obstacleBubble.popBubble();
                    popBubble();
                }
            } else if (b instanceof LargeObstacleBubble) {
                LargeObstacleBubble largeObstacleBubble = (LargeObstacleBubble) b;
                if (largeObstacleBubble.mIsObstacle) {
                    largeObstacleBubble.popBubble();
                    popBubble();
                }
            } else if (b instanceof DummyBubble) {
                DummyBubble dummyBubble = (DummyBubble) b;
                Bubble targetBubble = dummyBubble.mTargetBubble;
                if ((targetBubble instanceof LargeObstacleBubble) && !Arrays.asList(this.mEdges).contains(targetBubble)) {
                    dummyBubble.popBubble();
                    popBubble();
                }
            }
        }
    }

    public Bubble getCollidedBubble(CollidableSprite collidableSprite) {
        if (collidableSprite.mY > this.mY + (((float) this.mHeight) / 2.0f)) {
            if (collidableSprite.mX > this.mX) {
                return this.mEdges[5];
            }
            return this.mEdges[4];
        } else if (collidableSprite.mX > this.mX) {
            return this.mEdges[3];
        } else {
            return this.mEdges[2];
        }
    }

    public void shiftBubble(float shiftDistance) {
        if (shiftDistance > 0.0f) {
            this.mShiftDown = true;
        } else {
            this.mShiftUp = true;
        }
        this.mShiftPosition = this.mY + shiftDistance;
        this.mShiftTotalTime = 0;
    }

    public boolean isShifting() {
        return this.mShiftUp || this.mShiftDown;
    }

    public void addHint() {
        this.mHintEffect.activate(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
    }

    public void removeHint() {
        this.mHintEffect.removeFromGame();
    }
}
