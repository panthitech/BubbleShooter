package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.BombDotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.DotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BombBubble extends BoosterBubble {
    public static final int EXPLOSION_PARTICLES = 30;
    public final ParticleSystem mExplosionEffect;
    public final ParticleSystem mExplosionParticleSystem;
    public final ParticleSystem mSparkleParticleSystem;
    public final ParticleSystem mTrailParticleSystem;

    public BombBubble(BubbleSystem bubbleSystem, Game game) {
        super(bubbleSystem, game, R.drawable.bomb_bubble);
        this.mTrailParticleSystem = new ParticleSystem(game, R.drawable.sparkle, 50).setDurationPerParticle(300).setEmissionRate(50).setAccelerationX(-2.0f, 2.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f).setScale(2.0f, 4.0f).setLayer(this.mLayer - 1);
        this.mSparkleParticleSystem = new ParticleSystem(game, R.drawable.bomb_particle, 10).setDurationPerParticle(400).setSpeedX(-1000.0f, 1000.0f).setSpeedY(-1000.0f, 1000.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f).setScale(1.0f, 0.5f).setLayer(this.mLayer - 1);
        this.mExplosionParticleSystem = new ParticleSystem(game, R.drawable.white_particle, 30).setDurationPerParticle(600).setSpeedAngle(-2500.0f, 2500.0f).setAccelerationY(1.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 200).setScale(1.0f, 0.0f, 200).setLayer(3);
        this.mExplosionEffect = new ParticleSystem(game, R.drawable.circle_light, 1).setDurationPerParticle(400).setAlpha(255.0f, 0.0f).setScale(1.0f, 6.0f, 100).setLayer(3);
        this.mPlayerBubbleBg.setSpriteBitmap(R.drawable.bomb_bubble_bg);
    }

    
    public DotSystem getDotSystem() {
        return new BombDotSystem(this, this.mGame);
    }

    public void onStart() {
        super.onStart();
        this.mTrailParticleSystem.addToGame();
        this.mSparkleParticleSystem.addToGame();
        this.mSparkleParticleSystem.emit();
    }

    public void onRemove() {
        super.onRemove();
        this.mTrailParticleSystem.removeFromGame();
        this.mSparkleParticleSystem.removeFromGame();
    }

    public void onUpdate(long elapsedMillis) {
        super.onUpdate(elapsedMillis);
        this.mTrailParticleSystem.setEmissionPosition(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
        this.mSparkleParticleSystem.setEmissionPosition(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
    }

    
    public void onBubbleShoot() {
        super.onBubbleShoot();
        this.mTrailParticleSystem.emit();
    }

    
    public void onBubbleHit(Bubble bubble) {
        if (this.mY >= bubble.mY && !this.mConsume) {
            bfs(this.mBubbleSystem.getCollidedBubble(this, bubble));
            this.mExplosionParticleSystem.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 30);
            this.mExplosionEffect.oneShot(this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f), 1);
            this.mGame.getSoundManager().playSound(MySoundEvent.BOMB_EXPLODE);
            reset();
        }
    }

    public void bfs(Bubble root) {
        int depth;
        List<Bubble> removedList = new ArrayList<>();
        Queue<Bubble> queue = new LinkedList<>();
        root.mDepth = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            Bubble currentBubble = queue.poll();
            removedList.add(currentBubble);
            for (Bubble b : currentBubble.mEdges) {
                if (b != null && b.mDepth == -1 && (depth = currentBubble.mDepth + 1) <= 2) {
                    b.mDepth = depth;
                    queue.offer(b);
                }
            }
        }
        for (Bubble b2 : removedList) {
            b2.popBubble();
        }
        removedList.clear();
    }

    
    public void onBubbleReset() {
        super.onBubbleReset();
        this.mTrailParticleSystem.stopEmit();
        this.mSparkleParticleSystem.stopEmit();
    }
}
