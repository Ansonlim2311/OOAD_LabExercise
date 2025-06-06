import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// RefreshButtonHandler class handles the refresh functionality for the right canvas
// It prompts the user to save changes if there are unsaved changes in the right canvas
public class RefreshButtonHandler { 
    private RightCanvasPanel rightCanvas;
    private Component parentComponent;
    private int choice, result;
    private String formatSelection,filename;
    private JFileChooser folderChooser = new JFileChooser("library");
    private File folder, outputFile;
    private BufferedImage imageToBeSaved;

    // Constructor to initialize the parent component and right canvas panel
    public RefreshButtonHandler(Component parentComponent, RightCanvasPanel rightCanvas) {
        this.parentComponent = parentComponent;
        this.rightCanvas = rightCanvas;
    }

    // Method to handle the refresh action for the right canvas
    public void handlerRefresh() {
        // if canvas is empty, show a message and return
        if (rightCanvas.isEmpty() == true) {
            JOptionPane.showMessageDialog(parentComponent, "Right Canvas Is Already Empty");
            return;
        }
        // if there are no unsaved changes, clear the canvas
        if (rightCanvas.hasUnsavedChanges() == false) {
            rightCanvas.clearCanvas();
            return;
        }

        choice = JOptionPane.showConfirmDialog(parentComponent, "Do you want to save your drawing before refreshing?", 
                                                                        "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }
        // if user chooses to save, call saveChange() to update the unSavedChanges Status and saveRightCanvas()
        if (choice == JOptionPane.YES_OPTION) {
            saveRightCanvas();
        } else {
            // if user chooses not to save, just clear the canvas
            rightCanvas.clearCanvas();
            JOptionPane.showMessageDialog(parentComponent, "Right canvas has been refreshed.");
        }
    }

    // Method to save the right canvas as an image file
    private void saveRightCanvas() {
        String[] format = {"JPG", "PNG"};
        formatSelection = (String) JOptionPane.showInputDialog(
            parentComponent,
            "Which File Format Would You Like To Save?",
            "Format Selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            format,
            format[0]
        );
        if (formatSelection == null) {
            return;
        }

        folderChooser.setDialogTitle("Choose Folder To Save Your File");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("Select");

        result = folderChooser.showSaveDialog(parentComponent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        folder = folderChooser.getSelectedFile();
        filename = JOptionPane.showInputDialog(parentComponent, "Enter Your Filename");
        if (filename == null || filename.trim().isEmpty()) {
            return;
        }

        try {
            imageToBeSaved = null;
            if ("PNG".equals(formatSelection)) {
                imageToBeSaved = rightCanvas.getPNGCanvasImage();
            } else if ("JPG".equals(formatSelection)) {
                imageToBeSaved = rightCanvas.getJPGCanvasImage();
            }

            if (imageToBeSaved != null) {
                outputFile = new File(folder, filename + "." + formatSelection.toLowerCase());
                ImageIO.write(imageToBeSaved, formatSelection.toLowerCase(), outputFile);
                JOptionPane.showMessageDialog(parentComponent, "Image Saved To:\n" + outputFile.getAbsolutePath());
                rightCanvas.clearCanvas();
                saveChange();
            }
        } catch (IOException error) {
            JOptionPane.showMessageDialog(parentComponent, "Image Saved Failed:\n" + error.getMessage());
        }
    }

    // Method to mark the right canvas as having no unsaved changes
    private boolean saveChange() {
        rightCanvas.setUnsavedChange();
        return true;
    }
}