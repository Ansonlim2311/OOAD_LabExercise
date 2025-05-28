import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> items = new ArrayList<>();
    private BufferedImage subCanvas;
    private int subCanvasX, subCanvasY;
    private Graphics2D g2d;

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
        g2d = subCanvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        subCanvasX = (getWidth() - width) / 2;
        subCanvasY = (getHeight() - height) / 2;

        repaint();
    }

    public void addImageToSubCanvas(CreationItem item) {
        if (subCanvas == null) {
            return;
        }
        items.add(item);
        repaint();
}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (subCanvas != null) {

            subCanvasX = (getWidth() - subCanvas.getWidth()) / 2;
            subCanvasY = (getHeight() - subCanvas.getHeight()) / 2;
            g2d.drawImage(subCanvas, subCanvasX, subCanvasY, null);

            Shape oldClip = g2d.getClip();
            g2d.setClip(subCanvasX, subCanvasY, subCanvas.getWidth(), subCanvas.getHeight());

            g2d.translate(subCanvasX, subCanvasY);
            for(int i = 0; i < items.size(); i++) {
                CreationItem item = items.get(i);
                item.draw(g2d);
            }
            g2d.translate(-subCanvasX, -subCanvasY);
            g2d.setClip(oldClip);
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

    public BufferedImage getComposedImage() {

        BufferedImage composed = new BufferedImage(subCanvas.getWidth(), subCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gra2D = composed.createGraphics();

        if (subCanvas != null) {
            gra2D.drawImage(subCanvas, 0, 0, null);
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

    public int getSubCanvasX() {
        return subCanvasX;
    }

    public int getSubCanvasY() {
        return subCanvasY;
    }
}