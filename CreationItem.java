import java.awt.Graphics2D;

public interface CreationItem {
    void draw(Graphics2D g2d);
    int getWidth();
    int getHeight();
    int getX();
    int getY();
    void setPosition(int x, int y);
    void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight);
}
