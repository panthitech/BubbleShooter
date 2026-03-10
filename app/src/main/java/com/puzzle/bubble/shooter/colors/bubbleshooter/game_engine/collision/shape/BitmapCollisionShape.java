package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import androidx.core.view.InputDeviceCompat;

public class BitmapCollisionShape extends CollisionShape {
    public final Bitmap mCollisionBitmap;

    public BitmapCollisionShape(Bitmap bitmap, int width, int height) {
        super(width, height);
        this.mCollisionBitmap = getBitmap(bitmap, width, height);
    }

    public Bitmap getBitmap(Bitmap sourceBitmap, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(InputDeviceCompat.SOURCE_ANY, PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sourceBitmap, (Rect) null, new Rect(0, 0, width, height), paint);
        return bitmap;
    }

    public Bitmap getCollisionBitmap() {
        return this.mCollisionBitmap;
    }
}
