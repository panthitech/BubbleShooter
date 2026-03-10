package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.DotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.Sprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class BasicBubble extends PlayerBubble {
    public BubbleColor mBubbleColor;
    public final BubbleQueue mBubbleQueue;
    public final CircleBg mCircleBg;
    public boolean mDetectedCollision;
    public final boolean mHintEnable;
    public final NextBubble mNextBubble;
    public final ParticleSystem mTrailParticleSystem;

    public BasicBubble(BubbleSystem bubbleSystem, Game game) {
        super(bubbleSystem, game, BubbleColor.BLANK.getDrawableId());
        this.mBubbleQueue = new BubbleQueue(((MyLevel) game.getLevel()).mPlayer.toCharArray());
        this.mNextBubble = new NextBubble(game);
        this.mCircleBg = new CircleBg(game);
        this.mTrailParticleSystem = new ParticleSystem(game, R.drawable.sparkle, 50).setDurationPerParticle(300).setEmissionRate(50).setAccelerationX(-2.0f, 2.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f).setScale(2.0f, 4.0f).setLayer(this.mLayer - 1);
        this.mHintEnable = game.getGameActivity().getSharedPreferences("prefs_setting", 0).getBoolean("hint", true);
    }

    public void setBubbleColor(BubbleColor bubbleColor) {
        this.mBubbleColor = bubbleColor;
        setSpriteBitmap(bubbleColor.getDrawableId());
    }

    public BubbleQueue getBubbleQueue() {
        return this.mBubbleQueue;
    }

    
    public DotSystem getDotSystem() {
        return new DotSystem(this, this.mGame);
    }

    public void onStart() {
        super.onStart();
        this.mCircleBg.addToGame();
        this.mNextBubble.addToGame();
        this.mTrailParticleSystem.addToGame();
        updateBubbleColor();
    }

    public void onRemove() {
        super.onRemove();
        this.mNextBubble.removeFromGame();
        this.mTrailParticleSystem.removeFromGame();
    }

    public void onUpdate(long elapsedMillis) {
        super.onUpdate(elapsedMillis);
        this.mTrailParticleSystem.setEmissionPosition(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
    }

    
    public void onBubbleShoot() {
        this.mBubbleQueue.popBubble();
        this.mTrailParticleSystem.emit();
        gameEvent(MyGameEvent.BUBBLE_SHOT);
        this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_SHOOT);
        this.mDetectedCollision = false;
    }

    
    public void onBubbleSwitch() {
        this.mBubbleQueue.switchBubble();
        updateBubbleColor();
        this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_SWITCH);
    }

    
    public void onBubbleHit(Bubble bubble) {
        if (this.mY >= bubble.mY && !this.mDetectedCollision) {
            gameEvent(MyGameEvent.BUBBLE_HIT);
            this.mBubbleSystem.addCollidedBubble(this, bubble);
            this.mDetectedCollision = true;
            this.mGame.getSoundManager().playSound(MySoundEvent.BUBBLE_HIT);
            reset();
        }
    }

    
    public void onBubbleReset() {
        this.mX = this.mStartX - (((float) this.mWidth) / 2.0f);
        this.mY = this.mStartY - (((float) this.mHeight) / 2.0f);
        this.mTrailParticleSystem.stopEmit();
        updateBubbleColor();
        gameEvent(MyGameEvent.BUBBLE_CONSUMED);
    }

    public void updateBubbleColor() {
        BubbleColor bubbleColor = this.mBubbleQueue.getBubble();
        if (bubbleColor != null) {
            setBubbleColor(bubbleColor);
            this.mDotSystem.setDotBitmap(bubbleColor.getDotDrawableId());
        } else {
            hide();
        }
        BubbleColor nextBubbleColor = this.mBubbleQueue.getNextBubble();
        if (nextBubbleColor != null) {
            this.mNextBubble.setBubbleColor(nextBubbleColor);
        } else {
            this.mNextBubble.hide();
        }
    }

    public void onGameEvent(GameEvent gameEvent) {
        super.onGameEvent(gameEvent);
        switch ((MyGameEvent) gameEvent) {
            case BOOSTER_ADDED:
                BubbleColor currentBubbleColor = this.mBubbleQueue.getBubble();
                if (currentBubbleColor != null) {
                    this.mNextBubble.setBubbleColor(currentBubbleColor);
                }
                hide();
                setEnable(false);
                return;
            case BOOSTER_REMOVED:
            case BOOSTER_CONSUMED:
                BubbleColor nextBubbleColor = this.mBubbleQueue.getNextBubble();
                if (nextBubbleColor != null) {
                    this.mNextBubble.setBubbleColor(nextBubbleColor);
                }
                show();
                setEnable(true);
                return;
            case ADD_EXTRA_MOVE:
                addExtraBubble();
                return;
            default:
                return;
        }
    }

    public void show() {
        this.mIsVisible = true;
        this.mPlayerBubbleBg.mIsVisible = true;
    }

    public void hide() {
        this.mIsVisible = false;
        this.mPlayerBubbleBg.mIsVisible = false;
    }

    public void showHint() {
        if (this.mHintEnable) {
            this.mBubbleSystem.addBubbleHint(this.mBubbleColor);
        }
    }

    public void removeHint() {
        if (this.mHintEnable) {
            this.mBubbleSystem.removeBubbleHint(this.mBubbleColor);
        }
    }

    public void addExtraBubble() {
        this.mBubbleQueue.initExtraBubble();
        updateBubbleColor();
        show();
        this.mNextBubble.show();
    }

    public class NextBubble extends Sprite {
        public NextBubble(Game game) {
            super(game, BubbleColor.BLANK.getDrawableId());
            this.mLayer = 2;
        }

        public void setBubbleColor(BubbleColor bubbleColor) {
            setSpriteBitmap(bubbleColor.getDrawableId());
        }

        public void onStart() {
            float distance = this.mPixelFactor * 300.0f;
            this.mX = (BasicBubble.this.mStartX + distance) - (((float) this.mWidth) / 2.0f);
            this.mY = (BasicBubble.this.mStartY + distance) - (((float) this.mHeight) / 2.0f);
            this.mScale = 0.8f;
        }

        public void onUpdate(long elapsedMillis) {
        }

        
        public void show() {
            this.mIsVisible = true;
        }

        
        public void hide() {
            this.mIsVisible = false;
        }
    }
}
