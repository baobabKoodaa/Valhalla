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
        frame.getContentPane().add(painter);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }
}

