import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

class CustomImage extends AbstractCreationItem {
    private BufferedImage image;

    public CustomImage(BufferedImage image) {
        super(0, 0);
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, getX(), getY(), null);
    }

    @Override
    public int getWidth() { 
        return image.getWidth(); 
    }

    @Override
    public int getHeight() { 
        return image.getHeight(); 
    }

    @Override
    protected BufferedImage getImage() { 
        return image; 
    }

    @Override
    public void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight) {
        int w = getWidth();
        int h = getHeight();
        double scale = Math.min((double)maxWidth / w, (double)maxHeight / h);
        int newW = (int)(w * scale);
        int newH = (int)(h * scale);
        g2d.drawImage(image, getX(), getY(), newW, newH, null);
    }
}