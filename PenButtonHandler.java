import javax.swing.*;
import java.awt.*;

public class PenButtonHandler{
    private JFrame parentFrame;
    private JDialog paletteDialog;
    private JColorChooser colourChooser;
    private Color penColor = new Color(0, 0, 0);
    private int penSize = 4;
    private Color newColor;
    private JSlider penSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, penSize);

    public PenButtonHandler(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    // open a pen dialog to allow user to change pen color and size
    public void openPenDialog() {
        paletteDialog = new JDialog(parentFrame, "Pen Setting", false);
        paletteDialog.setLayout(new BorderLayout(10, 10));

        // set up colour chooser
        colourChooser = new JColorChooser(penColor);
        colourChooser.getSelectionModel().addChangeListener(e -> {
            newColor = colourChooser.getColor();
            if (newColor != null) {
                penColor = newColor;
            }
        });

        // set up pen size slider
        penSizeSlider.setMajorTickSpacing(5);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.addChangeListener(e -> {
            penSize = penSizeSlider.getValue();
        });

        // add components to the dialog
        paletteDialog.add(colourChooser, BorderLayout.NORTH);
        paletteDialog.add(penSizeSlider, BorderLayout.CENTER);

        paletteDialog.pack();
        paletteDialog.setLocationRelativeTo(parentFrame);
        paletteDialog.setVisible(true);
    }

    // return current pen color
    public Color getPenColor() {
        return penColor;
    }

    // return current pen size
    public int getPenSize() {
        return penSize;
    }
}
