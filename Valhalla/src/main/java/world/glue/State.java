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

/**
 * Internal game state is accessed via a State object.
 */
public class State {
    public boolean updateInProgress;
    private Cell[][] map;
    private List<Organism> organismList;
    private int round;

    public State() {
        round = 1;
        organismList = new ArrayList<>();

        /* Generates arbitrary map for testing purposes */
        int size = 500;
        map = new Cell[size][size];
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

    public void placeSomeOrganisms() {
        placeOrganism(new Nanobot(), HUMAN_PLAYER, map[10][10]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[20][20]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[200][200]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[400][400]);
        placeOrganism(new Worm(), HUMAN_PLAYER, map[300][300]);
    }

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

    /* We want clusters of food & individual spots */
    private boolean shouldHaveFood(int y, int x) {
        if (percentOfTime(1)) {
            return true;
        }
        boolean northFood = (y > 0 && map[y - 1][x].hasFood());
        boolean westFood = (x > 0 && map[y][x - 1].hasFood());
        //if (northFood && westFood) return percentOfTime(90);
        if (northFood || westFood) {
            return percentOfTime(40);
        }
        return false;
    }

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

    public boolean actedAlreadyThisRound(Organism organism) {
        return (organism.getRoundOfLatestAction() == round);
    }

    public Cell[][] getMap() {
        return map;
    }

    /* Diagonal not considered to be adjacent */
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

    /* Diagonal not considered to be adjacent */
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

    /* Diagonal not considered to be adjacent */
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

    public List<Cell> getNeighboursWithinRadius(int centerY, int centerX, int radius) {
        List<Cell> list = new ArrayList<>();
        for (int y = centerY - radius; y <= centerY + radius; y++) {
            for (int x = centerX - radius; x <= centerX + radius; x++) {
                if (y < 0 || x < 0) {
                    continue;
                }
                if (y >= map.length || x >= map[y].length) {
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

    public List<Cell> getCellsWithinRadius(int centerY, int centerX, int radius) {
        List<Cell> cells = getNeighboursWithinRadius(centerY, centerX, radius);
        cells.add(map[centerY][centerX]);
        return cells;
    }

    public int getRound() {
        return round;
    }

    public int getOrganismCount() {
        return organismList.size();
    }
}
