package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.Drawable;
import java.util.List;

public class GameView extends View {
    public List<Drawable> mDrawables;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawables(List<Drawable> drawables) {
        this.mDrawables = drawables;
    }

    public void draw() {
        postInvalidate();
    }

    
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (this.mDrawables) {
            int min = getMinLayer();
            int max = getMaxLayer();
            for (int i = min; i <= max; i++) {
                drawLayer(canvas, i);
            }
        }
    }

    public void drawLayer(Canvas canvas, int layer) {
        int size = this.mDrawables.size();
        for (int i = 0; i < size; i++) {
            Drawable d = this.mDrawables.get(i);
            if (d.isVisible() && d.getLayer() == layer) {
                d.draw(canvas);
            }
        }
    }

    public int getMinLayer() {
        int min = this.mDrawables.get(0).getLayer();
        int size = this.mDrawables.size();
        for (int i = 0; i < size; i++) {
            int layer = this.mDrawables.get(i).getLayer();
            if (layer < min) {
                min = layer;
            }
        }
        return min;
    }

    public int getMaxLayer() {
        int max = this.mDrawables.get(0).getLayer();
        int size = this.mDrawables.size();
        for (int i = 0; i < size; i++) {
            int layer = this.mDrawables.get(i).getLayer();
            if (layer > max) {
                max = layer;
            }
        }
        return max;
    }
}
