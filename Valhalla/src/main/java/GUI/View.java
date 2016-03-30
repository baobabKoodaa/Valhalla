package GUI;

import World.State;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;
    public Painter painter;

    public View(State state) {
        int height = 1200;
        int width = 1200;
        frame = new JFrame();
        painter = new Painter(state, width, height);
        frame.getContentPane().add(painter);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void repaint() {
        painter.repaint();
    }

}

