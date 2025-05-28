import javax.swing.*;
import java.awt.*;

public class ToolBar {
    private JToolBar toolBar;

    public ToolBar(FileButtonHandler fileListener, AddButtonHandler addListener, DesignButtonHandler designListener, PenButtonHandler penListener, EraserButtonHandler eraserListener, RefreshButtonHandler refreshListener) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(173, 216, 230));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

        JButton fileButton = createIconButton("images/file.png", "File");
        fileButton.addActionListener(e -> {
            fileListener.openFileDialog();
        });

        JButton addButton = createIconButton("images/add.png", "Add");
        addButton.addActionListener(e -> {
            addListener.openAddDialog();
            System.out.println("add button clicked");
        });

        JButton designButton = createIconButton("images/design.png", "Design");
        designButton.addActionListener(e -> {
            designListener.openDesignLibrary();
        });

        JButton resizeButton = createIconButton("images/resize.png", "Resize");
        resizeButton.addActionListener(e -> {
            System.out.println("Resize button clicked");
        });

        toolBar.add(fileButton);
        toolBar.add(addButton);
        toolBar.add(designButton);
        toolBar.add(resizeButton);

        toolBar.add(Box.createHorizontalGlue());

        JButton penButton = createIconButton("images/pen.png", "Pen");
        penButton.addActionListener(e -> {
            penListener.openPenDialog();
        });

        JButton eraserButton = createIconButton("images/eraser.png", "Eraser");
        eraserButton.addActionListener(e -> {
            eraserListener.activeEraser(eraserButton);
        });

        JButton refreshButton = createIconButton("images/refresh.png", "Refresh");
        refreshButton.addActionListener(e -> {
            refreshListener.handlerRefresh();
        });

        toolBar.add(penButton);
        toolBar.add(eraserButton);
        toolBar.add(refreshButton);
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
