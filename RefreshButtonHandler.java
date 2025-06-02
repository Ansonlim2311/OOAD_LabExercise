import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class RefreshButtonHandler { 
    private RightCanvasPanel rightCanvas;
    private Component parentComponent;
    private int choice, result;
    private String formatSelection,filename;
    private JFileChooser folderChooser = new JFileChooser("library");
    private File folder, outputFile;
    private BufferedImage imageToBeSaved;
    private Boolean saved;

    public RefreshButtonHandler(Component parentComponent, RightCanvasPanel rightCanvas) {
        this.parentComponent = parentComponent;
        this.rightCanvas = rightCanvas;
    }

    public void handlerRefresh() {
        if (rightCanvas.isEmpty() == true) {
            JOptionPane.showMessageDialog(parentComponent, "Right Canvas Is Already Empty");
            return;
        }
        if (rightCanvas.hasUnsavedChanges() == false) {
            rightCanvas.clearCanvas();
            return;
        }

        choice = JOptionPane.showConfirmDialog(parentComponent, "Do you want to save your drawing before refreshing?", "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }
        if (choice == JOptionPane.YES_OPTION) {
            saved = saveChange();
            saveRightCanvas();
            if (saved) {
                rightCanvas.clearCanvas();
            }
        } else {
            rightCanvas.clearCanvas();
            JOptionPane.showMessageDialog(parentComponent, "Right canvas has been refreshed.");
        }
    }

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
            if (formatSelection == "PNG") {
                imageToBeSaved = rightCanvas.getPNGCanvasImage();
            } else if (formatSelection == "JPG") {
                imageToBeSaved = rightCanvas.getJPGCanvasImage();
            }

            if (imageToBeSaved != null) {
                outputFile = new File(folder, filename + "." + formatSelection.toLowerCase());
                ImageIO.write(imageToBeSaved, formatSelection.toLowerCase(), outputFile);
                JOptionPane.showMessageDialog(parentComponent, "Image Saved To:\n" + outputFile.getAbsolutePath());
            }
        } catch (IOException error) {
            JOptionPane.showMessageDialog(parentComponent, "Image Saved Failed:\n" + error.getMessage());
        }
    }

    private boolean saveChange() {
        rightCanvas.setUnsavedChange();
        return true;
    }
}
