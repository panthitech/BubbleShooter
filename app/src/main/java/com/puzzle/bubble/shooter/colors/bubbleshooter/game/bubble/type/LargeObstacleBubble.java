package com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.type;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleColor;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.CircleCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.RectCollisionShape;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite.CollidableSprite;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class LargeObstacleBubble extends CompositeBubble {
    public static final int EXPLOSION_PARTICLES = 10;
    public int mCollisionNum = 0;
    public final ParticleSystem mExplosionParticleSystem;
    public boolean mIsObstacle = true;

    public LargeObstacleBubble(Game game) {
        super(game, BubbleColor.LARGE_OBSTACLE);
        setCollisionShape(new RectCollisionShape(this.mWidth, this.mHeight));
        this.mExplosionParticleSystem = new ParticleSystem(game, R.drawable.wood_particle_02, 10).setDurationPerParticle(600).setSpeedX(-2500.0f, 2500.0f).setSpeedY(-2500.0f, 200.0f).setAccelerationY(5.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 300).setScale(1.0f, 0.0f, 300).setLayer(3);
        this.mLayer--;
    }

    public void onStart() {
        super.onStart();
        this.mX -= ((float) this.mWidth) / 3.0f;
        this.mScale = 1.05f;
        addDummyBubble((DummyBubble) this.mEdges[2]);
        addDummyBubble((DummyBubble) this.mEdges[3]);
    }

    public void popBubble() {
        if (this.mIsObstacle) {
            this.mCollisionNum++;
            if (this.mCollisionNum >= 2) {
                popLargeObstacle();
            } else {
                setSpriteBitmap(R.drawable.bubble_large_wood_02);
            }
            this.mGame.getSoundManager().playSound(MySoundEvent.WOOD_EXPLODE);
            return;
        }
        super.popBubble();
    }

    public void popFloater() {
        if (this.mIsObstacle) {
            popLargeObstacle();
        } else {
            super.popFloater();
        }
    }

    public void popLargeObstacle() {
        this.mExplosionParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 10);
        setBubbleColor(BubbleColor.BLANK);
        setCollisionShape(new CircleCollisionShape(this.mWidth, this.mHeight));
        clearDummyBubble();
        this.mX += (float) this.mWidth;
        this.mLayer++;
        this.mIsObstacle = false;
    }

    public Bubble getCollidedBubble(CollidableSprite collidableSprite) {
        if (!this.mIsObstacle) {
            return super.getCollidedBubble(collidableSprite);
        }
        if (collidableSprite.mY > this.mY + (((float) this.mHeight) / 2.0f)) {
            if (collidableSprite.mX > this.mX + (((float) (this.mWidth * 3)) / 4.0f)) {
                return this.mEdges[5].mEdges[3];
            }
            if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 2.0f)) {
                return this.mEdges[5];
            }
            if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 4.0f)) {
                return this.mEdges[4];
            }
            return this.mEdges[4].mEdges[2];
        } else if (collidableSprite.mX > this.mX + (((float) this.mWidth) / 2.0f)) {
            return this.mEdges[3].mEdges[3];
        } else {
            return this.mEdges[2].mEdges[2];
        }
    }
}
