package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape;

import android.graphics.Bitmap;
import android.graphics.Rect;

public interface ShapeCollidable {
    Bitmap getCollisionBitmap();

    Rect getCollisionBounds();

    void setCollisionBoundsPosition(int i, int i2);
}
