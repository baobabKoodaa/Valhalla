package World;

import java.util.List;

import static Util.MagicNumbers.nanobotLineOfSight;
import static Util.Utils.percentOfTime;

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
        if (percentOfTime(50)) return;
        Limb head = (Limb) map[y][x].getTopElement(); /* TODO: Same, but typesafe */
        Cell moveTo = getState().getRandomAdjacentNonFriendlyCell(y, x, getPlayer());
        if (moveTo == null) return;
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
        if (birthPlace == null) return;
        getState().placeOrganism(new Nanobot(), getPlayer(), birthPlace);
    }

    private void clearLineOfSight() {
        List<Cell> cellsInRange = getState().getCellsWithinRadius(y, x, nanobotLineOfSight);
        for (Cell cell : cellsInRange) {
            cell.setVisibleTo(getPlayer());
        }
    }

}
