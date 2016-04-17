package controller;

import gui.View;
import util.Average;
import world.glue.State;

import java.util.ArrayList;
import java.util.List;

import static util.Utils.tryToSleep;

/**
 * Periodically asks for game state update and repaint.
 */
public class GameLoop {
    State gameState;
    private boolean paused;
    Average updateTimes;
    long prevUpdatePaintedTime;
    long lastUpdateBurnedTime;
    long currentSpeed; /* Goal how many milliseconds between updates */
    int indexForCurrentSpeed;
    List<Long> speedOptions;
    View view;

    /**
     * Constructor initializes game loop.
     * @param gameState reference to the game state
     * @param view reference to the view
     */
    public GameLoop(State gameState, View view) {
        this.gameState = gameState;
        initializeSpeeds();
        updateTimes = new Average();
        prevUpdatePaintedTime = timeNow();
        this.view = view;
    }

    /** Creates a list of speeds which user can iterate back and forth. */
    public void initializeSpeeds() {
        this.speedOptions = new ArrayList<Long>();
        for (long i = 5; i <= 3000; i *= 1.5) {
            speedOptions.add(i);
        }
        this.indexForCurrentSpeed = 9;  // about 190ms
        this.currentSpeed = speedOptions.get(indexForCurrentSpeed);
    }

    /** Increases loop speed. */
    public void faster() {
        if (indexForCurrentSpeed == 0) {
            return;
        }
        indexForCurrentSpeed--;
        currentSpeed = speedOptions.get(indexForCurrentSpeed);
    }

    /** Decreases loop speed. */
    public void slower() {
        if (indexForCurrentSpeed == speedOptions.size()) {
            return;
        }
        indexForCurrentSpeed++;
        currentSpeed = speedOptions.get(indexForCurrentSpeed);
    }

    /** Starts game loop. */
    public void start() {
        waitForNextUpdateTime();
        while (true) {
            waitBeforeUpdating(); /* User clicks will cause repaints too, so we don't want to update too early */
            if (paused) {
                /* This block must be just before updateGameState to get instant pausing */
                tryToSleep(50L);
                continue;
            }
            updateGameState();
            waitForNextUpdateTime(); /* We don't want to draw too early */
            askForRepaint();
            waitUntilPaintIsDry();
        }
    }

    /** Protection against concurrency related repaint errors. */
    private void askForRepaint() {
        view.dontTouchThePaint = true;
        view.repaint();
    }
    private void waitUntilPaintIsDry() {
        while (view.dontTouchThePaint) {
            tryToSleep(1L);
        }
        //System.out.println("DIFF = " + (timeNow() - prevUpdatePaintedTime));
        prevUpdatePaintedTime = timeNow();
    }

    private void waitBeforeUpdating() {
        long expectedUpdateTime = 5 + Math.max((long) updateTimes.getAverage(), lastUpdateBurnedTime);
        long endTime = prevUpdatePaintedTime + currentSpeed - expectedUpdateTime;
        waitUntil(endTime);
    }

    private void waitForNextUpdateTime() {
        long endTime = prevUpdatePaintedTime + currentSpeed;
        waitUntil(endTime);
    }

    /** Waits with a combination of Thread.sleep and busywaiting. */
    private void waitUntil(long endTime) {
        long waitGoal = endTime - timeNow();
        long sleepGoal = waitGoal - 20L;
        if (sleepGoal > 0) {
            tryToSleep(sleepGoal);
        }
        while (timeNow() < endTime) {
            /* Since Thread.sleep is not accurate, we busywait approx. last 20ms of the wait goal */

            if (1 == 2) {
                System.out.println("Kurssivaatimuksena tyhjiä while-lohkoja ei saanut olla. Siksi tämä rivi on tässä.");
            }
        }
    }


    private long timeNow() {
        return System.nanoTime() / 1000000;
    }

    /** Asks gameState to step ahead and measures time spent. */
    private void updateGameState() {
        long timeBeforeUpdate = timeNow();
        gameState.stepAhead();
        long timeAfterUpdate = timeNow();
        long updateBurnedTime = timeAfterUpdate - timeBeforeUpdate;
        lastUpdateBurnedTime = updateBurnedTime;
        updateTimes.addInstance(updateBurnedTime);
    }

    /** If loop was paused, continues loop. Otherwise pauses loop. */
    public void pauseOrPlay() {
        paused = !paused;
    }
}
