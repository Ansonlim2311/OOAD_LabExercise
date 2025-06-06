import java.awt.Graphics2D;

// CreationItem interface defines the methods that any creation item must implement.
// include setter and getter methods for position, flipping, scaling, and rotation,
// as well as a method to draw the item on a Graphics2D context.
public interface CreationItem {
    void draw(Graphics2D g2d);
    int getWidth();
    int getHeight();
    int getX();
    int getY();
    void setPosition(int x, int y);
    void setFlippedHorizontally(boolean flipped);
    void setFlippedVertically(boolean flipped);
    boolean isFlippedHorizontally();
    boolean isFlippedVertically();
    void setScale(double scale);
    double getScale();
    void setRotation(double angle);
    double getRotation();
}
