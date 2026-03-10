package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameFragment;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.timer.LivesTimer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.timer.WheelTimer;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.TransitionEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.AdLivesDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.LevelDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.SettingDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.ShopDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog.WheelDialog;
import java.util.ArrayList;

public class MapFragment extends GameFragment implements View.OnClickListener, TransitionEffect.OnTransitionListener {
    public static final int TOTAL_LEVEL = 109;
    
    public int mCurrentLevel;
    public DatabaseHelper mDatabaseHelper;
    public ArrayList<Integer> mLevelStar;
    
    public LivesTimer mLivesTimer;
    public TransitionEffect mTransitionEffect;
    
    public WheelTimer mWheelTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mDatabaseHelper = ((MainActivity) getGameActivity()).getDatabaseHelper();
        this.mLivesTimer = ((MainActivity) getGameActivity()).getLivesTimer();
        this.mWheelTimer = new WheelTimer(getGameActivity());
        this.mTransitionEffect = new TransitionEffect(getGameActivity());
        this.mTransitionEffect.setListener(this);
        this.mLevelStar = this.mDatabaseHelper.getAllLevelStar();
        this.mCurrentLevel = this.mLevelStar.size() + 1;
        init();
    }

    
    public void onLayoutCreated() {
        if (this.mCurrentLevel <= 109) {
            ScrollView scrollView = (ScrollView) getView().findViewById(R.id.layout_map);
            scrollView.scrollTo(0, ((TextView) findViewByName("btn_level_" + this.mCurrentLevel)).getBottom() - (scrollView.getHeight() / 2));
        }
    }

    public void init() {
        ImageButton btnSetting = (ImageButton) getView().findViewById(R.id.btn_setting);
        ImageButton btnShop = (ImageButton) getView().findViewById(R.id.btn_shop);
        ImageButton btnCoin = (ImageButton) getView().findViewById(R.id.btn_coin);
        ImageButton btnWheel = (ImageButton) getView().findViewById(R.id.btn_wheel);
        btnSetting.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnCoin.setOnClickListener(this);
        btnWheel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnSetting);
        UIEffect.createButtonEffect(btnShop);
        UIEffect.createButtonEffect(btnCoin);
        UIEffect.createButtonEffect(btnWheel);
        TextView txtLives = (TextView) getView().findViewById(R.id.txt_lives);
        txtLives.setOnClickListener(this);
        UIEffect.createButtonEffect(txtLives);
        initLevelButton();
        initLevelStar();
        initAd();
        loadCoin();
        getView().postDelayed(new Runnable() {
            public void run() {
                if (MapFragment.this.mWheelTimer.isWheelReady()) {
                    MapFragment.this.showWheelDialogAndShowLevel();
                } else {
                    MapFragment.this.showLevelDialog(MapFragment.this.mCurrentLevel);
                }
            }
        }, 1200);
    }

    public View findViewByName(String name) {
        return getView().findViewById(getResources().getIdentifier(name, "id", getGameActivity().getPackageName()));
    }

    public void initLevelButton() {
        for (int i = 1; i <= 109; i++) {
            TextView txtLevel = (TextView) findViewByName("btn_level_" + i);
            if (i <= this.mCurrentLevel) {
                final int level = i;
                txtLevel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        MapFragment.this.showLevelDialog(level);
                        MapFragment.this.getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
                    }
                });
                txtLevel.setBackgroundResource(R.drawable.btn_level);
                txtLevel.setTextColor(getResources().getColor(R.color.brown));
                UIEffect.createButtonEffect(txtLevel);
            } else {
                txtLevel.setBackgroundResource(R.drawable.btn_level_lock);
                txtLevel.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }

    public void initLevelStar() {
        for (int i = 1; i <= 109; i++) {
            ImageView imageStar = (ImageView) findViewByName("image_level_star_" + i);
            if (i < this.mCurrentLevel) {
                switch (this.mLevelStar.get(i - 1).intValue()) {
                    case 1:
                        imageStar.setImageResource(R.drawable.star_set_01);
                        break;
                    case 2:
                        imageStar.setImageResource(R.drawable.star_set_02);
                        break;
                    case 3:
                        imageStar.setImageResource(R.drawable.star_set_03);
                        break;
                }
                imageStar.setVisibility(View.VISIBLE);
            } else {
                imageStar.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void initAd() {
        ((AdView) getView().findViewById(R.id.adView)).loadAd(new AdRequest.Builder().build());
    }

    
    public void loadCoin() {
        ((TextView) getView().findViewById(R.id.txt_coin)).setText(String.valueOf(this.mDatabaseHelper.getItemNum(Item.COIN)));
    }

    
    public void showLevelDialog(final int level) {
        if (level <= 109) {
            showDialog(new LevelDialog(getGameActivity(), level) {
                public void navigateToGame() {
                    if (MapFragment.this.mLivesTimer.isEnoughLives()) {
                        super.navigateToGame();
                    } else {
                        MapFragment.this.showLiveNotEnoughDialog();
                    }
                }

                public void startGame() {
                    MapFragment.this.getGameActivity().navigateToFragment(MyGameFragment.newInstance(level));
                    MapFragment.this.getGameActivity().getSoundManager().unloadMusic();
                }
            });
        }
    }

    
    public void showLiveNotEnoughDialog() {
        showDialog(new AdLivesDialog(getGameActivity()));
    }

    public void showWheelDialog() {
        showDialog(new WheelDialog(getGameActivity(), this.mWheelTimer) {
            public void updateCoin() {
                MapFragment.this.loadCoin();
            }
        });
    }

    
    public void showWheelDialogAndShowLevel() {
        showDialog(new WheelDialog(getGameActivity(), this.mWheelTimer) {
            public void showLevel() {
                MapFragment.this.showLevelDialog(MapFragment.this.mCurrentLevel);
            }

            public void updateCoin() {
                MapFragment.this.loadCoin();
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_setting) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            showDialog(new SettingDialog(getGameActivity()));
        } else if (id == R.id.btn_shop || id == R.id.btn_coin) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            showDialog(new ShopDialog(getGameActivity()) {
                public void updateMapCoin() {
                    MapFragment.this.loadCoin();
                }
            });
        } else if (id == R.id.btn_wheel) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            showWheelDialog();
        } else if (id == R.id.txt_lives) {
            getGameActivity().getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            if (!this.mLivesTimer.isLivesFull()) {
                showLiveNotEnoughDialog();
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.mLivesTimer.start();
    }

    public void onPause() {
        super.onPause();
        this.mLivesTimer.stop();
    }

    public boolean onBackPressed() {
        this.mTransitionEffect.show();
        return true;
    }

    public void onTransition() {
        getGameActivity().navigateToFragment(new MenuFragment());
    }
}
