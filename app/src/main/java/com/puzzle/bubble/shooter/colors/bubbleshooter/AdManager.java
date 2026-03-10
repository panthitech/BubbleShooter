package com.puzzle.bubble.shooter.colors.bubbleshooter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {
    public final Activity mActivity;
    public AdRewardListener mListener;
    public MyActivityListener myActivityListener;
    public boolean mRewardEarned = false;
    public RewardedAd mRewardedAd;
    public InterstitialAd interstitialAd;
    private static final String TAG = "Admanager";

    public interface AdRewardListener {
        void onEarnReward();

        void onLossReward();
    }

    public interface MyActivityListener {
        void showInterstitial();
    }

    public AdManager(Activity activity) {
        this.mActivity = activity;
        requestAd();
        requestInterstitialAd();
    }

    public void requestAd() {
        RewardedAd.load((Context) this.mActivity, this.mActivity.getString(R.string.txt_admob_reward), new AdRequest.Builder().build(), (RewardedAdLoadCallback) new RewardedAdLoadCallback() {
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }

            public void onAdLoaded(RewardedAd rewardedAd) {
                RewardedAd unused = AdManager.this.mRewardedAd = rewardedAd;
            }
        });
    }

    public void requestInterstitialAd() {
        AdRequest adIRequest = new AdRequest.Builder().build();
        InterstitialAd.load(mActivity, this.mActivity.getString(R.string.txt_admob_interstitial), adIRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        AdManager.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                AdManager.this.requestInterstitialAd();
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
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        interstitialAd = null;
                    }
                });
    }

    public boolean showInterstitialAd() {
        if (this.interstitialAd == null) {
            return false;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (interstitialAd != null) {
                    interstitialAd.show(mActivity);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });
        return true;
//        interstitialAd.show(mActivity);
    }


    public boolean showRewardAd() {
        if (this.mRewardedAd == null) {
            return false;
        }
        this.mRewardEarned = false;
        this.mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            public void onAdShowedFullScreenContent() {
            }

            public void onAdFailedToShowFullScreenContent(AdError adError) {
            }

            public void onAdDismissedFullScreenContent() {
                RewardedAd unused = AdManager.this.mRewardedAd = null;
                if (!AdManager.this.mRewardEarned) {
                    AdManager.this.mListener.onLossReward();
                }
                AdManager.this.requestAd();
            }
        });
        this.mRewardedAd.show(this.mActivity, new OnUserEarnedRewardListener() {
            public void onUserEarnedReward(RewardItem rewardItem) {
                AdManager.this.mListener.onEarnReward();
                boolean unused = AdManager.this.mRewardEarned = true;
            }
        });
        return true;
    }

    public void setListener(AdRewardListener listener) {
        this.mListener = listener;
    }

    public void setInterstitialAdListener(MyActivityListener listener) {
        this.myActivityListener = listener;
    }
}
