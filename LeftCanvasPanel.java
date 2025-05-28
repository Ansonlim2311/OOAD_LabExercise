import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> items = new ArrayList<>();
    private BufferedImage subCanvas;

    public LeftCanvasPanel() {
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);
    }

    public void addItem(CreationItem item) {
        items.add(item);
        repaint();
    }

    public void newSubCanvas(int width, int height) {
        subCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = subCanvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        setPreferredSize(new Dimension(width, height));
        revalidate();
        repaint();
    }

    public void addImageToSubCanvas(CreationItem item) {
    if (subCanvas == null) {
        return;
    }
    Graphics2D g2d = subCanvas.createGraphics();
    item.draw(g2d);
    g2d.dispose();
    repaint();
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (subCanvas != null) {
            g2d.drawImage(subCanvas, 0, 40, null);
        }
        for(int i = 0; i < items.size(); i++) {
            CreationItem item = items.get(i);
            item.draw(g2d);
        }
        g.setColor(Color.BLACK);
        g.drawString("Left Canvas (Image Composition)", 10, 20);
    }

    public BufferedImage getComposedImage() {
        int width = getWidth();
        int height = getHeight();

        BufferedImage composed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gra2D = composed.createGraphics();

        if (subCanvas != null) {
            gra2D.drawImage(subCanvas, 0, 40, null);
        }

        for(int i = 0; i < items.size(); i++) {
            CreationItem item = items.get(i);
            item.draw(gra2D);
        }
        gra2D.dispose();
        return composed;
    }

    public BufferedImage getSubCanvas() {
        return subCanvas;
    }

    public int getSubCanvasWidth() {
        return subCanvas.getWidth();
    }

    public int getSubCanvasHeight() {
        return subCanvas.getHeight();
    }
}