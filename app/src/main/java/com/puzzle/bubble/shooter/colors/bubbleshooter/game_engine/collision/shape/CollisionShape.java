package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape;

import android.graphics.Rect;

public abstract class CollisionShape implements ShapeCollidable {
    public final Rect mBounds = new Rect(-1, -1, -1, -1);
    public final int mCollisionShapeHeight;
    public final int mCollisionShapeWidth;

    protected CollisionShape(int collisionShapeWidth, int collisionShapeHeight) {
        this.mCollisionShapeWidth = collisionShapeWidth;
        this.mCollisionShapeHeight = collisionShapeHeight;
    }

    public Rect getCollisionBounds() {
        return this.mBounds;
    }

    public void setCollisionBoundsPosition(int x, int y) {
        this.mBounds.set(x - (this.mCollisionShapeWidth / 2), y - (this.mCollisionShapeHeight / 2), (this.mCollisionShapeWidth / 2) + x, (this.mCollisionShapeHeight / 2) + y);
    }
}
