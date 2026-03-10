package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.shape.ShapeCollidable;

public interface Collidable {
    ShapeCollidable getCollisionShape();

    CollisionType getCollisionType();

    void onCollision(Collidable collidable);

    void setCollisionShape(ShapeCollidable shapeCollidable);

    void setCollisionType(CollisionType collisionType);
}
