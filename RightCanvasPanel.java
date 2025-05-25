import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RightCanvasPanel extends JPanel implements MouseMotionListener, MouseListener {
    
    Point oldPoint = new Point();
    Point newPoint  = new Point();
    Image image;
    Graphics2D graphics2d;
    public static Color penColor = new Color(0, 0, 0);
    public static int pen = 4;

    RightCanvasPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent me) {
        if (me.getModifiers() == MouseEvent.BUTTON3_MASK) {
            penColor = JColorChooser.showDialog(this, "Change pen color", penColor);
        }
    }

    public void mousePressed (MouseEvent me) {};
    public void mouseReleased (MouseEvent me) {};
    public void mouseEntered (MouseEvent me) {};
    public void mouseExited (MouseEvent me) {};

    @Override
    public void mouseDragged(MouseEvent me) {
        graphics2d.setStroke(new BasicStroke(pen));
        graphics2d.setColor(penColor);
        newPoint = me.getPoint();
        if (graphics2d != null) {
            graphics2d.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
        }
        repaint();
        oldPoint = newPoint;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        oldPoint = me.getPoint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            graphics2d = (Graphics2D) image.getGraphics();
        } 
        else {
            g.drawImage(image, 0, 0, null);
        }
        
    }
}