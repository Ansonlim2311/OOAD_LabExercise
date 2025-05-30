import javax.swing.*;
import java.awt.*;

public class AddButtonHandler {
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private String widthStr, heightStr;
    private int width, height;

    AddButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
    }

    public void openAddDialog() {
        widthStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas width:", "400");
        if (widthStr == null) {
            return;
        }
        heightStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas height:", "400");
        if (heightStr == null) {
            return;
        }

        try {
            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);

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
