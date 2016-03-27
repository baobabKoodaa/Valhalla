package Launcher;

import GUI.View;
import World.State;

public class Main {

    public static void main(String[] args) {
        System.out.println("moi");
        State gameState = new State();
        View view = new View(gameState);
    }
}
