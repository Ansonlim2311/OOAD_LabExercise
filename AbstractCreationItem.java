import java.awt.*;
import java.awt.image.BufferedImage;

abstract class AbstractCreationItem implements CreationItem {
    protected int x, y, translateX, translateY;
    protected int scaleX = 1, scaleY = 1;
    protected boolean flippedHorizontally = false;
    protected boolean flippedVertically = false;
    protected Graphics2D g2d;
    protected double scale = 1.0;
    protected BufferedImage image;

    public AbstractCreationItem(int x, int y) {
        this.x = x; 
        this.y = y;
    }

    public int getX() { 
        return x; 
    }

    public int getY() { 
        return y; 
    }
    
    public void setPosition(int x, int y) { 
        this.x = x; 
        this.y = y; 
    }

    public void setFlippedHorizontally(boolean flipped) {
        this.flippedHorizontally = flipped;
    }

    public void setFlippedVertically(boolean flipped) {
        this.flippedVertically = flipped;
    }

    public boolean isFlippedHorizontally() {
        return flippedHorizontally;
    }

    public boolean isFlippedVertically() {
        return flippedVertically;
    }

    public void setScale(double scale) {
        this.scale = Math.max(0.1, Math.min(scale, 10.0));
    }

    public double getScale() {
        return scale;
    }

    public abstract void draw(Graphics2D g2d);
    public int getWidth() {
        return (int) (image.getWidth() * scale);
    }
    public int getHeight() {
        return (int) (image.getHeight() * scale);
    }

    public void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight) {
        int w = getWidth();
        int h = getHeight();
        double scale = Math.min((double)maxWidth / w, (double)maxHeight / h);
        int newW = (int)(w * scale);
        int newH = (int)(h * scale);
        g2d.drawImage(getImage(), x, y, newW, newH, null);
    }

    protected void drawFlippedImage(Graphics2D g, BufferedImage image) {
        int imgW = (int)(image.getWidth() * scale);
        int imgH = (int)(image.getHeight() * scale);
        translateX = x;
        translateY = y;
        scaleX = 1;
        scaleY = 1;

        if (flippedHorizontally == true) {
            translateX = x + imgW;
            scaleX = -1;
        }

        if (flippedVertically == true) {
            translateY = y + imgH;
            scaleY = -1;
        }

        g2d = (Graphics2D) g.create();
        g2d.translate(translateX, translateY);
        g2d.scale(scaleX * scale, scaleY * scale);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    }

    protected abstract Image getImage();
}