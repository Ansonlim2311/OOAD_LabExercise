import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// DesignButtonHandler class to handle design library operations
// which allows users to select a folder and an image from the library
// and add it to the left canvas
public class DesignButtonHandler {
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private LeftSubCanvas subCanvas;
    private JFileChooser folderChooser, imageChooser;
    private File libraryDir = new File("library");
    private int result, imageResult, x, y;
    private File selectedFolder, selectedImageFile;
    private String folderName;
    private CreationItem newCreation;
    private BufferedImage image;

    // Constructor to initialize the parent component and left canvas
    // This is to let the pop up dialog appear in the correct context
    public DesignButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
    }

    // Method to open the design library dialog
    // It allows the user to select a folder and an image from the library
    // and insert the selected image into the left canvas as CreationItem
    public void openDesignLibrary() {
        if (libraryDir.exists() == false) {
            libraryDir.mkdirs();
        }

        // open folder chooser dialog
        folderChooser = new JFileChooser(libraryDir);
        folderChooser.setDialogTitle("Select or Create a Folder in Library");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("Select Folder");

        result = folderChooser.showOpenDialog(parentComponent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        // open image chooser dialog
        selectedFolder = folderChooser.getSelectedFile();
        folderName = selectedFolder.getName().toLowerCase();
        imageChooser = new JFileChooser(selectedFolder);
        imageChooser.setDialogTitle("Select An Image To Insert");
        imageChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));

        imageResult = imageChooser.showOpenDialog(parentComponent);
        if (imageResult != JFileChooser.APPROVE_OPTION) {
            return;
        }

        selectedImageFile = imageChooser.getSelectedFile();

        try {
            image = ImageIO.read(selectedImageFile);
            if (image == null) {
                JOptionPane.showMessageDialog(parentComponent, "Faild To Load Image.");
                return;
            }

            if (leftCanvas.hasSubCanvas() == false) {
                JOptionPane.showMessageDialog(parentComponent, "Please create a canvas first using the Add button.");
                return;
            }

            subCanvas = leftCanvas.getSubCanvas();
            if (subCanvas == null) {
                JOptionPane.showMessageDialog(parentComponent, "No subcanvas found. Please create one first.");
                return;
            }

            // Create a new CreationItem based on the selected folder
            if (folderName.equals("animal")) {
                newCreation = new AnimalItems(image, 10, 10);
            } else if (folderName.equals("flower")) {
                newCreation = new FlowerItems(image, 10, 10);
            } else {
                newCreation = new CustomImage(image, 10, 10);
            }

            // Center the new creation item in the subcanvas
            x = (subCanvas.getSubCanvasWidth() - newCreation.getWidth()) / 2;
            y = (subCanvas.getSubCanvasHeight() - newCreation.getHeight()) / 2;

            // Set the position and add the new creation item to the subcanvas
            newCreation.setPosition(x, y);
            subCanvas.addItem(newCreation);
            leftCanvas.repaint();
            
        } catch (IOException error) {
            JOptionPane.showMessageDialog(parentComponent, "Error loading image: " + error.getMessage());
        }
    }
}