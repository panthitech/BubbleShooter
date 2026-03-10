package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.MainActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.database.DatabaseHelper;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.product.Product;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;

public class ConfirmDialog extends GameDialog implements View.OnClickListener {
    public final DatabaseHelper mDatabaseHelper;
    public final Product mProduct;
    public int mSelectedId;

    public ConfirmDialog(GameActivity activity, Product product) {
        super(activity);
        setContentView(R.layout.dialog_confirm);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        this.mProduct = product;
        this.mDatabaseHelper = ((MainActivity) activity).getDatabaseHelper();
        init();
    }

    public void init() {
        ((ImageView) findViewById(R.id.image_product)).setImageResource(this.mProduct.getDrawableId());
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        ImageButton btnConfirm = (ImageButton) findViewById(R.id.btn_confirm);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createButtonEffect(btnConfirm);
        UIEffect.createPopUpEffect(btnConfirm);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        } else if (id == R.id.btn_confirm) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            this.mSelectedId = id;
            buy();
            dismiss();
        }
    }

    public void buy() {
        int price = this.mProduct.getPrice();
        int saving = this.mDatabaseHelper.getItemNum(Item.COIN);
        if (saving < price) {
            this.mSelectedId = R.id.btn_watch_ad;
            return;
        }
        this.mDatabaseHelper.updateItemNum(Item.COIN, saving - price);
        this.mDatabaseHelper.updateItemNum(this.mProduct.getName(), this.mDatabaseHelper.getItemNum(this.mProduct.getName()) + 1);
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    
    public void onHide() {
        if (this.mSelectedId == R.id.btn_watch_ad) {
            this.mParent.showDialog(new AdCoinDialog(this.mParent));
        } else if (this.mSelectedId == R.id.btn_confirm) {
            this.mParent.showDialog(new NewBoosterDialog(this.mParent, this.mProduct.getDrawableId()));
            updateCoin();
        }
    }

    public void updateCoin() {
    }
}
