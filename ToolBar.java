import javax.swing.*;
import java.awt.*;

// TooBar class create a custom toolbar with various buttons with specific functionalities
public class ToolBar {
    private JToolBar toolBar;

    // constructor to initialize the toolbar with various buttons
    public ToolBar(FileButtonHandler fileListener, AddButtonHandler addListener, DesignButtonHandler designListener, PenButtonHandler penListener, 
                    ColourButtonHandler colourListener, EraserButtonHandler eraserListener, RefreshButtonHandler refreshListener) {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(173, 216, 230));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.X_AXIS));

        addLeftSideButtons(fileListener, addListener, designListener);
        toolBar.add(Box.createHorizontalGlue());
        addRightSideButtons(penListener, colourListener, eraserListener, refreshListener);
    }

    // Method to add buttons to the left side of the toolbar
    private void addLeftSideButtons(FileButtonHandler fileListener, AddButtonHandler addListener, DesignButtonHandler designListener) {
        toolBar.add(createFileButton(fileListener));
        toolBar.add(createAddButton(addListener));
        toolBar.add(createDesignButton(designListener));
    }

    // Method to add buttons to the right side of the toolbar
    private void addRightSideButtons(PenButtonHandler penListener, ColourButtonHandler colourListener, 
                                    EraserButtonHandler eraserListener, RefreshButtonHandler refreshListener) {
        toolBar.add(createPenButton(penListener));
        toolBar.add(createColourButton(colourListener));
        toolBar.add(createEraserButton(eraserListener));
        toolBar.add(createRefreshButton(refreshListener));
    }

    // create a file button with an icon and action listener
    private JButton createFileButton(FileButtonHandler listener) {
        JButton button = createIconButton("images/file.png", "File");
        button.addActionListener(e -> listener.openFileDialog());
        return button;
    }

    // create an add button with an icon and action listener
    private JButton createAddButton(AddButtonHandler listener) {
        JButton button = createIconButton("images/add.png", "Add");
        button.addActionListener(e -> listener.openAddDialog());
        return button;
    }

    // create a design button with an icon and action listener
    private JButton createDesignButton(DesignButtonHandler listener) {
        JButton button = createIconButton("images/design.png", "Design");
        button.addActionListener(e -> listener.openDesignLibrary());
        return button;
    }

    // create a pen button with an icon and action listener
    private JButton createPenButton(PenButtonHandler listener) {
        JButton button = createIconButton("images/pen.png", "Pen");
        button.addActionListener(e -> listener.openPenDialog());
        return button;
    }

    private JButton createColourButton(ColourButtonHandler listener) {
        JButton button = createIconButton("images/colourPalette.png", "Colour");
        button.addActionListener(e -> listener.openColourDialog());
        return button;
    }

    // create an eraser button with an icon and action listener
    private JButton createEraserButton(EraserButtonHandler listener) {
        JButton button = createIconButton("images/eraser.png", "Eraser");
        button.addActionListener(e -> listener.activeEraser(button));
        return button;
    }

    // create a refresh button with an icon and action listener
    private JButton createRefreshButton(RefreshButtonHandler listener) {
        JButton button = createIconButton("images/refresh.png", "Refresh");
        button.addActionListener(e -> listener.handlerRefresh());
        return button;
    }

    // function to create a button with an icon and tooltip
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

    // method to get the toolbar
    public JToolBar getToolBar() {
        return toolBar;
    }
}