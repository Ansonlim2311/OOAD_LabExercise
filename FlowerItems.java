import java.awt.Graphics2D;
import java.awt.Image;

class FlowerItems extends AbstractCreationItem {
    private Image image;

    public FlowerItems(Image image, int x, int y) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, null);
    }

    @Override
    public int getWidth() { return image.getWidth(null); }

    @Override
    public int getHeight() { return image.getHeight(null); }

    @Override
    protected Image getImage() { return image; }

    @Override
    public void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight) {
        int w = getWidth();
        int h = getHeight();
        double scale = Math.min((double)maxWidth / w, (double)maxHeight / h);
        int newW = (int)(w * scale);
        int newH = (int)(h * scale);
        g2d.drawImage(image, x, y, newW, newH, null);
    }
}
