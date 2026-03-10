package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.event;

public interface GameEventListener {
    void gameEvent(GameEvent gameEvent);

    void onGameEvent(GameEvent gameEvent);
}
