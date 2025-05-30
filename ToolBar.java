import javax.swing.*;
import java.awt.*;

public class ToolBar {
    private JToolBar toolBar;

    public ToolBar(FileButtonHandler fileListener, AddButtonHandler addListener, DesignButtonHandler designListener, PenButtonHandler penListener, EraserButtonHandler eraserListener, RefreshButtonHandler refreshListener) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(173, 216, 230));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

        addLeftSideButtons(fileListener, addListener, designListener);
        toolBar.add(Box.createHorizontalGlue());
        addRightSideButtons(penListener, eraserListener, refreshListener);
    }

    private void addLeftSideButtons(FileButtonHandler fileListener, AddButtonHandler addListener, DesignButtonHandler designListener) {
        toolBar.add(createFileButton(fileListener));
        toolBar.add(createAddButton(addListener));
        toolBar.add(createDesignButton(designListener));
        toolBar.add(createResizeButton());
    }

    private void addRightSideButtons(PenButtonHandler penListener, EraserButtonHandler eraserListener, RefreshButtonHandler refreshListener) {
        toolBar.add(createPenButton(penListener));
        toolBar.add(createEraserButton(eraserListener));
        toolBar.add(createRefreshButton(refreshListener));
    }

        private JButton createFileButton(FileButtonHandler listener) {
        JButton button = createIconButton("images/file.png", "File");
        button.addActionListener(e -> listener.openFileDialog());
        return button;
    }

    private JButton createAddButton(AddButtonHandler listener) {
        JButton button = createIconButton("images/add.png", "Add");
        button.addActionListener(e -> listener.openAddDialog());
        return button;
    }

    private JButton createDesignButton(DesignButtonHandler listener) {
        JButton button = createIconButton("images/design.png", "Design");
        button.addActionListener(e -> listener.openDesignLibrary());
        return button;
    }

    private JButton createResizeButton() {
        return createIconButton("images/resize.png", "Resize");
        // Add listener logic when implemented
    }

    private JButton createPenButton(PenButtonHandler listener) {
        JButton button = createIconButton("images/pen.png", "Pen");
        button.addActionListener(e -> listener.openPenDialog());
        return button;
    }

    private JButton createEraserButton(EraserButtonHandler listener) {
        JButton button = createIconButton("images/eraser.png", "Eraser");
        button.addActionListener(e -> listener.activeEraser(button));
        return button;
    }

    private JButton createRefreshButton(RefreshButtonHandler listener) {
        JButton button = createIconButton("images/refresh.png", "Refresh");
        button.addActionListener(e -> listener.handlerRefresh());
        return button;
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