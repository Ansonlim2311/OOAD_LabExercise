import javax.swing.*;
import java.awt.*;

public class PenButtonDialog extends JDialog {
    public static Color penColor = new Color(0, 0, 0);
    public static int pen = 4;
    private JSlider penSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, pen);
    private JButton colourButton = new JButton("Change Color");

    public PenButtonDialog(JFrame parentFrame) {
        super(parentFrame, "Pen Tool", false);

        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(parentFrame);

        penSizeSlider.setMajorTickSpacing(5);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.addChangeListener(e -> {
            pen = penSizeSlider.getValue();
        });

        colourButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Pen Color", penColor);
            if (newColor != null) {
                penColor = newColor;
            }
        });

        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(penSizeSlider);
        panel.add(colourButton);
        add(panel, BorderLayout.CENTER);
    }
}
