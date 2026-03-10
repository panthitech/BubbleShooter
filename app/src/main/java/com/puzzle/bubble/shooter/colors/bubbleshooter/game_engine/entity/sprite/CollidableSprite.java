package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.sprite;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.CollisionType;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.ShapeCollidable;

public abstract class CollidableSprite extends Sprite implements Collidable {
    public ShapeCollidable mCollisionShape;
    public CollisionType mCollisionType = CollisionType.ACTIVE;

    protected CollidableSprite(Game game, int drawableId) {
        super(game, drawableId);
    }

    public CollisionType getCollisionType() {
        return this.mCollisionType;
    }

    public void setCollisionType(CollisionType type) {
        this.mCollisionType = type;
    }

    public ShapeCollidable getCollisionShape() {
        return this.mCollisionShape;
    }

    public void setCollisionShape(ShapeCollidable shapeCollidable) {
        this.mCollisionShape = shapeCollidable;
    }

    public void onCollision(Collidable otherObject) {
    }

    public void onPostUpdate(long elapsedMillis) {
        this.mCollisionShape.setCollisionBoundsPosition(((int) this.mX) + (this.mWidth / 2), ((int) this.mY) + (this.mHeight / 2));
    }

    public void draw(Canvas canvas) {
        if (Game.getDebugMode() && getCollisionType() != CollisionType.NONE) {
            canvas.drawBitmap(this.mCollisionShape.getCollisionBitmap(), (Rect) null, this.mCollisionShape.getCollisionBounds(), (Paint) null);
        }
        super.draw(canvas);
    }
}
