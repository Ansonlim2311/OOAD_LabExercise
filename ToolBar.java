import javax.swing.*;
import java.awt.*;

public class ToolBar {
    private JToolBar toolBar;

    public ToolBar(FileButtonHandler fileListener, PenButtonHandler penListener, EraserButtonHandler eraserListener) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(173, 216, 230));

        JButton fileButton = createIconButton("images/file.png", "File");
        fileButton.addActionListener(e -> {
            fileListener.openFileDialog();
            System.out.println("File button clicked");
        });

        JButton designButton = createIconButton("images/design.png", "Design");
        designButton.addActionListener(e -> {
            System.out.println("Design button clicked");
        });

        JButton resizeButton = createIconButton("images/resize.png", "Resize");
        resizeButton.addActionListener(e -> {
            System.out.println("Resize button clicked");
        });

        JButton penButton = createIconButton("images/pen.png", "Pen");
        penButton.addActionListener(e -> {
            penListener.openPenDialog();
            System.out.println("Pen button clicked");
        });

        JButton eraserButton = createIconButton("images/eraser.png", "Eraser");
        eraserButton.addActionListener(e -> {
            eraserListener.activeEraser(eraserButton);
        });

        toolBar.add(fileButton);
        toolBar.add(designButton);
        toolBar.add(resizeButton);
        toolBar.add(penButton);
        toolBar.add(eraserButton);
    }

    private JButton createIconButton(String iconPath, String toolTip) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setToolTipText(toolTip);
        button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        button.setContentAreaFilled(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
