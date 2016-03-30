package GUI;

import Util.Pair;
import World.*;

import javax.swing.*;
import java.awt.*;

import static Util.MagicNumbers.HEAD_MARKER_COLOR;
import static Util.MagicNumbers.humanPlayer;
import static Util.Utils.getColorForTerrain;

public class Painter extends JPanel {
    public boolean dontTouchThePaint;
    private State state;
    private int cellSideLength;
    private double offsetY;
    private double offsetX;
    private int viewHeight;
    private int viewWidth;

    private Pair selectedSq;

    public Painter(State state, int width, int height) {
        this.state = state;
        this.viewWidth = width;
        this.viewHeight = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        this.cellSideLength = 50;
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

        Cell[][] map = state.getMap();
        int topY = (int)offsetY/ cellSideLength;
        int leftX = (int)offsetX/ cellSideLength;
        int bottomY = 1 + topY + (int) Math.ceil(viewHeight / cellSideLength);
        int rightX = 1 + leftX + (int) Math.ceil(viewWidth / cellSideLength);
        while (bottomY >= map.length) bottomY--;
        while (rightX >= map[bottomY].length) rightX--;

        int viewY = - (int)offsetY % cellSideLength;
        for (int y=topY; y<=bottomY; y++, viewY+=cellSideLength) {
            int viewX = - (int)offsetX % cellSideLength;
            for (int x=leftX; x<=rightX; x++, viewX+=cellSideLength) {
                Cell cell = map[y][x];
                if (cell.isVisibleTo(humanPlayer)) drawCell(g2d, cell, viewY, viewX);
                else drawUndiscoveredArea(g2d, viewY, viewX);
            }
        }
        /* Draw selection */
        if (selectedSq != null) {
            viewY = - (int)offsetY % cellSideLength + (selectedSq.y - topY) * cellSideLength;
            int viewX = - (int)offsetX % cellSideLength + (selectedSq.x - leftX) * cellSideLength;
            g2d.setColor(Color.YELLOW);
            g2d.drawRect(viewX, viewY, cellSideLength, cellSideLength);
        }
        dontTouchThePaint = false; /* Concurrency related flag */
    }

    public void drawCell(Graphics2D g2d, Cell cell, int viewY, int viewX) {
        /* Draw terrain */
        g2d.setColor(getColorForTerrain(cell.getTerrain()));
        g2d.fillRect(viewX, viewY, cellSideLength, cellSideLength);
        /* Draw element */
        if (cell.getTopElement() != null) {
            g2d.setColor(cell.getTopElement().getColor());
            g2d.fillRect(viewX, viewY, cellSideLength, cellSideLength);
            if (cell.getTopElement().paintAsHead()) {
                g2d.setColor(HEAD_MARKER_COLOR);
                int halvedSide = cellSideLength/2;
                int x = viewX + cellSideLength/4;
                int y = viewY + cellSideLength/4;
                g2d.fillRect(x, y, halvedSide, halvedSide);
            }
        }
        /* Draw grid */
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawRect(viewX, viewY, cellSideLength, cellSideLength);
    }

    public void drawUndiscoveredArea(Graphics2D g2d, int viewY, int viewX) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(viewX, viewY, cellSideLength, cellSideLength);
    }

    public void userClickedOn(Pair point) {
        int mapX = getMapXFromView(point.x);
        int mapY = getMapYFromView(point.y);
        if (selectedSq != null) selectedSq = null;
        else selectedSq = new Pair(mapY, mapX);
        repaint();
    }

    public void zoomIn() {
        int newZoom = (int) Math.round(1.1 * this.cellSideLength);
        applyZoom(newZoom);
    }

    public void zoomOut() {
        if (this.cellSideLength * state.getMap().length < viewHeight) return;
        int newZoom = (int) Math.round(this.cellSideLength / 1.1);
        applyZoom(newZoom);
    }

    public void applyZoom(int newZoom) {
        int prevZoom = this.cellSideLength;
        this.cellSideLength = newZoom;
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
        return (int)(offsetX+viewX)/ cellSideLength;
    }

    public int getMapYFromView(int viewY) {
        return (int)(offsetY+viewY)/ cellSideLength;
    }

}

