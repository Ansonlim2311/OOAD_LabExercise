import javax.swing.*;
import java.awt.*;

public class EraserButtonHandler {
    private boolean eraserActive = false;

    // toggle eraser active state and change button border
    public void activeEraser(JButton button) {
        eraserActive = !eraserActive;
        if (eraserActive) {
            button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        }
    }

    // check if eraser is active or not
    public boolean eraserActive() {
        return eraserActive;
    }
}