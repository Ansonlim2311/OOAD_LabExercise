import javax.swing.*;
import java.awt.*;

public class ColourButtonHandler {
    private JFrame parentFrame;
    private JDialog paletteDialog;
    private JColorChooser colourChooser;
    private Color penColor = new Color(0, 0, 0);
    private Color newColor;

    public ColourButtonHandler(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void openColourDialog() {
        paletteDialog = new JDialog(parentFrame, "Pen COlour", false);
        paletteDialog.setLayout(new BorderLayout(10, 10));

        // set up colour chooser
        colourChooser = new JColorChooser(penColor);
        colourChooser.getSelectionModel().addChangeListener(e -> {
            newColor = colourChooser.getColor();
            if (newColor != null) {
                penColor = newColor;
            }
        });

        // add components to the dialog
        paletteDialog.add(colourChooser, BorderLayout.NORTH);

        paletteDialog.pack();
        paletteDialog.setLocationRelativeTo(parentFrame);
        paletteDialog.setVisible(true);
    }

    // return current pen color
    public Color getPenColor() {
        return penColor;
    }
}