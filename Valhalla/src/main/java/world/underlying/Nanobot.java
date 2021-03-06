package world.underlying;

import world.glue.Cell;
import world.representation.Remains;
import world.representation.Limb;

import java.util.List;

import static util.MagicNumbers.NANOBOT_LINE_OF_SIGHT;
import static util.Utils.percentOfTime;

/**
 * Replicating organism which occupies 1 Cell only.
 */
public class Nanobot extends Organism {

    @Override
    public void physicalBirth(Cell birthPlace) {
        this.y = birthPlace.y;
        this.x = birthPlace.x;
        Limb head = new Limb(this);
        birthPlace.addElement(head);
        clearLineOfSight();
    }

    @Override
    public void takeDamage(Cell cell) {
        cell.removeTopElement();
        cell.addElement(new Remains(getPlayer()));
        this.alive = false;
    }

    @Override
    public void live(Cell[][] map) {
        if (percentOfTime(50)) {
            move(map);
        }
    }

    /**
     * Tries to move to a neighboring cell, possibly eating food and replicating.
     * @param map map
     */
    public void move(Cell[][] map) {
        Limb head = (Limb) map[y][x].getTopElement(); /* TODO: Same, but typesafe */
        Cell moveTo = getState().getRandomAdjacentNonFriendlyCell(y, x, getPlayer());
        if (moveTo == null) {
            return;
        }
        /* Clear previous cell */
        map[y][x].removeTopElement();
        /* Set new cell */
        y = moveTo.y;
        x = moveTo.x;
        if (map[y][x].eatFoodAs(getPlayer())) {
            replicate();
        }
        map[y][x].addElement(head);
        clearLineOfSight();
    }

    private void replicate() {
        Cell birthPlace = getState().getRandomAdjacentEmptyCell(y, x);
        if (birthPlace == null) {
            return;
        }
        getState().placeOrganism(new Nanobot(), getPlayer(), birthPlace);
    }

    private void clearLineOfSight() {
        List<Cell> cellsInRange = getState().getCellsWithinRadius(y, x, NANOBOT_LINE_OF_SIGHT);
        for (Cell cell : cellsInRange) {
            cell.setVisibleTo(getPlayer());
        }
    }

}
