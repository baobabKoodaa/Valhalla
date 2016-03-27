package GUI;

import World.State;

import javax.swing.*;
import java.awt.*;

public class View {
    private JFrame frame;
    private Painter painter;

    public View(State state) {
        int height = 800;
        int width = 800;
        frame = new JFrame();
        painter = new Painter(state, width, height);
        painter.setBackground(Color.white);
        frame.add(painter);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
    }
}

