package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.GameView;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.Collidable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.collision.algorithm.QuadTree;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.Drawable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity.Updatable;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEventListener;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    public final List<Updatable> mAddWaitingPool = new ArrayList();
    public DrawLoop mDrawLoop;
    public final List<Drawable> mDrawables = new ArrayList();
    public final GameView mGameView;
    public final QuadTree mQuadTree = new QuadTree();
    public final List<Updatable> mRemoveWaitingPool = new ArrayList();
    public final List<Updatable> mUpdatables = new ArrayList();
    public UpdateLoop mUpdateLoop;

    public GameEngine(GameView gameView) {
        this.mGameView = gameView;
        this.mGameView.setDrawables(this.mDrawables);
        this.mQuadTree.init(gameView.getWidth(), gameView.getHeight());
    }

    public void startGame() {
        stopGame();
        this.mUpdateLoop = new UpdateLoop(this);
        this.mUpdateLoop.startLoop();
        this.mDrawLoop = new DrawLoop(this);
        this.mDrawLoop.startLoop();
    }

    public void stopGame() {
        if (this.mUpdateLoop != null) {
            this.mUpdateLoop.stopLoop();
            this.mUpdateLoop = null;
        }
        if (this.mDrawLoop != null) {
            this.mDrawLoop.stopLoop();
            this.mDrawLoop = null;
        }
    }

    public void pauseGame() {
        if (this.mUpdateLoop != null) {
            this.mUpdateLoop.pauseLoop();
        }
        if (this.mDrawLoop != null) {
            this.mDrawLoop.pauseLoop();
        }
    }

    public void resumeGame() {
        if (this.mUpdateLoop != null) {
            this.mUpdateLoop.resumeLoop();
        }
        if (this.mDrawLoop != null) {
            this.mDrawLoop.resumeLoop();
        }
    }

    public boolean isRunning() {
        return this.mUpdateLoop != null && this.mUpdateLoop.isRunning();
    }

    public boolean isPaused() {
        return this.mUpdateLoop != null && this.mUpdateLoop.isPaused();
    }

    public void update(long elapsedMillis) {
        int size = this.mUpdatables.size();
        for (int i = 0; i < size; i++) {
            Updatable u = this.mUpdatables.get(i);
            if (u.isActive()) {
                u.update(elapsedMillis);
            }
        }
        checkCollision();
        synchronized (this.mDrawables) {
            while (!this.mRemoveWaitingPool.isEmpty()) {
                removeFromEngine(this.mRemoveWaitingPool.remove(0));
            }
            while (!this.mAddWaitingPool.isEmpty()) {
                addToEngine(this.mAddWaitingPool.remove(0));
            }
        }
    }

    public void draw() {
        this.mGameView.draw();
    }

    public void checkCollision() {
        this.mQuadTree.checkCollision(this);
    }

    public void addToEngine(Updatable updatable) {
        this.mUpdatables.add(updatable);
        if (updatable instanceof Drawable) {
            this.mDrawables.add((Drawable) updatable);
        }
        if (updatable instanceof Collidable) {
            this.mQuadTree.addCollidableObject((Collidable) updatable);
        }
    }

    public void removeFromEngine(Updatable updatable) {
        if (this.mUpdatables.remove(updatable)) {
            if (updatable instanceof Drawable) {
                this.mDrawables.remove((Drawable) updatable);
            }
            if (updatable instanceof Collidable) {
                this.mQuadTree.removeCollidableObject((Collidable) updatable);
            }
        }
    }

    public void addUpdatable(Updatable updatable) {
        if (isRunning()) {
            this.mAddWaitingPool.add(updatable);
        } else {
            addToEngine(updatable);
        }
    }

    public void removeUpdatable(Updatable updatable) {
        this.mRemoveWaitingPool.add(updatable);
    }

    public void onGameEvent(GameEvent gameEvent) {
        synchronized (this.mDrawables) {
            int size = this.mUpdatables.size();
            for (int i = 0; i < size; i++) {
                Updatable u = this.mUpdatables.get(i);
                if (u instanceof GameEventListener) {
                    ((GameEventListener) u).onGameEvent(gameEvent);
                }
            }
        }
    }
}
