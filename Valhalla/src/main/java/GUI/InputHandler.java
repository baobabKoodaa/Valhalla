package GUI;

import java.awt.event.*;

public class InputHandler implements ActionListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private int lastDragX;
    private int lastDragY;
    private Painter painter;

    public InputHandler(Painter painter) {
        this.painter = painter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed: " + e.paramString());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse clicked event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.lastDragX = e.getX();
        this.lastDragY = e.getY();
        //System.out.println("Mouse pressed event " + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released event " + e.getX() + "," + e.getY());
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
        //System.out.println("Mouse dragged event " + e.getX() + "," + e.getY());
        int offsetChangeX = this.lastDragX - e.getX();
        int offsetChangeY = this.lastDragY - e.getY();
        this.lastDragX = e.getX();
        this.lastDragY = e.getY();
        painter.modifyOffset(offsetChangeX, offsetChangeY);
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
}
