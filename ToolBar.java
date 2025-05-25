import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ToolBar {
    private JToolBar toolBar;

    public ToolBar(FileButtonHandler fileListener, PenButtonHandler penListener) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(173, 216, 230));

        JButton fileButton = createIconButton("images/file.png", "File", e -> {
            fileListener.openFileDialog();
            System.out.println("File button clicked");
        });

        JButton designButton = createIconButton("images/design.png", "Design", e -> {
            System.out.println("Design button clicked");
        });

        JButton resizeButton = createIconButton("images/resize.png", "Resize", e -> {
            System.out.println("Resize button clicked");
            // resizeImage();
        });

        JButton toolsButton = createIconButton("images/pen.png", "Pen", e -> {
            penListener.openPenDialog();
            System.out.println("Pen button clicked");
        });

        toolBar.add(fileButton);
        toolBar.add(designButton);
        toolBar.add(resizeButton);
        toolBar.add(toolsButton);
    }

    private JButton createIconButton(String iconPath, String toolTip, ActionListener actionListener) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setToolTipText(toolTip);
        button.addActionListener(actionListener);
        button.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4)); // small padding
        // button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        // // button.setOpaque(false);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return button;
    }

    public JToolBar getToolBar() {
        return toolBar;
    }
}
