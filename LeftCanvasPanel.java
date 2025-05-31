import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> items = new ArrayList<>();
    private leftSubCanvas subCanvas;
    private int subCanvasX, subCanvasY;
    private Graphics2D g2d;

    public LeftCanvasPanel() {
        setTransferHandler(new ImageDropHandler());
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);
    }

    public void addItem(CreationItem item) {
        items.add(item);
        repaint();
    }

    public void centerItemInSubCanvas(CreationItem item) {
        if (subCanvas == null) {
            return;
        }
        subCanvas.addItem(item);
    }

    public void newSubCanvas(int width, int height) {
        subCanvasX = (getWidth() - width) / 2;
        subCanvasY = (getHeight() - height) / 2;

        subCanvas = new leftSubCanvas(width, height, getWidth(), getHeight());

        for (int i = 0; i < items.size(); i++) {
            CreationItem item = items.get(i);
            centerItemInSubCanvas(item);
            item.draw(g2d);
        }
        repaint();
    }

    public void addImageToSubCanvas(CreationItem item) {
        if (subCanvas == null) {
            return;
        }
        subCanvas.addItem(item);
        repaint();
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (subCanvas != null) {
            subCanvas.draw(g2d);
        } else {
            for(int i = 0; i < items.size(); i++) {
                CreationItem item = items.get(i);
                item.draw(g2d);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Left Canvas (Image Composition)", 10, 20);
        g.dispose();
    }

    public BufferedImage getComposedImage(boolean JPG) {
        if (subCanvas == null) {
            JOptionPane.showMessageDialog(this, "No subcanvas found to compose image.");
            return null;
        }
        return subCanvas.getComposedImage(JPG);
    }

    public BufferedImage getPNGCanvasImage() {
        return getComposedImage(false);
    }

    public BufferedImage getJPGCanvasImage() {
        return getComposedImage(true);
    }

    public BufferedImage getSubCanvas() {
        if (subCanvas == null) {
            JOptionPane.showMessageDialog(this, "No subcanvas found.");
            return null;
        }
        return subCanvas.getPNGCanvasImage();
    }

    public int getSubCanvasWidth() {
        return subCanvas.getPNGCanvasImage().getWidth();
    }

    public int getSubCanvasHeight() {
        return subCanvas.getPNGCanvasImage().getHeight();
    }

    public int getSubCanvasX() {
        return subCanvasX;
    }

    public int getSubCanvasY() {
        return subCanvasY;
    }
}