package com.puzzle.bubble.shooter.colors.bubbleshooter.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.puzzle.bubble.shooter.colors.bubbleshooter.AdManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bonus.BonusSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.effect.WinTextEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.BasicBubble;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.BubbleQueue;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster.BoosterManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.particles.ParticleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevel;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.AdExtraMoveDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.ErrorDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.LossDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.StartDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.TutorialDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.WinDialog;

public class GameController extends GameObject implements AdManager.AdRewardListener, AdManager.MyActivityListener {
    public static final String PREFS_NAME = "prefs_tutorial";
    
    public BasicBubble mBasicBubble;
    public BonusSystem mBonusSystem;
    
    public final BoosterManager mBoosterManager;
    public final BubbleSystem mBubbleSystem;
    public boolean mExtraLives = true;
    public final ParticleSystem mLeftConfetti;
    
    public final GameActivity mParent;
    public final ParticleSystem mRightConfetti;
    public GameControllerState mState;
    public long mTotalMillis;
    public final WinTextEffect mWinText;
    private InterstitialAd interstitial;
    Context context;

    public GameController(Game game) {
        super(game);
        this.mParent = game.getGameActivity();
        this.mBubbleSystem = new BubbleSystem(game);
        this.mBonusSystem = new BonusSystem(game);
        this.mBasicBubble = new BasicBubble(this.mBubbleSystem, game);
        this.mBoosterManager = new BoosterManager(this.mBubbleSystem, game);
        this.mWinText = new WinTextEffect(game);
        int[] confettiId = {R.drawable.confetti_blue, R.drawable.confetti_green, R.drawable.confetti_pink, R.drawable.confetti_yellow};
        this.mLeftConfetti = new ParticleSystem(game, confettiId, 50).setDurationPerParticle(1500).setEmissionRate(30).setEmissionPositionX(0.0f).setEmissionRangeY(((float) game.getScreenHeight()) / 3.0f, ((float) (game.getScreenHeight() * 3)) / 4.0f).setSpeedX(1000.0f, 1500.0f).setSpeedY(-4000.0f, -3000.0f).setAccelerationX(-2.0f, 0.0f).setAccelerationY(5.0f, 10.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 500).setScale(0.75f, 0.0f, 1000).setLayer(3);
        this.mRightConfetti = new ParticleSystem(game, confettiId, 50).setDurationPerParticle(1500).setEmissionRate(30).setEmissionPositionX((float) game.getScreenWidth()).setEmissionRangeY(((float) game.getScreenHeight()) / 3.0f, ((float) (game.getScreenHeight() * 3)) / 4.0f).setSpeedX(-1500.0f, -1000.0f).setSpeedY(-4000.0f, -3000.0f).setAccelerationX(0.0f, 2.0f).setAccelerationY(5.0f, 10.0f).setInitialRotation(0, 360).setRotationSpeed(-720.0f, 720.0f).setAlpha(255.0f, 0.0f, 500).setScale(0.75f, 0.0f, 1000).setLayer(3);
    }

    public void onStart() {
        this.mState = GameControllerState.SHIFT_BUBBLE;
        this.mTotalMillis = 0;
        this.mLeftConfetti.addToGame();
        this.mRightConfetti.addToGame();
    }

