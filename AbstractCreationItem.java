import java.awt.*;

abstract class AbstractCreationItem implements CreationItem {
    protected int x, y;

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

    public abstract void draw(Graphics2D g2d);
    public abstract int getWidth();
    public abstract int getHeight();

    public void drawScaled(Graphics2D g2d, int maxWidth, int maxHeight) {
        int w = getWidth();
        int h = getHeight();
        double scale = Math.min((double)maxWidth / w, (double)maxHeight / h);
        int newW = (int)(w * scale);
        int newH = (int)(h * scale);
        g2d.drawImage(getImage(), x, y, newW, newH, null);
    }

    protected abstract Image getImage();
}