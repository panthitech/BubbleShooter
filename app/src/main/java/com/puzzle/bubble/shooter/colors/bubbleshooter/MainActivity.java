package com.puzzle.bubble.shooter.colors.bubbleshooter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.GameController;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.level.MyLevelManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.timer.LivesTimer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.fragment.MenuFragment;

public class MainActivity extends GameActivity implements MyActivityListener {
    public AdManager mAdManager;
    public DatabaseHelper mDatabaseHelper;
    public LivesTimer mLivesTimer;
    private InterstitialAd interstitial;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AnimalsPop);
        overridePendingTransition(17432576, 17432577);
        setContentView(R.layout.activity_main);
        setContainerId(R.id.container);
        setLevelManager(new MyLevelManager(this));
        setSoundManager(new MySoundManager(this));
        this.mDatabaseHelper = new DatabaseHelper(this);
        this.mAdManager = new AdManager(this);
        this.mLivesTimer = new LivesTimer(this);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adIRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, this.getString(R.string.txt_admob_interstitial), adIRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        interstitial = interstitialAd;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(interstitial != null)
//                                    interstitial.show(MainActivity.this);
//                            }
//                        });

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                        Log.i("NAMES", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d("NAMES", loadAdError.toString());
                        interstitial = null;
                    }
                });


        if (savedInstanceState == null) {
            navigateToFragment(new MenuFragment());
            getSoundManager().loadMusic(R.raw.happy_and_joyful_children);
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        return this.mDatabaseHelper;
    }

    public AdManager getAdManager() {
        return this.mAdManager;
    }

    public LivesTimer getLivesTimer() {
        return this.mLivesTimer;
    }

    @Override
    public void showInterstitial() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (interstitial != null)
                    interstitial.show(MainActivity.this);
            }
        });

    }
}
