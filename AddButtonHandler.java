import javax.swing.*;
import java.awt.*;

public class AddButtonHandler {
    
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;

    AddButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
    }

    public void openAddDialog() {
        String widthStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas width:", "400");
        String heightStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas height:", "400");

        try {
            int width = Integer.parseInt(widthStr);
            int height = Integer.parseInt(heightStr);

            if (width <= 0 || height <= 0) {
                JOptionPane.showMessageDialog(parentComponent, "Width and height must be positive numbers.");
                return;
            }

            leftCanvas.newSubCanvas(width, height);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentComponent, "Invalid input! Please enter valid numbers.");
        }
    }
}
