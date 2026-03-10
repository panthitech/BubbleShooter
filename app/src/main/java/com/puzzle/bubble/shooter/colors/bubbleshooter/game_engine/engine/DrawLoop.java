package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine;

public class DrawLoop extends Loop {
    public static final int DELTA_TIME = 16;

    public DrawLoop(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void onLoopUpdate(long elapsedMillis) {
        if (elapsedMillis < 16) {
            try {
                Thread.sleep(16 - elapsedMillis);
            } catch (InterruptedException e) {
            }
        }
        this.mGameEngine.draw();
    }
}
