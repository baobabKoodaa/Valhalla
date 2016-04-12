package world.representation;

import world.representation.Food;

import java.awt.*;

import static util.Utils.*;

/**
 * A dead organism becomes food to other players.
 */
public class Remains extends Food {
    int player;

    public Remains(int player) {
        this.player = player;
        Color color = getRemainsColorForPlayer(player);
        super.setColor(color);
    }

    /** Remains of player A may not be eaten by player A. */
    @Override
    public boolean canBeEatenBy(int player) {
        return this.player != player;
    }
}
