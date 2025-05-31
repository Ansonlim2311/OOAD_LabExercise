import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> oldItems = new ArrayList<>();
    private LeftSubCanvas newSubCanvas, subCanvas;
    private CreationItem item;

    public LeftCanvasPanel() {
        setTransferHandler(new ImageDropHandler());
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);
    }

    public void newSubCanvas(int width, int height) {
        if (subCanvas == null) {
            subCanvas = new LeftSubCanvas(width, height);
        } else {
            newSubCanvas = new LeftSubCanvas(width, height);
            oldItems = subCanvas.getItems();
            for (int i = 0; i < oldItems.size(); i++) {
                item = oldItems.get(i);
                newSubCanvas.addItem(item);
            }
            subCanvas = newSubCanvas;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        if (subCanvas != null) {
            subCanvas.draw(g2d, getWidth(), getHeight());
        } 
        g.setColor(Color.BLACK);
        g.drawString("Left Canvas (Image Composition)", 10, 20);
        g.dispose();
    }

    public boolean hasSubCanvas() {
        return subCanvas != null;
    }

    public LeftSubCanvas getSubCanvas() {
        return subCanvas;
    }
}