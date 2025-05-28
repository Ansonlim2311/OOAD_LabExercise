// import javax.imageio.ImageIO;
// import javax.swing.*;
// import java.awt.image.BufferedImage;
// import java.awt.datatransfer.DataFlavor;
// import java.io.File;
// import java.io.IOException;
// import java.util.List;

// public class ImageDropHandler extends TransferHandler {
    
//     public boolean canImport(TransferSupport support) {
//         return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
//     }

//     @Override
//     public boolean importData(TransferSupport support) {
//         if (canImport(support) == false) {
//             return false;
//         }

//         try {
//             List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
//             for (int i = 0; i < files.size(); i++) {
//                 File file = files.get(i);
//                 String name = file.getName().toLowerCase();

//                 if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
//                     BufferedImage img = ImageIO.read(file);
//                     if (img != null) {
//                         LeftCanvasPanel leftCanvas = (LeftCanvasPanel) support.getComponent();

//                         if (leftCanvas.getSubCanvas() == null) {
//                             JOptionPane.showMessageDialog(leftCanvas, "Please create a subcanvas first!");
//                             return false;
//                         }

//                         // Get image width and height
//                         int imgW = img.getWidth();
//                         int imgH = img.getHeight();

//                         // Calculate center position for subcanvas
//                         int centerX = (leftCanvas.getSubCanvasWidth() - imgW) / 2;
//                         int centerY = (leftCanvas.getSubCanvasHeight() - imgH) / 2;

//                         // Create a CustomImage item and set position
//                         CreationItem item = new CustomImage(img, centerX, centerY);

//                         // Add it to the subcanvas
//                         leftCanvas.addImageToSubCanvas(item);
//                     }
//                 }
//             }
//             return true;

//         } catch (IOException error) {
//             error.printStackTrace();
//             return false;
//         }
//     }
// }