package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.algorithm;

import android.graphics.Rect;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine.GameEngine;
import java.util.ArrayList;
import java.util.List;

public class QuadTree {
    public static final int MAX_TREE_NODE = 12;
    public final List<Collision> mDetectedCollisions = new ArrayList();
    public final QuadTreeNode mRoot = new QuadTreeNode(this);
    public final List<QuadTreeNode> mTreeNodePool = new ArrayList();

    public QuadTree() {
        this.mTreeNodePool.clear();
        for (int i = 0; i < 12; i++) {
            this.mTreeNodePool.add(new QuadTreeNode(this));
        }
    }

    public void init(int width, int height) {
        this.mRoot.setArea(new Rect(0, 0, width, height));
    }

    public void checkCollision(GameEngine gameEngine) {
        while (!this.mDetectedCollisions.isEmpty()) {
            Collision.returnToPool(this.mDetectedCollisions.remove(0));
        }
        this.mRoot.checkCollision(gameEngine, this.mDetectedCollisions);
    }

    public void addCollidableObject(Collidable objectToAdd) {
        this.mRoot.mCollidables.add(objectToAdd);
    }

    public void removeCollidableObject(Collidable objectToRemove) {
        this.mRoot.mCollidables.remove(objectToRemove);
    }

    public int getPoolSize() {
        return this.mTreeNodePool.size();
    }

    public QuadTreeNode getNode() {
        return this.mTreeNodePool.remove(0);
    }

    public void returnToPool(QuadTreeNode treeNode) {
        this.mTreeNodePool.add(treeNode);
    }
}
