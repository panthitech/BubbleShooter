package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster;

import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.Bubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.DotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.dot.FireDotSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class FireBubble extends BoosterBubble {
    public final ParticleSystem mSparkleParticleSystem;
    public final ParticleSystem mTrailParticleSystem;

    public FireBubble(BubbleSystem bubbleSystem, Game game) {
        super(bubbleSystem, game, R.drawable.fire_bubble);
        this.mTrailParticleSystem = new ParticleSystem(game, R.drawable.fire_flame, 40).setDurationPerParticle(400).setEmissionRate(50).setSpeedX(-800.0f, 800.0f).setSpeedY(-800.0f, 800.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f).setScale(1.0f, 2.0f).setLayer(this.mLayer - 1);
        this.mSparkleParticleSystem = new ParticleSystem(game, R.drawable.fire_particle, 10).setDurationPerParticle(400).setSpeedX(-1000.0f, 1000.0f).setSpeedY(-1000.0f, 1000.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f).setScale(1.0f, 0.5f).setLayer(this.mLayer - 1);
        this.mPlayerBubbleBg.setSpriteBitmap(R.drawable.fire_bubble_bg);
    }

    
    public DotSystem getDotSystem() {
        return new FireDotSystem(this, this.mGame);
    }

    public void onStart() {
        super.onStart();
        this.mTrailParticleSystem.addToGame();
        this.mSparkleParticleSystem.addToGame();
        this.mTrailParticleSystem.emit();
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

    
    public void bounceRight() {
        if (this.mX <= ((float) (-this.mWidth))) {
            reset();
        }
    }

    
    public void bounceLeft() {
        if (this.mX >= ((float) this.mGame.getScreenWidth())) {
            reset();
        }
    }

    
    public void onBubbleShoot() {
        super.onBubbleShoot();
        this.mGame.getSoundManager().playSound(MySoundEvent.FIRE_BUBBLE_SHOOT);
    }

    
    public void onBubbleHit(Bubble bubble) {
        if (this.mY >= 0.0f) {
            bubble.popBubble();
        }
    }

    
    public void onBubbleReset() {
        super.onBubbleReset();
        this.mTrailParticleSystem.stopEmit();
        this.mSparkleParticleSystem.stopEmit();
    }
}
