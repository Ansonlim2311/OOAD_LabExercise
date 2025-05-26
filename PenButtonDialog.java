import javax.swing.*;
import java.awt.*;

public class PenButtonDialog extends JDialog {
    public static Color penColor = new Color(0, 0, 0);
    public static int pen = 4;
    public static boolean eraserActive = false;
    private JSlider penSizeSlider = new JSlider(JSlider.HORIZONTAL, 1, 30, pen);
    private JButton penSettingButton;

    public PenButtonDialog(JFrame parentFrame) {
        super(parentFrame, "Pen Tool", false);
        JToolBar penToolBar = new JToolBar();
        penToolBar.setFloatable(false);

        setSize(80, 100);
        setLocationRelativeTo(parentFrame);

        penSettingButton = createIconButton("images/colourPalette.png", "Pen Setting");
        penSettingButton.addActionListener(e -> showPenSettingPopup());
        penToolBar.add(penSettingButton);

        add(penToolBar, BorderLayout.CENTER);
    }

    private void showPenSettingPopup() {
        JDialog paletteDialog = new JDialog(this, "Pen Setting", false);
        paletteDialog.setLayout(new BorderLayout(10, 10));

        JColorChooser colourChooser = new JColorChooser(penColor);
        colourChooser.getSelectionModel().addChangeListener(e -> {
            Color newColor = colourChooser.getColor();
            if (newColor != null) {
                penColor = newColor;
            }
        });

        penSizeSlider.setMajorTickSpacing(5);
        penSizeSlider.setPaintTicks(true);
        penSizeSlider.setPaintLabels(true);
        penSizeSlider.addChangeListener(e -> {
            pen = penSizeSlider.getValue();
        });

        paletteDialog.add(colourChooser, BorderLayout.NORTH);
        paletteDialog.add(penSizeSlider, BorderLayout.CENTER);

        paletteDialog.pack();
        paletteDialog.setLocationRelativeTo(this);
        paletteDialog.setVisible(true);
    }

    private JButton createIconButton(String iconPath, String toolTip) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setToolTipText(toolTip);
        button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}
