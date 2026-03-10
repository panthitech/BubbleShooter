package com.puzzle.bubble.shooter.colors.bubbleshooter.ui.dialog;

import android.view.View;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameActivity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.ui.GameDialog;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.product.Product;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.product.ProductManager;
import com.puzzle.bubble.shooter.colors.bubbleshooter.sound.MySoundEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.UIEffect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.ui.view.ProductAdapter;
import java.util.List;

public class ShopDialog extends GameDialog implements View.OnClickListener {
    public ShopDialog(GameActivity activity) {
        super(activity);
        setContentView(R.layout.dialog_shop);
        setRootLayoutId(R.layout.dialog_container);
        setEnterAnimationId(R.anim.enter_from_center);
        setExitAnimationId(R.anim.exit_to_center);
        init();
    }

    public void init() {
        ImageButton btnCancel = (ImageButton) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        UIEffect.createButtonEffect(btnCancel);
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox));
        UIEffect.createPopUpEffect(findViewById(R.id.image_fox_bg), 2);
        initProduct(new ProductManager(this.mParent).getAllProducts());
    }

    public void initProduct(List<Product> productList) {
        ProductAdapter productAdapter = new ProductAdapter(this.mParent, productList) {
            public void showDialog(Product product) {
                if (product.getName().equals(Item.COIN)) {
                    ShopDialog.this.showAdCoinDialog();
                } else {
                    ShopDialog.this.showConfirmDialog(product);
                }
            }
        };
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mParent, 1, false));
        recyclerView.setAdapter(productAdapter);
    }

    
    public void showAdCoinDialog() {
        this.mParent.showDialog(new AdCoinDialog(this.mParent) {
            public void updateCoin() {
                ShopDialog.this.updateMapCoin();
            }
        });
    }

    
    public void showConfirmDialog(Product product) {
        this.mParent.showDialog(new ConfirmDialog(this.mParent, product) {
            public void updateCoin() {
                ShopDialog.this.updateMapCoin();
            }
        });
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_cancel) {
            this.mParent.getSoundManager().playSound(MySoundEvent.BUTTON_CLICK);
            dismiss();
        }
    }

    
    public void onShow() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_IN);
    }

    
    public void onDismiss() {
        this.mParent.getSoundManager().playSound(MySoundEvent.SWEEP_OUT);
    }

    public void updateMapCoin() {
    }
}
