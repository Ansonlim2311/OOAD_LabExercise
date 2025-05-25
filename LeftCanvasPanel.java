import java.awt.*;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    public LeftCanvasPanel() {
        setPreferredSize(new Dimension(400, 600)); // Set size of the left canvas
        setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // You can draw placeholder images here for now
        g.drawString("Left Canvas (Image Composition)", 10, 20);
    }
}