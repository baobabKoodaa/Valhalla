package Controller;

import GUI.View;
import Util.Average;
import World.State;

public class GameLoop {
    State gameState;
    Long betweenUpdatesGoal;
    Average updateTimes;
    long prevUpdatePaintedTime;
    View view;

    public GameLoop(State gameState, View view) {
        this.gameState = gameState;
        betweenUpdatesGoal = 200L;
        updateTimes = new Average();
        prevUpdatePaintedTime = timeNow();
        this.view = view;
    }

    public void start() {
        waitForNextUpdateTime();
        while (true) {
            updateGameState();
            waitForNextUpdateTime();
            askForRepaint();
            waitUntilPaintIsDry();
        }
    }

    /* Threaded repaint was causing concurrency related errors */
    private void askForRepaint() {
        view.painter.dontTouchThePaint = true;
        view.repaint();
    }
    private void waitUntilPaintIsDry() {
        while (view.painter.dontTouchThePaint) {
            tryToSleep(1L);
        }
        //System.out.println("DIFF = " + (timeNow() - prevUpdatePaintedTime));
        prevUpdatePaintedTime = timeNow();
    }




    /* Waits with a combination of Thread.sleep and busywaiting */
    private void waitForNextUpdateTime() {
        long endTime = prevUpdatePaintedTime + betweenUpdatesGoal;
        long waitGoal = endTime - timeNow();
        long sleepGoal = waitGoal - 20L;
        if (sleepGoal > 0) tryToSleep(sleepGoal);
        while (timeNow() < endTime) {
            /* Since Thread.sleep is not accurate, we busywait approx. last 20ms of the wait goal */
        }
    }

    private void tryToSleep(long sleepGoal) {
        try {
            Thread.sleep(sleepGoal);
        } catch (InterruptedException e) {
            /* If we can't get sleep, we will end up just busywaiting longer */
        }
    }

    private long timeNow() {
        return System.nanoTime() / 1000000;
    }

    /* Asks gameState to step ahead and measures time spent */
    private void updateGameState() {
        long timeBeforeUpdate = timeNow();
        gameState.stepAhead();
        long timeAfterUpdate = timeNow();
        long updateBurnedTime = timeAfterUpdate - timeBeforeUpdate;
        updateTimes.addInstance(updateBurnedTime);
    }
}
