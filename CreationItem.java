import java.awt.Graphics2D;

public interface CreationItem {
    void draw(Graphics2D g2d);
    int getWidth();
    int getHeight();
    int getX();
    int getY();
    void setPosition(int x, int y);
    void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight);
    void setFlippedHorizontally(boolean flipped);
    void setFlippedVertically(boolean flipped);
    boolean isFlippedHorizontally();
    boolean isFlippedVertically();
    void setScale(double scale);
    double getScale();
}
