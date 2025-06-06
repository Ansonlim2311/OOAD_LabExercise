import javax.swing.*;
import java.awt.*;

// AddButtonHandler class to handle adding new sub canvases
public class AddButtonHandler {
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private String widthStr, heightStr;
    private int width, height;

    // constructor to initialize parent component and left canvas
    AddButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
    }

    // create sub canvas with user specific width and height
    public void openAddDialog() {
        widthStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas width:", "400");
        if (widthStr == null) {
            return;
        }
        heightStr = JOptionPane.showInputDialog(parentComponent, "Enter canvas height:", "400");
        if (heightStr == null) {
            return;
        }
        // Validate the input and create a new sub canvas
        try {
            width = Integer.parseInt(widthStr);
            height = Integer.parseInt(heightStr);

            if (width <= 0 || height <= 0) {
                JOptionPane.showMessageDialog(parentComponent, "Width and height must be positive numbers.");
                return;
            }
            // Create a new sub canvas with the specified width and height
            leftCanvas.newSubCanvas(width, height);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(parentComponent, "Invalid input! Please enter valid numbers.");
        }
    }
}
