import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> items = new ArrayList<>();
    private List<CreationItem> oldItems = new ArrayList<>();
    private leftSubCanvas newSubCanvas, subCanvas;
    private int x, y;
    private CreationItem item;

    public LeftCanvasPanel() {
        setTransferHandler(new ImageDropHandler());
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);
    }

    public void addItem(CreationItem item) {
        if (subCanvas != null) {
            addImageToSubCanvas(item);
        } else {
            x = (getWidth() - item.getWidth()) / 2;
            y = (getHeight() - item.getHeight()) / 2;
            item.setPosition(x, y);
            items.add(item);
        }
        repaint();
    }

    public void newSubCanvas(int width, int height) {
        if (subCanvas == null) {
            subCanvas = new leftSubCanvas(width, height);
        } else {
            newSubCanvas = new leftSubCanvas(width, height);
            oldItems = subCanvas.getItems();
            for (int i = 0; i < oldItems.size(); i++) {
                item = oldItems.get(i);
                newSubCanvas.addItem(item);
            }
            subCanvas = newSubCanvas;
        }
        repaint();
    }

    public void addImageToSubCanvas(CreationItem item) {
        if (subCanvas == null) {
            JOptionPane.showMessageDialog(this, "No subcanvas found. Please create one first.");
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
            subCanvas.draw(g2d, getWidth(), getHeight());
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
}