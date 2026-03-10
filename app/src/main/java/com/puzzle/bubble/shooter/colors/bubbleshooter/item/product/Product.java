package com.puzzle.bubble.shooter.colors.bubbleshooter.item.product;

public class Product {
    public int mButtonId;
    public String mDescription;
    public int mDrawableId;
    public final String mName;
    public final int mPrice;

    public Product(String name, int price) {
        this.mName = name;
        this.mPrice = price;
    }

    public String getName() {
        return this.mName;
    }

    public int getPrice() {
        return this.mPrice;
    }

    public int getDrawableId() {
        return this.mDrawableId;
    }

    public int getButtonId() {
        return this.mButtonId;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setView(int drawableResId, int buttonResId) {
        this.mDrawableId = drawableResId;
        this.mButtonId = buttonResId;
    }
}
