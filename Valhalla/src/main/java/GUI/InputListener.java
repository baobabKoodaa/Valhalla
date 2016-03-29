package GUI;

import Util.Point;

import java.awt.event.*;

public class InputListener implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private Point lastDrag;
    private Point lastPress;
    private Painter painter;

    public InputListener(Painter painter) {
        this.painter = painter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.paramString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /* Not using this event, because it would incorrectly trigger from mouse drags */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point point = getPoint(e);

        /* User may intend to click or drag */
        lastDrag = point;
        lastPress = point;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point point = getPoint(e);
        if (point.equals(lastPress)) {
            painter.userClickedOn(point);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse entered event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse exited event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point point = getPoint(e);
        int offsetChangeX = lastDrag.x - point.x;
        int offsetChangeY = lastDrag.y - point.y;
        lastDrag = point;
        painter.mouseDragged(offsetChangeX, offsetChangeY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("Mouse moved event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) painter.zoomIn();
        else painter.zoomOut();
    }

    public Point getPoint(MouseEvent e) {
        return new Point(e.getY(), e.getX());
    }
}
