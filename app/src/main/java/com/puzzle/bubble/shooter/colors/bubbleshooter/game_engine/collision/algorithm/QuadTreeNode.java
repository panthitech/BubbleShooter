package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.algorithm;

import android.graphics.Rect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.CollisionType;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine.GameEngine;
import java.util.ArrayList;
import java.util.List;

public class QuadTreeNode {
    public static final int MAX_OBJECTS_TO_CHECK = 8;
    public final Rect mArea = new Rect();
    public final List<QuadTreeNode> mChildren = new ArrayList(4);
    public final List<Collidable> mCollidables = new ArrayList();
    public final QuadTree mParent;

    public QuadTreeNode(QuadTree quadTree) {
        this.mParent = quadTree;
    }

    public void setArea(Rect area) {
        this.mArea.set(area);
    }

    public void checkCollision(GameEngine gameEngine, List<Collision> detectedCollisions) {
        int size = this.mCollidables.size();
        if (size <= 8 || this.mParent.getPoolSize() < 4) {
            for (int i = 0; i < size; i++) {
                Collidable collidableA = this.mCollidables.get(i);
                for (int j = i + 1; j < size; j++) {
                    Collidable collidableB = this.mCollidables.get(j);
                    if (CollisionHandler.isCollisionsDetected(collidableA, collidableB)) {
                        Collision c = Collision.init(collidableA, collidableB);
                        if (!hasBeenDetected(c, detectedCollisions)) {
                            detectedCollisions.add(c);
                            collidableA.onCollision(collidableB);
                            collidableB.onCollision(collidableA);
                        }
                    }
                }
            }
            return;
        }
        divideAndCheck(gameEngine, detectedCollisions);
    }

    public void divideAndCheck(GameEngine gameEngine, List<Collision> detectedCollisions) {
        this.mChildren.clear();
        for (int i = 0; i < 4; i++) {
            this.mChildren.add(this.mParent.getNode());
        }
        for (int i2 = 0; i2 < 4; i2++) {
            QuadTreeNode node = this.mChildren.get(i2);
            node.setArea(getArea(i2));
            node.checkObjects(this.mCollidables);
            node.checkCollision(gameEngine, detectedCollisions);
            node.mCollidables.clear();
            this.mParent.returnToPool(node);
        }
    }

    public void checkObjects(List<Collidable> collidables) {
        this.mCollidables.clear();
        int size = collidables.size();
        for (int i = 0; i < size; i++) {
            Collidable c = collidables.get(i);
            if (c.getCollisionType() != CollisionType.NONE && Rect.intersects(c.getCollisionShape().getCollisionBounds(), this.mArea)) {
                this.mCollidables.add(c);
            }
        }
    }

    public boolean hasBeenDetected(Collision collision, List<Collision> detectedCollisions) {
        int size = detectedCollisions.size();
        for (int i = 0; i < size; i++) {
            if (detectedCollisions.get(i).equals(collision)) {
                return true;
            }
        }
        return false;
    }

    public Rect getArea(int area) {
        int startX = this.mArea.left;
        int startY = this.mArea.top;
        int width = this.mArea.width();
        int height = this.mArea.height();
        switch (area) {
            case 0:
                return new Rect(startX, startY, (width / 2) + startX, (height / 2) + startY);
            case 1:
                return new Rect((width / 2) + startX, startY, startX + width, (height / 2) + startY);
            case 2:
                return new Rect(startX, (height / 2) + startY, (width / 2) + startX, startY + height);
            case 3:
                return new Rect((width / 2) + startX, (height / 2) + startY, startX + width, startY + height);
            default:
                return null;
        }
    }
}
