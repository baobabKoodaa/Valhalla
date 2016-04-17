package world.glue;

import world.representation.Food;
import world.representation.Terrain;
import world.underlying.Nanobot;
import world.underlying.Organism;
import world.underlying.Worm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static util.MagicNumbers.HUMAN_PLAYER;
import static util.Utils.percentOfTime;
import static world.representation.Terrain.*;

/** Internal game state is accessed via a State object. */
public class State {
    public boolean updateInProgress;
    public int round;
    private Cell[][] map;
    private List<Organism> organismList;

    /** Constructor initializes state. Generates arbitrary map for testing purposes. */
    public State() {
        round = 1;
        organismList = new ArrayList<>();
        map = new Cell[500][500];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                Terrain terrain = ATOMICSNOW;
                Cell sq = new Cell(y, x, terrain);
                map[y][x] = sq;
                if (shouldHaveFood(y, x)) {
                    sq.addElement(new Food());
                }
            }
        }
    }

    /** Places some new organisms on the map. */
    public void placeSomeOrganisms() {
        placeOrganism(new Nanobot(), HUMAN_PLAYER, map[10][10]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[20][20]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[200][200]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[400][400]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[300][300]);
    }

    /** Places a particular organism on a particular cell.
     * @param o organism to place
     * @param player player
     * @param cell cell
     */
    public void placeOrganism(Organism o, int player, Cell cell) {
        while (!cell.isEmpty()) {
            cell.removeTopElement();
        }
        o.setPlayer(player);
        o.setState(this);
        o.physicalBirth(cell);
        o.setRoundOfLatestAction(round);
        organismList.add(o);
    }

    private boolean shouldHaveFood(int y, int x) { /* Used in map generation. We want clusters of food & individual spots */
        if (percentOfTime(1)) {
            return true;
        }
        boolean northFood = (y > 0 && map[y - 1][x].hasFood());
        boolean westFood = (x > 0 && map[y][x - 1].hasFood());
        return (northFood || westFood) && percentOfTime(40);
    }

    /** Steps ahead to the next game state. Called from game loop. */
    public void stepAhead() {
        updateInProgress = true;
        round++;
        for (int i = organismList.size() - 1; i >= 0; i--) {
            Organism organism = organismList.get(i);
            /* Because of combats an organism may have acted already */
            if (actedAlreadyThisRound(organism)) {
                continue;
            }
            if (!organism.alive) {
                organismList.remove(i);
                continue;
            }
            organism.live(map);
            organism.setRoundOfLatestAction(round);
        }
        updateInProgress = false;
    }

    /** Returns true if organism has already acted this round.
     * @param organism organism
     * @return true if organism has already acted this round
     */
    public boolean actedAlreadyThisRound(Organism organism) {
        return (organism.getRoundOfLatestAction() == round);
    }

    public Cell[][] getMap() {
        return map;
    }

    /** Returns random adjacent cell (diagonal not considered to be adjacent).
     * @param y y of center
     * @param x x of center
     * @return random adjacent cell
     */
    public Cell getRandomAdjacentCell(int y, int x) {
        List<Cell> options = getNeighboursWithinRadius(y, x, 1);
        Collections.shuffle(options);
        for (Cell cell : options) {
            if (cell.x != x && cell.y != y) {
                continue; /* Diagonal */
            }
            return cell;
        }
        return null; /* Never returns null */
    }

    /** Returns random adjacent empty cell (diagonal not considered to be adjacent).
     * @param y y of center
     * @param x x of center
     * @return random adjacent empty cell, or null if none are possible
     */
    public Cell getRandomAdjacentEmptyCell(int y, int x) {
        List<Cell> options = getNeighboursWithinRadius(y, x, 1);
        Collections.shuffle(options);
        for (Cell cell : options) {
            if (cell.x != x && cell.y != y) {
                continue; /* Diagonal */
            }
            if (cell.isEmpty()) {
                return cell;
            }
        }
        return null;
    }

    /** Returns random adjacent non friendly cell (diagonal not considered to be adjacent).
     * @param y y of center
     * @param x x of center
     * @param player player to determine friendly or not
     * @return cell
     */
    public Cell getRandomAdjacentNonFriendlyCell(int y, int x, int player) {
        List<Cell> options = getNeighboursWithinRadius(y, x, 1);
        Collections.shuffle(options);
        for (Cell cell : options) {
            if (cell.x != x && cell.y != y) {
                continue; /* Diagonal */
            }
            if (!cell.hasLimbOf(player)) {
                return cell;
            }
        }
        return null;
    }

    /** Returns a list of cells which are neighbors to given coordinates within a given radius.
     * @param centerY y
     * @param centerX x
     * @param radius radius
     * @return list of cells
     */
    public List<Cell> getNeighboursWithinRadius(int centerY, int centerX, int radius) {
        List<Cell> list = new ArrayList<>();
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (y < 0 || x < 0 || y >= map.length || x >= map[y].length) {
                    continue;
                }
                if (y == centerY && x == centerX) {
                    continue;
                }
                list.add(map[y][x]);
            }
        }
        return list;
    }

    /** Similar to getNeighboursWithinRadius, but includes the center as well.
     * @param centerY y
     * @param centerX x
     * @param radius radius
     * @return list of cells
     */
    public List<Cell> getCellsWithinRadius(int centerY, int centerX, int radius) {
        List<Cell> cells = getNeighboursWithinRadius(centerY, centerX, radius);
        cells.add(map[centerY][centerX]);
        return cells;
    }

    public int getOrganismCount() {
        return organismList.size();
    }
}