package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.algorithm;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.CollisionType;

public class CollisionHandler {
    public CollisionHandler() {
    }

    public static boolean isCollisionsDetected(Collidable collidableA, Collidable collidableB) {
        if (collidableA.getCollisionType() == CollisionType.PASSIVE && collidableB.getCollisionType() == CollisionType.PASSIVE) {
            return false;
        }
        Rect boundsA = collidableA.getCollisionShape().getCollisionBounds();
        Rect boundsB = collidableB.getCollisionShape().getCollisionBounds();
        if (Rect.intersects(boundsA, boundsB)) {
            Rect collisionBounds = getCollisionBounds(boundsA, boundsB);
            for (int i = collisionBounds.left; i < collisionBounds.right; i += 10) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j += 10) {
                    int collidableAPixel = getBitmapPixel(collidableA.getCollisionShape().getCollisionBitmap(), i - boundsA.left, j - boundsA.top);
                    int collidableBPixel = getBitmapPixel(collidableB.getCollisionShape().getCollisionBitmap(), i - boundsB.left, j - boundsB.top);
                    if (isFilled(collidableAPixel) && isFilled(collidableBPixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Rect getCollisionBounds(Rect rectA, Rect rectB) {
        return new Rect(Math.max(rectA.left, rectB.left), Math.max(rectA.top, rectB.top), Math.min(rectA.right, rectB.right), Math.min(rectA.bottom, rectB.bottom));
    }

    public static int getBitmapPixel(Bitmap bitmap, int i, int j) {
        if (i < 0) {
            i = 0;
        }
        if (j < 0) {
            j = 0;
        }
        int maxX = bitmap.getWidth() - 1;
        if (i > maxX) {
            i = maxX;
        }
        int maxY = bitmap.getHeight() - 1;
        if (j > maxY) {
            j = maxY;
        }
        return bitmap.getPixel(i, j);
    }

    public static boolean isFilled(int pixel) {
        return pixel != 0;
    }
}
