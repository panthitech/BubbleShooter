package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.algorithm;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import java.util.ArrayList;
import java.util.List;

public class Collision {
    public static final List<Collision> sCollisionPool = new ArrayList();
    public Collidable mObjectA;
    public Collidable mObjectB;

    public static Collision init(Collidable objectA, Collidable objectB) {
        if (sCollisionPool.isEmpty()) {
            return new Collision(objectA, objectB);
        }
        Collision c = sCollisionPool.remove(0);
        c.mObjectA = objectA;
        c.mObjectB = objectB;
        return c;
    }

    public static void returnToPool(Collision c) {
        c.mObjectA = null;
        c.mObjectB = null;
        sCollisionPool.add(c);
    }

    public Collision(Collidable objectA, Collidable objectB) {
        this.mObjectA = objectA;
        this.mObjectB = objectB;
    }

    public boolean equals(Collision c) {
        return (this.mObjectA == c.mObjectA && this.mObjectB == c.mObjectB) || (this.mObjectA == c.mObjectB && this.mObjectB == c.mObjectA);
    }
}
