package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.entity;

public interface Updatable {
    void addToGame();

    boolean isActive();

    void removeFromGame();

    void update(long j);
}
