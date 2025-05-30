import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class RightCanvasPanel extends JPanel implements MouseMotionListener, MouseListener {
    
    private Point oldPoint = new Point();
    private Point newPoint  = new Point();
    private BufferedImage image, newImage, jpgImage;
    private boolean hasDrawn, unsavedChanges = false;
    private Graphics2D graphics2d, g2d, clearG2;
    private PenButtonHandler penHandler;
    private EraserButtonHandler eraserHandler;

    RightCanvasPanel(PenButtonHandler penHandler, EraserButtonHandler eraserHandler) {
        this.penHandler = penHandler;
        this.eraserHandler = eraserHandler;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent me) {};
    public void mousePressed (MouseEvent me) {};
    public void mouseReleased (MouseEvent me) {};
    public void mouseEntered (MouseEvent me) {};
    public void mouseExited (MouseEvent me) {};

    @Override
    public void mouseDragged(MouseEvent me) {
        graphics2d.setStroke(new BasicStroke(penHandler.getPenSize()));
        if (eraserHandler.eraserActive() == true) {
            graphics2d.setColor(Color.WHITE);
        }
        else {
            graphics2d.setColor(penHandler.getPenColor());
        }
        newPoint = me.getPoint();
        if (graphics2d != null) {
            graphics2d.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
        }
        repaint();
        oldPoint = newPoint;
        hasDrawn = true;
        unsavedChanges = true;
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
        if (width > 0 && height > 0) {
            newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = newImage.createGraphics();
            g2d.setBackground(Color.WHITE);
            g2d.clearRect(0, 0, width, height);

            if (image != null) {
                g2d.drawImage(image, 0, 0, null);
            }
            g2d.dispose();
            image = newImage;
            graphics2d = image.createGraphics();
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


    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    public void setUnsavedChange() {
        unsavedChanges = false;
    }

    public boolean isEmpty() {
        return !hasDrawn;
    }

    public void clearCanvas() {
        if (image != null) {
            clearG2 = image.createGraphics();
            clearG2.setColor(Color.WHITE);
            clearG2.fillRect(0, 0, getWidth(), getHeight());
            clearG2.dispose();
            hasDrawn = false;
            unsavedChanges = false;
            repaint();
        }
    }
}