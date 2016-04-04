package world;

public abstract class Organism {
    public boolean alive;
    public int y;
    public int x;
    private State state;
    private int player;
    private int roundOfLatestAction;


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

    abstract public void physicalBirth(Cell birthPlace);
    abstract public void takeDamage(Cell cell);
    abstract public void live(Cell[][] map);
}
