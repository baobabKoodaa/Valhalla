package launcher;

import gui.View;
import controller.GameLoop;
import world.glue.State;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        State gameState = new State();
        View view = new View(gameState);
        GameLoop loop = new GameLoop(gameState, view);
        view.setGameLoop(loop);
        loop.start();
    }
}
