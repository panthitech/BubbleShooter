package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.Drawable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.GameObject;

public abstract class Sprite extends GameObject implements Drawable {
    public float mAlpha = 255.0f;
    public Bitmap mBitmap;
    public int mHeight;
    public boolean mIsVisible = true;
    public int mLayer;
    public final Matrix mMatrix = new Matrix();
    public final Paint mPaint = new Paint();
    protected final float mPixelFactor;
    public float mRotation;
    public float mScale = 1.0f;
    public int mWidth;
    public float mX;
    public float mY;

    protected Sprite(Game game, int drawableId) {
        super(game);
        this.mPixelFactor = game.getPixelFactor();
        setSpriteBitmap(drawableId);
    }

    
    public Bitmap getBitmapFromId(int drawableId) {
        return ((BitmapDrawable) this.mGame.getGameActivity().getResources().getDrawable(drawableId)).getBitmap();
    }

    public void setSpriteBitmap(int drawableId) {
        this.mBitmap = getBitmapFromId(drawableId);
        this.mWidth = (int) (((float) this.mBitmap.getWidth()) * this.mPixelFactor);
        this.mHeight = (int) (((float) this.mBitmap.getHeight()) * this.mPixelFactor);
    }

    public void draw(Canvas canvas) {
        if (this.mX <= ((float) canvas.getWidth()) && this.mY <= ((float) canvas.getHeight()) && this.mX >= ((float) (-this.mWidth)) && this.mY >= ((float) (-this.mHeight))) {
            float scaleFactor = this.mPixelFactor * this.mScale;
            float translateFactor = (1.0f - this.mScale) / 2.0f;
            this.mMatrix.reset();
            this.mMatrix.postScale(scaleFactor, scaleFactor);
            this.mMatrix.postTranslate(this.mX + (((float) this.mWidth) * translateFactor), this.mY + (((float) this.mHeight) * translateFactor));
            this.mMatrix.postRotate(this.mRotation, this.mX + (((float) this.mWidth) / 2.0f), this.mY + (((float) this.mHeight) / 2.0f));
            this.mPaint.setAlpha((int) this.mAlpha);
            canvas.drawBitmap(this.mBitmap, this.mMatrix, this.mPaint);
        }
    }

    public void addToGame() {
        this.mIsVisible = true;
        super.addToGame();
    }

    public void removeFromGame() {
        this.mIsVisible = false;
        super.removeFromGame();
    }

    public int getLayer() {
        return this.mLayer;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }
}
