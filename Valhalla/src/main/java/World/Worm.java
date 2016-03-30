package World;

import java.util.*;

import static Util.MagicNumbers.nanobotLineOfSight;

public class Worm extends Organism {
    Deque<Cell> partsOfWorm;
    int length;

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
    public void takeDamage() {

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
            if (limb.organism == this) cannibalizeUpToAndIncluding(moveTo);
            else {
                System.out.println("Killed a friendly. Check this works ok.");
                limb.organism.alive = false;
                /* TODO: Create remains */
                /* TODO: Worms mauling worms */
            }
        }
        y = moveTo.y;
        x = moveTo.x;
        partsOfWorm.addLast(prev);
        partsOfWorm.addLast(moveTo);
        if (map[y][x].hasFoodFor(getPlayer())) {
            map[y][x].removeTopElement();
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
            if (neighbor.y != y && neighbor.x != x) continue; /* Diagonals are not ok */
            /* Head has been temporarily polled from partsOfWorm to reveal neck */
            if (!partsOfWorm.isEmpty() && neighbor == partsOfWorm.peekLast()) {
                /* To stop worms from doing 180¤ */
                continue;
            }
            return neighbor;
        }
        return null; /* Never returns null */
    }

    private void cannibalizeUpToAndIncluding(Cell eatenCell) {
        for (int i=partsOfWorm.size()-1; i>=0; i--) {
            Cell tail = partsOfWorm.pollFirst();
            length--;
            tail.removeTopElement();
            tail.addElement(new Remains(getPlayer()));
            if (tail == eatenCell) break;
        }
    }





    private void clearLineOfSight() {
        List<Cell> cellsInRange = getState().getCellsWithinRadius(y, x, nanobotLineOfSight);
        for (Cell cell : cellsInRange) {
            cell.setVisibleTo(getPlayer());
        }
    }
}
