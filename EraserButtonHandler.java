import javax.swing.*;
import java.awt.*;

public class EraserButtonHandler {
    private boolean eraserActive = false;

    public void activeEraser(JButton button) {
        eraserActive = !eraserActive;
        if (eraserActive) {
            button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        }
    }

    public boolean eraserActive() {
        return eraserActive;
    }
}