package com.puzzle.bubble.shooter.colors.bubbleshooter.game.player.booster;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game.bubble.BubbleSystem;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class BoosterManager {
    public Activity mActivity;

    public final BombBubble mBombBubble;

    public int mBombBubbleNum;

    public Booster mBooster;
    public ImageButton mBtnBombBubble;
    public ImageButton mBtnColorBubble;
    public ImageButton mBtnFireBubble;

    public final ColorBubble mColorBubble;

    public int mColorBubbleNum;
    public DatabaseHelper mDatabaseHelper;

    public final FireBubble mFireBubble;

    public int mFireBubbleNum;
    public TextView mTxtBombBubble;
    public TextView mTxtColorBubble;
    public TextView mTxtFireBubble;

    public enum Booster {
        COLOR_BUBBLE,
        FIRE_BUBBLE,
        BOMB_BUBBLE
    }

    public BoosterManager(BubbleSystem bubbleSystem, Game game) {
        this.mActivity = game.getGameActivity();
        this.mColorBubble = new ColorBubble(bubbleSystem, game);
        this.mFireBubble = new FireBubble(bubbleSystem, game);
        this.mBombBubble = new BombBubble(bubbleSystem, game);
        init();
    }

    public void init() {
        mDatabaseHelper = ((MainActivity) this.mActivity).getDatabaseHelper();
        mBtnBombBubble = ((ImageButton) this.mActivity.findViewById(R.id.btn_bomb_bubble));
        mBtnColorBubble = ((ImageButton) this.mActivity.findViewById(R.id.btn_color_bubble));
        mBtnFireBubble = ((ImageButton) this.mActivity.findViewById(R.id.btn_fire_bubble));
        mTxtBombBubble = ((TextView) this.mActivity.findViewById(R.id.txt_bomb_bubble));
        mTxtColorBubble = ((TextView) this.mActivity.findViewById(R.id.txt_color_bubble));
        mTxtFireBubble = ((TextView) this.mActivity.findViewById(R.id.txt_fire_bubble));
        this.mBtnColorBubble.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BoosterManager.this.mColorBubbleNum != 0 && BoosterManager.this.mColorBubble.getEnable()) {
                    if (BoosterManager.this.mBooster == null) {
                        BoosterManager.this.mColorBubble.addToGame();
                        BoosterManager.this.mBooster = Booster.COLOR_BUBBLE;
                        BoosterManager.this.lockButton();
                    } else if (BoosterManager.this.mBooster == Booster.COLOR_BUBBLE) {
                        BoosterManager.this.mColorBubble.removeFromGame();
                        BoosterManager.this.unlockButton();
                        BoosterManager.this.mBooster = null;
                    }
                }
            }
        });
        this.mBtnFireBubble.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BoosterManager.this.mFireBubbleNum != 0 && BoosterManager.this.mFireBubble.getEnable()) {
                    if (BoosterManager.this.mBooster == null) {
                        BoosterManager.this.mFireBubble.addToGame();
                        BoosterManager.this.mBooster = Booster.FIRE_BUBBLE;
                        BoosterManager.this.lockButton();
                    } else if (BoosterManager.this.mBooster == Booster.FIRE_BUBBLE) {
                        BoosterManager.this.mFireBubble.removeFromGame();
                        BoosterManager.this.unlockButton();
                        BoosterManager.this.mBooster = null;
                    }
                }
            }
        });
        this.mBtnBombBubble.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BoosterManager.this.mBombBubbleNum != 0 && BoosterManager.this.mBombBubble.getEnable()) {
                    if (BoosterManager.this.mBooster == null) {
                        BoosterManager.this.mBombBubble.addToGame();
                        BoosterManager.this.mBooster = Booster.BOMB_BUBBLE;
                        BoosterManager.this.lockButton();
                    } else if (BoosterManager.this.mBooster == Booster.BOMB_BUBBLE) {
                        BoosterManager.this.mBombBubble.removeFromGame();
                        BoosterManager.this.unlockButton();
                        BoosterManager.this.mBooster = null;
                    }
                }
            }
        });
        UIEffect.createButtonEffect(this.mBtnColorBubble);
        UIEffect.createButtonEffect(this.mBtnFireBubble);
        UIEffect.createButtonEffect(this.mBtnBombBubble);
        UIEffect.clearColorFilter(this.mBtnColorBubble);
        UIEffect.clearColorFilter(this.mBtnFireBubble);
        UIEffect.clearColorFilter(this.mBtnBombBubble);
        UIEffect.clearColorFilter(this.mTxtColorBubble);
        UIEffect.clearColorFilter(this.mTxtFireBubble);
        UIEffect.clearColorFilter(this.mTxtBombBubble);
        initBoosterText();
    }

    public void initBoosterText() {
        this.mColorBubbleNum = this.mDatabaseHelper.getItemNum(Item.COLOR_BALL);
        this.mFireBubbleNum = this.mDatabaseHelper.getItemNum(Item.FIREBALL);
        this.mBombBubbleNum = this.mDatabaseHelper.getItemNum(Item.BOMB);
        this.mTxtColorBubble.setText(String.valueOf(this.mColorBubbleNum));
        this.mTxtFireBubble.setText(String.valueOf(this.mFireBubbleNum));
        this.mTxtBombBubble.setText(String.valueOf(this.mBombBubbleNum));
    }

    public void setEnable(boolean enable) {
        this.mFireBubble.setEnable(enable);
        this.mBombBubble.setEnable(enable);
        this.mColorBubble.setEnable(enable);
    }

    public void consumeBooster() {
        updateBoosterNum();
        this.mActivity.runOnUiThread(new Runnable() {
            public void run() {
                BoosterManager.this.updateBoosterText();
            }
        });
    }

    public void updateBoosterNum() {
        switch (this.mBooster) {
            case COLOR_BUBBLE:
                this.mColorBubbleNum--;
                this.mDatabaseHelper.updateItemNum(Item.COLOR_BALL, this.mColorBubbleNum);
                return;
            case FIRE_BUBBLE:
                this.mFireBubbleNum--;
                this.mDatabaseHelper.updateItemNum(Item.FIREBALL, this.mFireBubbleNum);
                return;
            case BOMB_BUBBLE:
                this.mBombBubbleNum--;
                this.mDatabaseHelper.updateItemNum(Item.BOMB, this.mBombBubbleNum);
                return;
            default:
                return;
        }
    }


    public void updateBoosterText() {
        switch (this.mBooster) {
            case COLOR_BUBBLE:
                this.mTxtColorBubble.setText(String.valueOf(this.mColorBubbleNum));
                unlockButton();
                break;
            case FIRE_BUBBLE:
                this.mTxtFireBubble.setText(String.valueOf(this.mFireBubbleNum));
                unlockButton();
                break;
            case BOMB_BUBBLE:
                this.mTxtBombBubble.setText(String.valueOf(this.mBombBubbleNum));
                unlockButton();
                break;
        }
        this.mBooster = null;
    }


    public void lockButton() {
        switch (this.mBooster) {
            case COLOR_BUBBLE:
                UIEffect.createColorFilter(this.mBtnBombBubble);
                UIEffect.createColorFilter(this.mBtnFireBubble);
                UIEffect.createColorFilter(this.mTxtBombBubble);
                UIEffect.createColorFilter(this.mTxtFireBubble);
                this.mBtnBombBubble.setEnabled(false);
                this.mBtnFireBubble.setEnabled(false);
                return;
            case FIRE_BUBBLE:
                UIEffect.createColorFilter(this.mBtnBombBubble);
                UIEffect.createColorFilter(this.mBtnColorBubble);
                UIEffect.createColorFilter(this.mTxtBombBubble);
                UIEffect.createColorFilter(this.mTxtColorBubble);
                this.mBtnBombBubble.setEnabled(false);
                this.mBtnColorBubble.setEnabled(false);
                return;
            case BOMB_BUBBLE:
                UIEffect.createColorFilter(this.mBtnFireBubble);
                UIEffect.createColorFilter(this.mBtnColorBubble);
                UIEffect.createColorFilter(this.mTxtFireBubble);
                UIEffect.createColorFilter(this.mTxtColorBubble);
                this.mBtnFireBubble.setEnabled(false);
                this.mBtnColorBubble.setEnabled(false);
                return;
            default:
                return;
        }
    }


    public void unlockButton() {
        switch (this.mBooster) {
            case COLOR_BUBBLE:
                UIEffect.clearColorFilter(this.mBtnBombBubble);
                UIEffect.clearColorFilter(this.mBtnFireBubble);
                UIEffect.clearColorFilter(this.mTxtBombBubble);
                UIEffect.clearColorFilter(this.mTxtFireBubble);
                this.mBtnBombBubble.setEnabled(true);
                this.mBtnFireBubble.setEnabled(true);
                return;
            case FIRE_BUBBLE:
                UIEffect.clearColorFilter(this.mBtnBombBubble);
                UIEffect.clearColorFilter(this.mBtnColorBubble);
                UIEffect.clearColorFilter(this.mTxtBombBubble);
                UIEffect.clearColorFilter(this.mTxtColorBubble);
                this.mBtnBombBubble.setEnabled(true);
                this.mBtnColorBubble.setEnabled(true);
                return;
            case BOMB_BUBBLE:
                UIEffect.clearColorFilter(this.mBtnFireBubble);
                UIEffect.clearColorFilter(this.mBtnColorBubble);
                UIEffect.clearColorFilter(this.mTxtFireBubble);
                UIEffect.clearColorFilter(this.mTxtColorBubble);
                this.mBtnFireBubble.setEnabled(true);
                this.mBtnColorBubble.setEnabled(true);
                return;
            default:
                return;
        }
    }
}
