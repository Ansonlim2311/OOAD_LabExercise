import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

// AnimalItems class is a subclass of AbstructCreationItem
class AnimalItems extends AbstractCreationItem {
    private BufferedImage image;

    // constructor to create an AniamlItem at a specific location
    public AnimalItems(BufferedImage image, int x, int y) {
        super(x, y);
        this.image = image;
    }

    // draw the image with current transformation into canvas
    @Override
    public void draw(Graphics2D g2d) {
        drawFlippedImage(g2d, image);
    }

    // reutrn the original width
    @Override
    public int getWidth() { 
        return image.getWidth(); 
    }

    // return the original height
    @Override
    public int getHeight() { 
        return image.getHeight(); 
    }
}