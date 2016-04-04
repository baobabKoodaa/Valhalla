package gui;

import util.Pair;

import javax.swing.*;
import java.awt.event.*;

public class InputListener implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private Pair lastDrag;
    private Pair lastPress;
    private View view;

    public InputListener(View view) {
        this.view = view;
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
        Pair point = getPoint(e);

        /* User may intend to click or drag */
        lastDrag = point;
        lastPress = point;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Pair point = getPoint(e);
        if (point.equals(lastPress)) {
            view.userClickedOn(point);
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
        Pair point = getPoint(e);
        int offsetChangeX = lastDrag.x - point.x;
        int offsetChangeY = lastDrag.y - point.y;
        lastDrag = point;
        view.mouseDragged(offsetChangeX, offsetChangeY);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("Mouse moved event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Pair point = getPoint(e);
        if (e.getWheelRotation() < 0) view.zoomIn(point);
        else view.zoomOut(point);
    }

    public Pair getPoint(MouseEvent e) {
        return new Pair(e.getY(), e.getX());
    }
}
