import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DesignButtonHandler {
    
    private Component parentComponent;
    private LeftCanvasPanel leftCanvas;
    private JFileChooser folderChooser, imageChooser;
    private File libraryDir = new File("library");
    private int result, imageResult, canvasWidth, canvasHeight, x, y;
    private File selectedFolder, selectedImageFile;
    private String folderName;
    private CreationItem newCreation;

    public DesignButtonHandler(Component parentComponent, LeftCanvasPanel leftCanvas) {
        this.parentComponent = parentComponent;
        this.leftCanvas = leftCanvas;
    }

    public void openDesignLibrary() {
        if (libraryDir.exists() == false) {
            libraryDir.mkdirs();
        }

        folderChooser = new JFileChooser(libraryDir);
        folderChooser.setDialogTitle("Select or Create a Folder in Library");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("Select Folder");

        result = folderChooser.showOpenDialog(parentComponent);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

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
            BufferedImage image = ImageIO.read(selectedImageFile);
            if (image == null) {
                JOptionPane.showMessageDialog(parentComponent, "Faild To Load Image.");
                return;
            }

            if (leftCanvas.getSubCanvas() == null) {
                JOptionPane.showMessageDialog(parentComponent, "Please create a canvas first using the Add button.");
                return;
            }

            if (folderName.equals("animal")) {
                newCreation = new AnimalItems(image, 10, 10);
            } else if (folderName.equals("flower")) {
                newCreation = new FlowerItems(image, 10, 10);
            }

            canvasWidth = leftCanvas.getSubCanvasWidth();
            canvasHeight = leftCanvas.getSubCanvasHeight();

            x = (canvasWidth - newCreation.getWidth()) / 2;
            y = (canvasHeight - newCreation.getHeight()) / 2;

            newCreation.setPosition(x, y);
            leftCanvas.addImageToSubCanvas(newCreation);
            
        } catch (IOException error) {
            JOptionPane.showMessageDialog(parentComponent, "Error loading image: " + error.getMessage());
        }
    }
}