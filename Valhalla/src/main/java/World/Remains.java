package world;

import java.awt.*;

import static util.Utils.*;

public class Remains extends Food {
    int player;

    public Remains(int player) {
        this.player = player;
        Color color = getRemainsColorForPlayer(player);
        super.setColor(color);
    }

    @Override
    public boolean canBeEatenBy(int player) {
        return this.player != player;
    }
}
