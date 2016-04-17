package world.underlying;

import world.glue.Cell;
import world.glue.State;

public abstract class Organism {
    public boolean alive;
    public int y;
    public int x;
    private State state;
    private int player;
    private int roundOfLatestAction;

    /**
     * Constructor.
     */
    public Organism() {
        this.roundOfLatestAction = -666;
        this.alive = true;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getRoundOfLatestAction() {
        return roundOfLatestAction;
    }
    public void setRoundOfLatestAction(int round) {
        roundOfLatestAction = round;
    }

    public int getPlayer() {
        return player;
    }

    /**
     * Initializes the birth of organism to cell birthPlace.
     * @param birthPlace cell where this thing is born
     */
    abstract public void physicalBirth(Cell birthPlace);

    /**
     * Takes a damage to some limb at cell.
     * @param cell where damaged limb was
     */
    abstract public void takeDamage(Cell cell);

    /**
     * Lives on the map.
     * @param map map
     */
    abstract public void live(Cell[][] map);
}
