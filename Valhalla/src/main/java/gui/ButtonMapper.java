package gui;

import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Given any pixel on screen, ButtonMapper can answer if any button is on it.
 */
public class ButtonMapper {
    List<Button> iterableList;
    HashMap<Pair, Button> pixelMap;

    public ButtonMapper() {
        iterableList = new ArrayList<>();
        pixelMap = new HashMap<>();
    }

    public void add(Button button) {
        iterableList.add(button);
        /* Add all pixels of button to pixelMap */
        int minY = button.y;
        int maxY = minY + button.height;
        int minX = button.x;
        int maxX = minX + button.width;
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Pair point = new Pair(y, x);
                Button prev = pixelMap.put(point, button);
                if (prev != null) {
                    System.out.println("Warning! Overlayed buttons " + prev.name + " and " + button.name);
                }
            }
        }
    }

    public Button getButton(Pair point) {
        return pixelMap.get(point);
    }

    public List<Button> getList() {
        return iterableList;
    }
}
