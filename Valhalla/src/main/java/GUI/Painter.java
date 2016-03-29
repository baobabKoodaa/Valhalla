package GUI;

import Util.Point;
import World.*;

import javax.swing.*;
import java.awt.*;

import static World.Terrain.ATOMICSNOW;
import static World.Terrain.DESERT;
import static World.Terrain.GRASS;

public class Painter extends JPanel {
    private State state;
    private int zoom; /* Square side length in pixels */
    private double offsetY;
    private double offsetX;
    private int viewHeight;
    private int viewWidth;

    private Point selectedSq;

    public Painter(State state, int width, int height) {
        this.state = state;
        this.viewWidth = width;
        this.viewHeight = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        this.zoom = 50;
        InputListener inputListener = new InputListener(this);
        addMouseListener(inputListener);
        addMouseMotionListener(inputListener);
        addMouseWheelListener(inputListener);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        int width = zoom;
        int height = zoom;
        Square[][] map = state.getMap();
        int topY = (int)offsetY/zoom;
        int leftX = (int)offsetX/zoom;
        int bottomY = 1 + topY + (int) Math.ceil(viewHeight / zoom);
        int rightX = 1 + leftX + (int) Math.ceil(viewWidth / zoom);
        while (bottomY >= map.length) bottomY--;
        while (rightX >= map[bottomY].length) rightX--;

        int viewY = - (int)offsetY % zoom;
        for (int y=topY; y<=bottomY; y++, viewY+=height) {
            int viewX = - (int)offsetX % zoom;
            for (int x=leftX; x<=rightX; x++, viewX+=width) {
                Square sq = map[y][x];

                /* Draw terrain */
                g2d.setColor(getColor(sq.getTerrain()));
                g2d.fillRect(viewX, viewY, width, height);

                /* Draw elements */
                for (Element elem : sq.getElements()) {
                    g2d.setColor(elem.getColor());
                    g2d.fillRect(viewX, viewY, width, height);
                }

                /* Draw grid */
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.drawRect(viewX, viewY, width, height);
            }
        }
        /* Draw selection */
        if (selectedSq != null) {
            viewY = - (int)offsetY % zoom + (selectedSq.y - topY) * height;
            int viewX = - (int)offsetX % zoom + (selectedSq.x - leftX) * width;
            g2d.setColor(Color.YELLOW);
            g2d.drawRect(viewX, viewY, width, height);
        }
    }

    public Color getColor(Terrain terrain) {
        if (terrain.equals(GRASS)) return Color.GREEN;
        if (terrain.equals(DESERT)) return Color.PINK;
        if (terrain.equals(ATOMICSNOW)) return Color.WHITE;
        return Color.WHITE;
    }

    public void userClickedOn(Point point) {
        int mapX = getMapXFromView(point.x);
        int mapY = getMapYFromView(point.y);
        System.out.println("Actual click " + mapX +"," + mapY);
        if (selectedSq != null) selectedSq = null;
        else selectedSq = new Point(mapY, mapX);
        repaint();
    }

    public void zoomIn() {
        int newZoom = (int) Math.round(1.1 * this.zoom);
        applyZoom(newZoom);
    }

    public void zoomOut() {
        if (this.zoom * state.getMap().length < viewHeight) return;
        int newZoom = (int) Math.round(this.zoom / 1.1);
        applyZoom(newZoom);
    }

    public void applyZoom(int newZoom) {
        int prevZoom = this.zoom;
        this.zoom = newZoom;
        double change = 1.0 * prevZoom / newZoom;
        this.offsetY /= change;
        this.offsetX /= change;
        forceOffsetWithinBounds();
        repaint();
    }


    public void mouseDragged(int dx, int dy) {
        setOffset(offsetY + dy, offsetX + dx);
    }

    public void setOffset(double y, double x) {
        this.offsetY = y;
        this.offsetX = x;
        forceOffsetWithinBounds();
        repaint();
    }

    private void forceOffsetWithinBounds() {
        this.offsetX = Math.max(0, this.offsetX);
        this.offsetY = Math.max(0, this.offsetY);
        while (tooMuchToTheRight() && this.offsetX > 0) this.offsetX--;
        while (tooMuchToTheSouth() && this.offsetY > 0) this.offsetY--;
    }

    private boolean tooMuchToTheSouth() {
        int y = getMapYFromView(viewHeight);
        return (y >= state.getMap().length);
    }

    private boolean tooMuchToTheRight() {
        int x = getMapXFromView(viewWidth);
        return (x >= state.getMap()[0].length);
    }

    public int getMapXFromView(int viewX) {
        return (int)(offsetX+viewX)/zoom;
    }

    public int getMapYFromView(int viewY) {
        return (int)(offsetY+viewY)/zoom;
    }

}

