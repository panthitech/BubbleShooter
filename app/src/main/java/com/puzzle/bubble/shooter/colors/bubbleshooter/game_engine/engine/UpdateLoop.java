package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine;

public class UpdateLoop extends Loop {
    public UpdateLoop(GameEngine gameEngine) {
        super(gameEngine);
    }

    public void onLoopUpdate(long elapsedMillis) {
        this.mGameEngine.update(elapsedMillis);
    }
}
