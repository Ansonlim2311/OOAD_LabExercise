import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private static class PositionedImage {
        Image image;
        int x, y;
        PositionedImage(Image image, int x, int y) {
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }

    private List<PositionedImage> images = new ArrayList<>();

    public LeftCanvasPanel() {
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);
    }

    public void addImage(Image image, int x, int y) {
        images.add(new PositionedImage(image, x, y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < images.size(); i++) {
            PositionedImage poImage = images.get(i);
            g.drawImage(poImage.image, poImage.x, poImage.y, this);
        }

        g.drawString("Left Canvas (Image Composition)", 10, 20);
    }

    public BufferedImage getComposedImage() {
        int width = getWidth();
        int height = getHeight();

        BufferedImage composed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gra2D = composed.createGraphics();

        for(int i = 0; i < images.size(); i++) {
            PositionedImage poImage = images.get(i);
            gra2D.drawImage(poImage.image, poImage.x, poImage.y, this);
        }
        gra2D.dispose();
        return composed;
    }
}