import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.datatransfer.UnsupportedFlavorException;

// ImageDropHandler class handles the drag-and-drop functionality for images
// It allows users to drop image files onto the left canvas and adds them as CreationItems
public class ImageDropHandler extends TransferHandler {
    private String fileName;
    private Object fileObject;
    private File file;
    private BufferedImage image;
    private LeftCanvasPanel leftCanvas;
    private LeftSubCanvas subCanvas;
    private CreationItem item;

    public ImageDropHandler(LeftCanvasPanel leftCanvas) {
        this.leftCanvas = leftCanvas;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (canImport(support) == false) {
            return false;
        }

        try {
            fileObject = support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            if (fileObject instanceof List<?> == false) {
                return false;
            }

            @SuppressWarnings("unchecked")
            List<File> files = (List<File>) fileObject;

            for (int i = 0; i < files.size(); i++) {
                file = files.get(i);
                fileName = file.getName().toLowerCase();

                if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    image = ImageIO.read(file);
                    if (image != null) {
                        subCanvas = leftCanvas.getSubCanvas();

                        if (leftCanvas.hasSubCanvas() == false) {
                            JOptionPane.showMessageDialog(leftCanvas, "Please create a subcanvas first!");
                            return false;
                        }
                        item = new CustomImage(image, 10, 10);
                        subCanvas.addItem(item);
                        leftCanvas.repaint();
                    }
                }
            }
            return true;

        } catch (IOException | UnsupportedFlavorException error) {
            error.printStackTrace();
            return false;
        }
    }
}