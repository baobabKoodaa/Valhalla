package world.underlying;

import world.glue.Cell;
import world.representation.Element;
import world.representation.Limb;
import world.representation.Remains;

import java.util.*;

import static util.MagicNumbers.NANOBOT_LINE_OF_SIGHT;

/**
 * Traditional worm (like Nokia cellphone games).
 */
public class Worm extends Organism {
    private Deque<Cell> partsOfWorm;
    private int length;

    @Override
    public void physicalBirth(Cell birthPlace) {
        this.length = 4;
        this.partsOfWorm = new ArrayDeque<>();
        /* x,y denotes head */
        this.y = birthPlace.y;
        this.x = birthPlace.x;
        Limb head = new Limb(this);
        head.setAsHead();
        birthPlace.addElement(head);
        partsOfWorm.addLast(birthPlace);
        clearLineOfSight();
    }

    @Override
    public void takeDamage(Cell cell) {
        dropTailUpTo(cell);
        if (partsOfWorm.isEmpty()) {
            this.alive = false;
        }
    }

    @Override
    public void live(Cell[][] map) {
        Cell prev = partsOfWorm.pollLast(); /* We will add this back */
        Element prevHead = map[y][x].getTopElement();
        prevHead.unsetAsHead();
        Cell moveTo = getState().getRandomAdjacentNonFriendlyCell(y, x, getPlayer());
        if (moveTo == null) {
            moveTo = getSomeCannibalizingMove();
            Limb limb = (Limb) moveTo.getTopElement();
            if (limb.getOrganism() == this) {
                dropTailUpTo(moveTo);
            } else {
                limb.getOrganism().takeDamage(moveTo);
            }
        }
        y = moveTo.y;
        x = moveTo.x;
        partsOfWorm.addLast(prev);
        partsOfWorm.addLast(moveTo);
        if (map[y][x].eatFoodAs(getPlayer())) {
            length++;
        }
        if (partsOfWorm.size() > length) {
            Cell tail = partsOfWorm.pollFirst();
            map[tail.y][tail.x].removeTopElement();
        }
        Limb head = new Limb(this);
        head.setAsHead();
        map[y][x].addElement(head);
        clearLineOfSight();
    }

    private Cell getSomeCannibalizingMove() {
        List<Cell> neighbors = getState().getNeighboursWithinRadius(y, x, 1);
        Collections.shuffle(neighbors);
        for (Cell neighbor : neighbors) {
            if (neighbor.y != y && neighbor.x != x) {
                continue; /* Diagonals are not ok */
            }
            /* Head has been temporarily polled from partsOfWorm to reveal neck */
            if (!partsOfWorm.isEmpty() && neighbor == partsOfWorm.peekLast()) {
                /* To stop worms from doing 180¤ */
                continue;
            }
            return neighbor;
        }
        return null; /* Never returns null */
    }

    private void dropTailUpTo(Cell eatenCell) {
        for (int i = partsOfWorm.size() - 1; i >= 0; i--) {
            Cell tail = partsOfWorm.pollFirst();
            length--;
            tail.removeTopElement();
            tail.addElement(new Remains(getPlayer()));
            if (tail == eatenCell) {
                break;
            }
        }
    }

    private void clearLineOfSight() {
        List<Cell> cellsInRange = getState().getCellsWithinRadius(y, x, NANOBOT_LINE_OF_SIGHT);
        for (Cell cell : cellsInRange) {
            cell.setVisibleTo(getPlayer());
        }
    }

    public int getLength() {
        return length;
    }
}
