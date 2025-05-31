import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.awt.datatransfer.UnsupportedFlavorException;

public class ImageDropHandler extends TransferHandler {
    private String fileName;
    private Object fileObject;
    private File file;
    private BufferedImage image;
    private LeftCanvasPanel leftCanvas;
    private LeftSubCanvas subCanvas;
    private int centerX, centerY, absoluteX, absoluteY;
    private CreationItem item;

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
                        leftCanvas = (LeftCanvasPanel) support.getComponent();
                        subCanvas = leftCanvas.getSubCanvas();

                        if (leftCanvas.hasSubCanvas() == false) {
                            JOptionPane.showMessageDialog(leftCanvas, "Please create a subcanvas first!");
                            return false;
                        }

                        centerX = (subCanvas.getSubCanvasWidth() - image.getWidth()) / 2;
                        centerY = (subCanvas.getSubCanvasHeight() - image.getHeight()) / 2;

                        absoluteX = subCanvas.getSubCanvasX() + centerX;
                        absoluteY = subCanvas.getSubCanvasY() + centerY;

                        item = new CustomImage(image, absoluteX, absoluteY);
                        // leftCanvas.addImageToSubCanvas(item);
                        subCanvas.addItem(item);
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