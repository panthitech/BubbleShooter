package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;

import com.puzzle.bubble.shooter.colors.bubbleshooter.AdManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class AdCoinDialog extends GameDialog implements View.OnClickListener, AdManager.AdRewardListener {
    public static final int REWARD_COIN = 50;
    public int mSelectedId;

    public AdCoinDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_ad_coin);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        init();
    }

    public void init() {
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        ImageButton btnWatchAd = (ImageButton) findViewById(R.id.btn_watch_ad);
        btnCancel.setOnClickListener(this);
        btnWatchAd.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createButtonEffect(btnWatchAd);
        UIEffect.createPopUpEffect(btnWatchAd);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        } else if (id == R.id.btn_watch_ad) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            dismiss();
        }
    }

    public void showAd() {
        AdManager ad = ((MainActivity) this.mParent).getAdManager();
        ad.setListener(this);
        if (!ad.showRewardAd()) {
            this.mParent.showDialog(new ErrorDialog(this.mParent) {
                public void retry() {
                    ((MainActivity) this.mParent).getAdManager().requestAd();
                    AdCoinDialog.this.showAd();
                }
            });
        }
    }

    public void onEarnReward() {
        DatabaseHelper databaseHelper = ((MainActivity) this.mParent).getDatabaseHelper();
        databaseHelper.updateItemNum(Item.COIN, databaseHelper.getItemNum(Item.COIN) + 50);
        updateCoin();
    }

    public void onLossReward() {
    }

    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    public void onHide() {
        if (this.mSelectedId == R.id.btn_watch_ad) {
            showAd();
        }
    }

    public void updateCoin() {
    }
}
