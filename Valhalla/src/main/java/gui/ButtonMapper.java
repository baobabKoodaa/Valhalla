package gui;

import util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Given any pixel on screen, ButtonMapper can answer if any button is on it.
 * ButtonMapper allows us to add buttons to GUI with a single line:
 *      buttonMapper.add(new Button("id", x, y, width, height));
 *      When user clicks on the button, it calls View.pressButton("id")
 *      So you need to add one more line to pressButton to handle case "id", but that's it.
 */
public class ButtonMapper {
    private List<Button> iterableList;
    private HashMap<Pair, Button> pixelMap;

    /** Constructor initializes ButtonMapper. */
    public ButtonMapper() {
        iterableList = new ArrayList<>();
        pixelMap = new HashMap<>();
    }

    /** Adds button. */
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

    /** Given a point on the view, answers if any button is on it.
     *
     * @param point point
     * @return if any button is on point
     */
    public Button getButton(Pair point) {
        return pixelMap.get(point);
    }

    /** Returns iterable list of all buttons.
     *
     * @return iterable list of all buttons.
     */
    public List<Button> getList() {
        return iterableList;
    }
}
