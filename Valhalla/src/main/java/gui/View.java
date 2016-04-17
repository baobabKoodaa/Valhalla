package gui;

import controller.GameLoop;
import world.glue.Cell;
import world.glue.State;

import javax.swing.*;
import util.Pair;
import world.representation.Blob;

import java.awt.*;

import static util.MagicNumbers.HEAD_MARKER_COLOR;
import static util.MagicNumbers.HUMAN_PLAYER;
import static util.Utils.getColorForTerrain;
import static util.Utils.tryToSleep;

/**
 * GUI responsible for painting a view and interpreting user actions.
 */
public class View {
    private JFrame frame;
    private JPanel canvas;
    private GameLoop gameLoop;

    public boolean dontTouchThePaint;
    private State state;
    private int cellSideLength;
    private double offsetY;
    private double offsetX;
    private int viewHeight;
    private int viewWidth;
    private Pair selectedSq;
    private ButtonMapper buttonMapper;

    public View(State state) {
        frame = new JFrame();
        this.state = state;
        this.viewWidth = 1200;
        this.viewHeight = 1200;
        this.cellSideLength = 50;
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(viewWidth, viewHeight));
        canvas.setBackground(Color.white);
        InputListener inputListener = new InputListener(this);
        canvas.addMouseListener(inputListener);
        canvas.addMouseMotionListener(inputListener);
        canvas.addMouseWheelListener(inputListener);
        createButtons();
        repaint();

        frame.getContentPane().add(canvas);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void setGameLoop(GameLoop loop) {
        this.gameLoop = loop;
    }

    public void repaint() {
        canvas.repaint();
    }

    private class Canvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            while (state.updateInProgress) {
                tryToSleep(1L);
            }
            dontTouchThePaint = true; /* Because of concurrency, should be also set everywhere that repaint is asked */
            Graphics2D g2d = (Graphics2D) g;
            //System.out.println("GFX ? " + (g2d != null));

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
                    if (cell.isVisibleTo(HUMAN_PLAYER)) drawCell(g2d, cell, viewY, viewX);
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
            /* Draw media buttons */
            for (Button button : buttonMapper.getList()) {
                button.draw(g2d);
            }
            g2d.setColor(Color.BLACK);
            g2d.drawString(state.round + "", viewWidth/2 + 63, 30);
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
    }

    public void createButtons() {
        buttonMapper = new ButtonMapper();
        int buttonWidth = 32;
        int buttonHeight = buttonWidth;
        int buttonGap = buttonWidth/4;
        int topYofButtons = 10;
        int xOfFirstButton = viewWidth/2 - (3*buttonWidth+2*buttonGap)/2;
        Button slower = new Button("slower", xOfFirstButton, topYofButtons, buttonWidth, buttonHeight);
        Button pauseplay = new Button("pause", xOfFirstButton + buttonWidth + buttonGap, topYofButtons, buttonWidth, buttonHeight);
        Button faster = new Button("faster", xOfFirstButton + 2*(buttonWidth + buttonGap), topYofButtons, buttonWidth, buttonHeight);
        buttonMapper.add(slower);
        buttonMapper.add(pauseplay);
        buttonMapper.add(faster);
    }



    /* temp to help drawing */
    public void userClickedOnHELPER(Pair point) {
        int mapX = getMapXFromView(point.x);
        int mapY = getMapYFromView(point.y);
        state.getMap()[mapY][mapX].addElement(new Blob());
        repaint();
    }

    /* Interpret either as button click or selection of a cell */
    public void userClickedOn(Pair point) {
        Button button = buttonMapper.getButton(point);
        if (button != null) {
            pressButton(button.name);
        }
        else {
            mapSelection(point);
        }
        repaint();
    }

    public void mapSelection(Pair point) {
        int mapX = getMapXFromView(point.x);
        int mapY = getMapYFromView(point.y);
        if (selectedSq != null) {
            selectedSq = null;
        }
        else {
            selectedSq = new Pair(mapY, mapX);
        }
    }

    public void pressButton(String name) {
        switch (name) {
            case "pause":
                gameLoop.pauseOrPlay();
                break;
            case "faster":
                gameLoop.faster();
                break;
            case "slower":
                gameLoop.slower();
                break;
            default:
                break;
        }
    }

    public void zoomIn(Pair point) {
        int newZoom = (int) Math.round(1.1 * this.cellSideLength);
        applyZoom(newZoom, point);
    }

    public void zoomOut(Pair point) {
        if (this.cellSideLength * state.getMap().length < viewHeight) return;
        int newZoom = (int) Math.round(this.cellSideLength / 1.1);
        applyZoom(newZoom, point);
    }

    public void applyZoom(int newZoom, Pair point) {
        int prevZoom = this.cellSideLength;
        this.cellSideLength = newZoom;
        double change = 1.0 * prevZoom / newZoom;
        this.offsetY /= change;
        this.offsetX /= change;
        this.offsetY += point.y/change;
        this.offsetX += point.x/change;
        this.offsetY -= point.y;
        this.offsetX -= point.x;
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

