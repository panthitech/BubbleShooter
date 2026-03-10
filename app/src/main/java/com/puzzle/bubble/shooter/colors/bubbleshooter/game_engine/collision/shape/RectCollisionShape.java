package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.view.InputDeviceCompat;

public class RectCollisionShape extends CollisionShape {
    public final Bitmap mCollisionBitmap;

    public RectCollisionShape(int width, int height) {
        super(width, height);
        this.mCollisionBitmap = getBitmap(width, height);
    }

    public Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(InputDeviceCompat.SOURCE_ANY);
        canvas.drawRect(0.0f, 0.0f, (float) width, (float) height, paint);
        return bitmap;
    }

    public Bitmap getCollisionBitmap() {
        return this.mCollisionBitmap;
    }
}