    public void onUpdate(long elapsedMillis) {
        switch (this.mState) {
            case SHIFT_BUBBLE:
                this.mTotalMillis += elapsedMillis;
                if (this.mTotalMillis >= 1000) {
                    this.mBubbleSystem.shiftBubble();
                    this.mState = GameControllerState.START_INTRO;
                    this.mTotalMillis = 0;
                    return;
                }
                return;
            case START_INTRO:
                if (!this.mBubbleSystem.isShifting()) {
                    this.mBasicBubble.addToGame();
                    showStartDialog();
                    this.mState = GameControllerState.WAITING;
                    return;
                }
                return;
            case PLAYER_WIN:
                if (!this.mBubbleSystem.isShifting()) {
                    this.mTotalMillis += elapsedMillis;
                    if (this.mTotalMillis >= 1000) {
                        this.mBasicBubble.removeFromGame();
                        this.mBubbleSystem.clearBubble();
                        hideBooster();
                        this.mWinText.activate();
                        createConfetti(2000);
                        this.mGame.getSoundManager().playSound(MySoundEvent.PLAYER_WIN);
                        this.mState = GameControllerState.BONUS_TIME;
                        this.mTotalMillis = 0;
                        return;
                    }
                    return;
                }
                return;
            case PLAYER_LOSS:
                if (!this.mBubbleSystem.isShifting()) {
                    this.mTotalMillis += elapsedMillis;
                    if (this.mTotalMillis < 500) {
                        return;
                    }
                    if (this.mExtraLives) {
                        showExtraMoveDialog();
                        this.mExtraLives = false;
                        this.mState = GameControllerState.WAITING;
                        this.mTotalMillis = 0;
                        return;
                    }
                    showLossDialog();
                    this.mState = GameControllerState.WAITING;
                    this.mTotalMillis = 0;
                    return;
                }
                return;
            case BONUS_TIME:
                this.mTotalMillis += elapsedMillis;
                if (this.mTotalMillis >= 1200) {
                    BubbleQueue bubbleQueue = this.mBasicBubble.getBubbleQueue();
                    while (bubbleQueue.hasBubble()) {
                        this.mBonusSystem.addBonusBubble(bubbleQueue.popBubble());
                    }
                    this.mBonusSystem.addToGame();
                    this.mState = GameControllerState.WAITING;
                    this.mTotalMillis = 0;
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onGameEvent(GameEvent gameEvent) {
        switch ((MyGameEvent) gameEvent) {
            case BOOSTER_CONSUMED:
                this.mBoosterManager.consumeBooster();
                return;
            case EMIT_CONFETTI:
                createConfetti(800);
                return;
            case GAME_WIN:
                this.mBasicBubble.setEnable(false);
                this.mBoosterManager.setEnable(false);
                this.mState = GameControllerState.PLAYER_WIN;
                return;
            case GAME_OVER:
                this.mBasicBubble.setEnable(false);
                this.mBoosterManager.setEnable(false);
                this.mState = GameControllerState.PLAYER_LOSS;
                return;
            case SHOW_WIN_DIALOG:
                hideState();
                showWinDialog();
                return;
            case ADD_EXTRA_MOVE:
                this.mBasicBubble.setEnable(true);
                this.mBoosterManager.setEnable(true);
                return;
            default:
                return;
        }
    }

    public void createConfetti(long duration) {
        this.mLeftConfetti.setDuration(duration).emit();
        this.mRightConfetti.setDuration(duration).emit();
    }

    public void hideBooster() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                GameController.this.mParent.findViewById(R.id.layout_booster).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void hideState() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                GameController.this.mParent.findViewById(R.id.layout_state).setVisibility(View.INVISIBLE);
                GameController.this.mParent.findViewById(R.id.txt_move).setVisibility(View.INVISIBLE);
            }
        });
    }

    public void showStartDialog() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                GameController.this.mParent.showDialog(new StartDialog(GameController.this.mParent, (MyLevel) GameController.this.mGame.getLevel()) {
                    public void startGame() {
                        GameController.this.mBasicBubble.setEnable(true);
                        GameController.this.mBoosterManager.setEnable(true);
                        if (((MyLevel) GameController.this.mGame.getLevel()).mLevelTutorial != null) {
                            GameController.this.showTutorialDialog();
                        }
                    }
                });
                GameController.this.mParent.findViewById(R.id.txt_move).setVisibility(View.VISIBLE);
            }
        });
    }

    public void showTutorialDialog() {
        SharedPreferences prefs = this.mParent.getSharedPreferences(PREFS_NAME, 0);
        String prefsKey = "level" + this.mGame.getLevel().mLevel;
        if (prefs.getBoolean(prefsKey, true)) {
            prefs.edit().putBoolean(prefsKey, false).apply();
            this.mParent.runOnUiThread(new Runnable() {
                public void run() {
                    GameController.this.mParent.showDialog(new TutorialDialog(GameController.this.mParent, (MyLevel) GameController.this.mGame.getLevel()) {
                        public void updateBooster() {
                            GameController.this.mBoosterManager.initBoosterText();
                        }
                    });
                }
            });
        }
    }

    public void showWinDialog() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                showInterstitial();
                GameController.this.mParent.showDialog(new WinDialog(GameController.this.mParent, (MyLevel) GameController.this.mGame.getLevel()) {
                    public void stopGame() {
                        GameController.this.mGame.stop();
                    }
                });
            }
        });
    }

    public void showLossDialog() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                GameController.this.mParent.showDialog(new LossDialog(GameController.this.mParent) {
                    public void stopGame() {
                        GameController.this.mGame.stop();
                    }
                });
            }
        });
    }

    public void showExtraMoveDialog() {
        this.mParent.runOnUiThread(new Runnable() {
            public void run() {
                GameController.this.mParent.showDialog(new AdExtraMoveDialog(GameController.this.mParent) {
                    public void showAd() {
                        GameController.this.showRewardedAd();
                    }

                    public void quit() {
                        GameController.this.gameEvent(MyGameEvent.GAME_OVER);
                    }
                });
            }
        });
    }



    
    public void showRewardedAd() {
        final AdManager adManager = ((MainActivity) this.mParent).getAdManager();
        adManager.setListener(this);
        if (adManager.showRewardAd()) {
            this.mGame.getGameEngine().pauseGame();
            return;
        }
        this.mParent.showDialog(new ErrorDialog(this.mParent) {
            public void retry() {
                adManager.requestAd();
                GameController.this.showRewardedAd();
            }

            public void quit() {
                GameController.this.gameEvent(MyGameEvent.GAME_OVER);
            }
        });
    }

    public void onEarnReward() {
        this.mGame.getGameEngine().resumeGame();
        gameEvent(MyGameEvent.ADD_EXTRA_MOVE);
    }

    public void onLossReward() {
        this.mGame.getGameEngine().resumeGame();
        gameEvent(MyGameEvent.GAME_OVER);
    }

    @Override
    public void showInterstitial() {
        final AdManager adManager = ((MainActivity) this.mParent).getAdManager();
        adManager.setInterstitialAdListener(this);
        if (adManager.showInterstitialAd()) {
            this.mGame.getGameEngine().pauseGame();
            return;
        }
    }
}
