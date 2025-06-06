import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

// Drawing canvas class where users can draw with a pen or erase drawings
public class RightCanvasPanel extends JPanel implements MouseMotionListener, MouseListener {
    // Variables for drawing and erasing
    private Point oldPoint = new Point();
    private Point newPoint  = new Point();
    private BufferedImage image, newImage, jpgImage;
    private boolean hasDrawn, unsavedChanges = false;
    private Graphics2D graphics2d, g2d, clearG2;
    private PenButtonHandler penHandler;
    private ColourButtonHandler colourHandler;
    private EraserButtonHandler eraserHandler;

    // Constructor initializes the canvas with pen and eraser handlers
    RightCanvasPanel(PenButtonHandler penHandler, ColourButtonHandler colourHandler, EraserButtonHandler eraserHandler) {
        this.penHandler = penHandler;
        this.colourHandler = colourHandler;
        this.eraserHandler = eraserHandler;
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent me) {};
    public void mousePressed (MouseEvent me) {};
    public void mouseReleased (MouseEvent me) {};
    public void mouseEntered (MouseEvent me) {};
    public void mouseExited (MouseEvent me) {};

    // Draw line as users drag the mouse
    @Override
    public void mouseDragged(MouseEvent me) {
        if (graphics2d == null) {
            return;
        }
        graphics2d.setStroke(new BasicStroke(penHandler.getPenSize()));
        if (eraserHandler.eraserActive() == true) {
            graphics2d.setColor(Color.WHITE);
        }
        else {
            graphics2d.setColor(colourHandler.getPenColor());
        }
        newPoint = me.getPoint();
        graphics2d.drawLine(oldPoint.x, oldPoint.y, newPoint.x, newPoint.y);
        repaint();
        oldPoint = newPoint;
        hasDrawn = true;
        unsavedChanges = true;
    }

    // store user current mouse position
    @Override
    public void mouseMoved(MouseEvent me) {
        oldPoint = me.getPoint();
    }

    // Paint the canvas with the current image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            graphics2d = image.createGraphics();
            graphics2d.setBackground(Color.WHITE);
            graphics2d.fillRect(0, 0, getWidth(), getHeight());
        }
        if (getWidth() > 0 && getHeight() > 0) {
            newImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = newImage.createGraphics();
            g2d.setBackground(Color.WHITE);
            g2d.clearRect(0, 0, getWidth(), getHeight());

            if (image != null) {
                g2d.drawImage(image, 0, 0, null);
            }
            g2d.dispose();
            image = newImage;
            graphics2d = image.createGraphics();
        }
        g.drawImage(image, 0, 0, null);
    }

    // return the current image of the right canvas
    public BufferedImage getCanvasImage(boolean JPG) {
        if (image == null) {
            return null;
        }

        if (JPG == false) {
            return image;
        }

        jpgImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        g2d = jpgImage.createGraphics();
        g2d.setBackground(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(image, 0, 0, null);
        
        return jpgImage;
    }

    // return the current image of the right canvas in PNG format
    public BufferedImage getPNGCanvasImage() {
        return getCanvasImage(false);
    }

    // return the current image of the right canvas in JPG format
    public BufferedImage getJPGCanvasImage() {
        return getCanvasImage(true);
    }

    // check if the canvas has unsaved changes
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }

    // mark canvas status to saved
    public void setUnsavedChange() {
        unsavedChanges = false;
    }

    // check if canvas is empty or not
    public boolean isEmpty() {
        return !hasDrawn;
    }

    // clear the right canvas 
    public void clearCanvas() {
        if (image != null) {
            clearG2 = image.createGraphics();
            clearG2.setColor(Color.WHITE);
            clearG2.fillRect(0, 0, 
                image.getWidth(), image.getHeight());
            clearG2.dispose();
            hasDrawn = false;
            unsavedChanges = false;
            repaint();
        }
    }
}