package GUI;

import World.*;

import javax.swing.*;
import java.awt.*;

import static World.Terrain.DESERT;
import static World.Terrain.GRASS;

public class Painter extends JPanel {
    private State state;
    private int zoom; /* Square side length in pixels */
    private int offsetX;
    private int offsetY;
    private int viewWidth;
    private int viewHeight;

    public Painter(State state, int width, int height) {
        this.state = state;
        this.viewWidth = width;
        this.viewHeight = height;
        this.zoom = 50;
        InputHandler inputHandler = new InputHandler(this);
        addMouseListener(inputHandler);
        addMouseMotionListener(inputHandler);
        addMouseWheelListener(inputHandler);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = zoom;
        int height = zoom;
        Square[][] map = state.getMap();
        int topY = offsetY/zoom;
        int leftX = offsetX/zoom;
        int bottomY = topY + (int) Math.ceil(viewHeight / zoom);
        int rightX = leftX + (int) Math.ceil(viewWidth / zoom);
        while (bottomY >= map.length) bottomY--;
        while (rightX >= map[bottomY].length) rightX--;

        int viewY = - offsetY % zoom;
        for (int y=topY; y<=bottomY; y++, viewY+=height) {
            int viewX = - offsetX % zoom;
            for (int x=leftX; x<=rightX; x++, viewX+=width) {
                Square sq = map[y][x];

                /* Draw terrain */
                g2d.setColor(getColor(sq.getTerrain()));
                g2d.fillRect(viewX, viewY, width, height);

                /* Draw grid */
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawRect(viewX, viewY, width, height);

            }
        }
    }

    public Color getColor(Terrain terrain) {
        if (terrain.equals(GRASS)) return Color.GREEN;
        if (terrain.equals(DESERT)) return Color.YELLOW;
        return Color.WHITE;
    }

    public void zoomIn() {
        this.zoom *= 1.1;
        forceOffsetWithinBounds();
        repaint();
    }

    public void zoomOut() {
        if (this.zoom * state.getMap().length < viewHeight) return;
        this.zoom *= 0.9;
        forceOffsetWithinBounds();
        repaint();
    }

    public void modifyOffset(int dx, int dy) {
        this.offsetX += dx;
        this.offsetY += dy;
        forceOffsetWithinBounds();
        repaint();
    }

    public void setOffset(int x, int y) {
        this.offsetX = x;
        this.offsetY = y;
        forceOffsetWithinBounds();
        repaint();
    }

    private void forceOffsetWithinBounds() {
        this.offsetX = Math.max(0, this.offsetX);
        this.offsetY = Math.max(0, this.offsetY);
        while (lastDrawnSqOverflows()) {
            this.offsetX -= 1;
            this.offsetY -= 1;
        }
    }

    private boolean lastDrawnSqOverflows() {
        int topY = offsetY/zoom;
        int leftX = offsetX/zoom;
        int bottomY = topY + (int) Math.ceil(viewHeight / zoom);
        int rightX = leftX + (int) Math.ceil(viewWidth / zoom);
        Square[][] map = state.getMap();
        return (bottomY >= map.length || rightX >= map[bottomY].length);
    }

}

