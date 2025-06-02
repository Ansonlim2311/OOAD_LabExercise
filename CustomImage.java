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
        drawFlippedImage(g2d, image);
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
}