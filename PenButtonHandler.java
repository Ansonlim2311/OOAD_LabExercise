import javax.swing.*;
import java.awt.*;

public class PenButtonHandler{
    private JFrame parentFrame;
    private JDialog paletteDialog;
    private int penSize = 4;
    private JSlider penSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, penSize);

    public PenButtonHandler(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // open a pen dialog to allow user to change pen color and size
    public void openPenDialog() {
        paletteDialog = new JDialog(parentFrame, "Pen Size", false);
        paletteDialog.setLayout(new BorderLayout(10, 10));

        // set up pen size slider
        penSizeSlider.setMajorTickSpacing(5);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.addChangeListener(e -> {
            penSize = penSizeSlider.getValue();
        });

        paletteDialog.add(penSizeSlider, BorderLayout.CENTER);

        paletteDialog.pack();
        paletteDialog.setLocationRelativeTo(parentFrame);
        paletteDialog.setVisible(true);
    }

    // return current pen size
    public int getPenSize() {
        return penSize;
    }
}
