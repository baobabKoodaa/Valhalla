package Launcher;

import GUI.View;
import Controller.GameLoop;
import World.State;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("moi");
        State gameState = new State();
        View view = new View(gameState);
        GameLoop loop = new GameLoop(gameState, view);
        loop.start();
    }
}
