package com.puzzle.bubble.shooter.colors.bubbleshooter.item.product;

import android.app.Activity;
import com.puzzle.bubble.shooter.colors.bubbleshooter.R;
import com.puzzle.bubble.shooter.colors.bubbleshooter.item.Item;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public final List<Product> mProductList = new ArrayList();

    public ProductManager(Activity activity) {
        Product productWatchAd = new Product(Item.COIN, 0);
        Product productColorBall = new Product(Item.COLOR_BALL, 50);
        Product productFireball = new Product(Item.FIREBALL, 60);
        Product productBomb = new Product(Item.BOMB, 70);
        productWatchAd.setView(R.drawable.product_coin_50, R.drawable.btn_watch_ad);
        productColorBall.setView(R.drawable.product_color_ball, R.drawable.btn_price_50);
        productFireball.setView(R.drawable.product_fireball, R.drawable.btn_price_60);
        productBomb.setView(R.drawable.product_bomb, R.drawable.btn_price_70);
        productWatchAd.setDescription(activity.getString(R.string.txt_coins));
        productColorBall.setDescription(activity.getString(R.string.txt_color_ball));
        productFireball.setDescription(activity.getString(R.string.txt_fireball));
        productBomb.setDescription(activity.getString(R.string.txt_bomb));
        this.mProductList.add(productWatchAd);
        this.mProductList.add(productColorBall);
        this.mProductList.add(productFireball);
        this.mProductList.add(productBomb);
    }

    public List<Product> getAllProducts() {
        return this.mProductList;
    }
}
