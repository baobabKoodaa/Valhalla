package Logic;

import GUI.View;
import Util.Average;
import World.State;

public class GameLoop {
    State gameState;
    Long betweenUpdatesGoal;
    Average betweenUpdatesActual;
    Average updateTimes;
    long prevUpdateFinishedTime;
    View view;

    public GameLoop(State gameState, View view) {
        this.gameState = gameState;
        betweenUpdatesGoal = 100L;
        updateTimes = new Average();
        betweenUpdatesActual = new Average();
        prevUpdateFinishedTime = timeNow();
        this.view = view;
    }

    public void start() {
        while (true) {
            waitForNextUpdateTime();
            updateGameState();

            //System.out.println("Average: " + betweenUpdatesActual.getAverage());
        }
    }

    /* Waits with a combination of Thread.sleep and busywaiting */
    private void waitForNextUpdateTime() {
        long timeAtStart = timeNow();
        long waitGoal = betweenUpdatesGoal - (long) updateTimes.getAverage();
        long sleepGoal = waitGoal - 20L;
        if (sleepGoal > 0) tryToSleep(sleepGoal);
        while (timeNow() < timeAtStart + waitGoal) {
            /* Since Thread.sleep is not accurate, we busywait approx. last 20ms of the wait goal */
        }
    }

    private void tryToSleep(long sleepGoal) {
        try {
            Thread.sleep(sleepGoal);
        } catch (Exception e) {
            /* If we can't get sleep, we will end up just busywaiting longer */
        }
    }

    private long timeNow() {
        return System.nanoTime() / 1000000;
    }

    /* Performs update and measures time burned */
    private void updateGameState() {
        long timeBeforeUpdate = timeNow();
        gameState.stepAhead();
        long timeAfterUpdate = timeNow();
        view.repaint();
        long updateBurnedTime = timeAfterUpdate - timeBeforeUpdate;
        updateTimes.addInstance(updateBurnedTime);
        long timeBetweenThisAndPrev = timeAfterUpdate - prevUpdateFinishedTime;
        prevUpdateFinishedTime = timeAfterUpdate;
        betweenUpdatesActual.addInstance(timeBetweenThisAndPrev);
    }
}
