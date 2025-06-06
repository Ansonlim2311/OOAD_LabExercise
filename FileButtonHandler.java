import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// FileButtonHandler class handler saving functionalities for the left and right canvas
// It allows users to select a canvas, choose a file format, and save the canvas as an image file
public class FileButtonHandler {
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private LeftSubCanvas subCanvas;
    private RightCanvasPanel rightCanvas;
    private String canvasSelection,formatSelection;
    private JFileChooser folderChooser = new JFileChooser("library");
    private int result;
    private File folder, outputFile;
    private String filename;
    private BufferedImage imageToBeSaved;

    // Constructor to initialize the parent component and left and right canvas panels
    public FileButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas, RightCanvasPanel rightCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
        this.rightCanvas = rightCanvas;
    }

    // Method to open a file dialog for saving the selected canvas
    // Ask the user to select which canvas to save, the file format, and the folder to save the file
    public void openFileDialog() {
        String[] options = {"Left Canvas", "Right Canvas"};
        canvasSelection = (String) JOptionPane.showInputDialog(
            parentComponent,
            "Which canvas would you like to save?",
           "Canvas Selection",
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );
        if (canvasSelection == null) {
            return;
        }

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
            if (canvasSelection.equals("Left Canvas")) {
                subCanvas = leftCanvas.getSubCanvas();
                if ("PNG".equals(formatSelection)) {
                    imageToBeSaved = subCanvas.getPNGCanvasImage();
                }
                else if ("JPG".equals(formatSelection)) {
                    imageToBeSaved = subCanvas.getJPGCanvasImage();
                }
            }
            else if (canvasSelection.equals("Right Canvas")) {
                if ("PNG".equals(formatSelection)) {
                    imageToBeSaved = rightCanvas.getPNGCanvasImage();
                }
                else if ("JPG".equals(formatSelection)) {
                    imageToBeSaved = rightCanvas.getJPGCanvasImage();
                }
                saveChange();
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
    
    // Method to mark right canvas as having no unsaved changes
    private boolean saveChange() {
        rightCanvas.setUnsavedChange();
        return true;
    }
}
