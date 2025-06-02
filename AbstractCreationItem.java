import java.awt.*;
import java.awt.image.BufferedImage;

abstract class AbstractCreationItem implements CreationItem {
    protected int x, y, imageWidth, imageHeight;
    protected int scaleX, scaleY;
    protected boolean flippedHorizontally = false;
    protected boolean flippedVertically = false;
    protected Graphics2D g2d;
    protected double scale = 1.0, rotation = 0.0;
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

    public void setRotation(double angle) {
        this.rotation = angle % 360;
    }

    public double getRotation() {
        return rotation;
    }

    public abstract void draw(Graphics2D g2d);
    public int getWidth() {
        return (int) (image.getWidth() * scale);
    }
    public int getHeight() {
        return (int) (image.getHeight() * scale);
    }

    protected void drawFlippedImage(Graphics2D g, BufferedImage image) {
        imageWidth = (int)(image.getWidth() * scale);
        imageHeight = (int)(image.getHeight() * scale);
        scaleX = 1;
        scaleY = 1;

        if (flippedHorizontally == true) {
            scaleX = -1;
        }

        if (flippedVertically == true) {
            scaleY = -1;
        }

        g2d = (Graphics2D) g.create();
        g2d.translate(x + imageWidth / 2, y + imageHeight / 2);
        g2d.rotate(Math.toRadians(rotation));
        g2d.scale(scaleX * scale, scaleY * scale);
        g2d.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, null);
        g2d.dispose();
    }

    protected abstract Image getImage();
}