package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity;

import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.Game;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEvent;
import com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event.GameEventListener;

public abstract class GameObject implements Updatable, GameEventListener {
    
    public final Game mGame;
    public boolean mIsActive = false;

    public abstract void onUpdate(long j);

    protected GameObject(Game game) {
        this.mGame = game;
    }

    public void update(long elapsedMillis) {
        onPreUpdate(elapsedMillis);
        onUpdate(elapsedMillis);
        onPostUpdate(elapsedMillis);
    }

    public void addToGame() {
        this.mIsActive = true;
        this.mGame.getGameEngine().addUpdatable(this);
        onStart();
    }

    public void removeFromGame() {
        this.mIsActive = false;
        this.mGame.getGameEngine().removeUpdatable(this);
        onRemove();
    }

    public boolean isActive() {
        return this.mIsActive;
    }

    public void onPreUpdate(long elapsedMillis) {
    }

    public void onPostUpdate(long elapsedMillis) {
    }

    public void onStart() {
    }

    public void onRemove() {
    }

    public void gameEvent(GameEvent gameEvent) {
        this.mGame.getGameEngine().onGameEvent(gameEvent);
    }

    public void onGameEvent(GameEvent gameEvent) {
    }
}
