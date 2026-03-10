package com.puzzle.bubble.shooter.colors.bubbleshooter.game.counter;

import android.graphics.drawable.ClipDrawable;
import android.widget.ImageView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.MyGameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.UIGameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;

public class ProgressBarCounter extends UIGameObject {
    public static final int POINTS_GAINED_PER_BUBBLE = 10;
    public static final int STAR1_THRESHOLD = 2800;
    public static final int STAR2_THRESHOLD = 7200;
    public static final int STAR3_THRESHOLD = 10000;
    
    public int mCurrentStar;
    public final MyLevel mLevel;
    public final float mPointFactor;
    public int mPoints;
    public final ClipDrawable mProgressBar;
    
    public final ImageView mStar1;
    
    public final ImageView mStar2;
    
    public final ImageView mStar3;
    public final Runnable mUpdateStarRunnable = new Runnable() {
        public void run() {
            switch (ProgressBarCounter.this.mCurrentStar) {
                case 1:
                    ProgressBarCounter.this.mStar1.setImageResource(R.drawable.star);
                    return;
                case 2:
                    ProgressBarCounter.this.mStar2.setImageResource(R.drawable.star);
                    return;
                case 3:
                    ProgressBarCounter.this.mStar3.setImageResource(R.drawable.star);
                    return;
                default:
                    return;
            }
        }
    };

    public ProgressBarCounter(Game game) {
        super(game);
        this.mLevel = (MyLevel) game.getLevel();
        this.mProgressBar = (ClipDrawable) ((ImageView) game.getGameActivity().findViewById(R.id.image_progress_bar)).getDrawable();
        this.mStar1 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_01);
        this.mStar2 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_02);
        this.mStar3 = (ImageView) game.getGameActivity().findViewById(R.id.image_star_03);
        this.mPointFactor = 10000.0f / (((float) this.mLevel.mMove) * 40.0f);
    }

    public void onStart() {
        this.mPoints = 0;
        this.mCurrentStar = 0;
        drawUI();
    }

    public void onUpdate(long elapsedMillis) {
        if (this.mPoints >= STAR1_THRESHOLD && this.mCurrentStar < 1) {
            this.mLevel.mStar = 1;
            this.mCurrentStar = 1;
            this.mStar1.post(this.mUpdateStarRunnable);
            this.mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
        } else if (this.mPoints >= STAR2_THRESHOLD && this.mCurrentStar < 2) {
            this.mLevel.mStar = 2;
            this.mCurrentStar = 2;
            this.mStar1.post(this.mUpdateStarRunnable);
            this.mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
        } else if (this.mPoints >= STAR3_THRESHOLD && this.mCurrentStar < 3) {
            this.mLevel.mStar = 3;
            this.mCurrentStar = 3;
            this.mStar1.post(this.mUpdateStarRunnable);
            this.mGame.getSoundManager().playSound(MySoundEvent.ADD_STAR);
            removeFromGame();
        }
    }

    
    public void onDrawUI() {
        this.mProgressBar.setLevel(this.mPoints);
    }

    public void onGameEvent(GameEvent gameEvents) {
        if (gameEvents == MyGameEvent.BUBBLE_POP) {
            this.mPoints = (int) (((float) this.mPoints) + (this.mPointFactor * 10.0f));
            drawUI();
        }
    }
}
