import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class FileButtonHandler {
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private RightCanvasPanel rightCanvas;
    private String canvasSelection,formatSelection;
    private JFileChooser folderChooser = new JFileChooser("library");
    private int result;
    private File folder, outputFile;
    private String filename;
    private BufferedImage imageToBeSaved;

    public FileButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas, RightCanvasPanel rightCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
        this.rightCanvas = rightCanvas;
    }

    public void openFileDialog() {
        showSaveDialog();
    }

    private void showSaveDialog() {
        String[] options = {"Left Canvas", "Right Canvas", "Both"};
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
            if(canvasSelection.equals("Left Canvas")) {
                imageToBeSaved = leftCanvas.getComposedImage();
            }
            else if (canvasSelection.equals("Right Canvas")) {
                if (formatSelection == "PNG") {
                    imageToBeSaved = rightCanvas.getPNGCanvasImage();
                }
                else if (formatSelection == "JPG") {
                    imageToBeSaved = rightCanvas.getJPGCanvasImage();
                }
            }

            if (imageToBeSaved != null) {
                outputFile = new File(folder, filename + "." + formatSelection.toLowerCase());
                ImageIO.write(imageToBeSaved, formatSelection.toLowerCase(), outputFile);
                JOptionPane.showMessageDialog(parentComponent, "Image Saved To:\n" + outputFile.getAbsolutePath());
                saveChange();
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
