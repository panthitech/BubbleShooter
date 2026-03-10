package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine;

public interface Loopable {
    boolean isPaused();

    boolean isRunning();

    void onLoopUpdate(long j);

    void pauseLoop();

    void resumeLoop();

    void startLoop();

    void stopLoop();
}
