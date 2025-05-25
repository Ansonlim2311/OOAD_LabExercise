import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class RightCanvasPanel extends JPanel implements MouseMotionListener, MouseListener {
    
    Point oldPoint = new Point();
    Point newPoint  = new Point();
    BufferedImage image, jpgImage;
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
        int width = getWidth();
        int height = getHeight();

        if (image == null) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            graphics2d = image.createGraphics();
            graphics2d.setBackground(Color.WHITE);
            graphics2d.fillRect(0, 0, width, height);
        }
        g.drawImage(image, 0, 0, null);
    }

    public BufferedImage getCanvasImage(boolean JPG) {
        if (image == null) {
            return null;
        }

        if (JPG == false) {
            return image;
        }

        int width = getWidth();
        int height = getHeight();
        jpgImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = jpgImage.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(image, 0, 0, null);
        
        return jpgImage;
    }

    public BufferedImage getPNGCanvasImage() {
        return getCanvasImage(false);
    }

    public BufferedImage getJPGCanvasImage() {
        return getCanvasImage(true);
    }
}