import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


public class LeftSubCanvas {
    private int x, y, centerX, centerY, width, height;
    private List<CreationItem> items = new ArrayList<>();
    private BufferedImage canvas, outputImage;
    private Graphics2D whiteCanvas, picture;

    public LeftSubCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        whiteCanvas = canvas.createGraphics();
        whiteCanvas.setColor(Color.WHITE);
        whiteCanvas.fillRect(0, 0, width, height);
        whiteCanvas.dispose();
    }

    public void addItem(CreationItem item) {
        centerX = (width - item.getWidth()) / 2;
        centerY = (height - item.getHeight()) / 2;
        item.setPosition(centerX, centerY);
        items.add(item);
    }

    public void draw(Graphics2D g, int panelWidth, int panelHeight) {
        x = (panelWidth - width) / 2;
        y = (panelHeight - height) / 2;

        g.drawImage(canvas, x, y, null);

        Shape oldClip = g.getClip();
        g.setClip(x, y, width, height);
        g.translate(x, y);
        for(int i = 0; i < items.size(); i++) {
            CreationItem item = items.get(i);
            item.draw(g);
        }
        g.translate(-x, -y);
        g.setClip(oldClip);
    }

    public BufferedImage getComposedImage(boolean JPG) {
        if (canvas == null) {
            JOptionPane.showMessageDialog(null, "No subcanvas found to compose image.");
            return null;
        }

        if (JPG == true) {
            outputImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        } else {
            outputImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        picture = outputImage.createGraphics();

        if (JPG == true) {
            picture.setColor(Color.WHITE);
            picture.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        for(int i = 0; i < items.size(); i++) {
            CreationItem item = items.get(i);
            item.draw(picture);
        }
        picture.dispose();
        return outputImage;
    }

    public BufferedImage getPNGCanvasImage() {
        return getComposedImage(false);
    }

    public BufferedImage getJPGCanvasImage() {
        return getComposedImage(true);
    }

    public List<CreationItem> getItems() {
        return new ArrayList<>(items);
    }

    public int getSubCanvasWidth() {
        return width;
    }

    public int getSubCanvasHeight() {
        return height;
    }

    public int getSubCanvasX() {
        return x;
    }

    public int getSubCanvasY() {
        return y;
    }
}