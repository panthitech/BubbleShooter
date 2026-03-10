package com.puzzle.bubble.shooter.colors.bubbleshooter.game_engine.engine;

public abstract class Loop extends Thread implements Loopable {
    protected final GameEngine mGameEngine;
    public volatile boolean mIsPause;
    public volatile boolean mIsRunning;
    public final Object mLock = new Object();

    public Loop(GameEngine gameEngine) {
        this.mGameEngine = gameEngine;
    }

    public void run() {
        long previousTimeMillis = System.currentTimeMillis();
        while (this.mIsRunning) {
            long currentTimeMillis = System.currentTimeMillis();
            long elapsedTimeMillis = currentTimeMillis - previousTimeMillis;
            if (this.mIsPause) {
                while (this.mIsPause) {
                    try {
                        synchronized (this.mLock) {
                            this.mLock.wait();
                        }
                    } catch (InterruptedException e) {
                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            onLoopUpdate(elapsedTimeMillis);
            previousTimeMillis = currentTimeMillis;
        }
    }

    public void startLoop() {
        this.mIsRunning = true;
        this.mIsPause = false;
        start();
    }

    public void stopLoop() {
        this.mIsRunning = false;
        resumeLoop();
    }

    public void pauseLoop() {
        this.mIsPause = true;
    }

    public void resumeLoop() {
        if (this.mIsPause) {
            this.mIsPause = false;
            synchronized (this.mLock) {
                this.mLock.notify();
            }
        }
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public boolean isPaused() {
        return this.mIsPause;
    }
}
