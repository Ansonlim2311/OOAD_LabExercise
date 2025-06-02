import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftSubCanvas extends JPanel implements MouseListener, MouseMotionListener {
    private int width, height, centerX, centerY;
    private int pressMouseX, pressMouseY, dragOffsetX, dragOffsetY;
    private List<CreationItem> itemList = new ArrayList<>();
    private BufferedImage whiteBaseCanvas, outputImage;
    private Graphics2D whiteCanvasGraphics, paintGraphics, imageGraphic;
    private CreationItem currentItem;
    private CreationItem selectedItem = null;
    private int selectedItemX, selectedItemY, selectedItemWidth, selectedItemHeight;
    private int dragStartX, dragStartY, dragX, dragY;
    private int scaleX, scaleY;
    private double scale, scaleFactor;

    public LeftSubCanvas(int width, int height) {
        this.width = width;
        this.height = height;

        whiteBaseCanvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        whiteCanvasGraphics = whiteBaseCanvas.createGraphics();
        whiteCanvasGraphics.setColor(Color.WHITE);
        whiteCanvasGraphics.fillRect(0, 0, width, height);
        whiteCanvasGraphics.dispose();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void addItem(CreationItem item) {
        centerX = (width - item.getWidth()) / 2;
        centerY = (height - item.getHeight()) / 2;
        item.setPosition(centerX, centerY);
        itemList.add(item);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGraphics = (Graphics2D) g.create();

        paintGraphics.drawImage(whiteBaseCanvas, 0, 0, null);

        for(int i = 0; i < itemList.size(); i++) {
            currentItem = itemList.get(i);
            currentItem.draw(paintGraphics);
        }
    }

    public BufferedImage getComposedImage(boolean JPG) {
        if (whiteBaseCanvas == null) {
            JOptionPane.showMessageDialog(null, "No subcanvas found to compose image.");
            return null;
        }

        if (JPG == true) {
            outputImage = new BufferedImage(whiteBaseCanvas.getWidth(), whiteBaseCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);
        } else {
            outputImage = new BufferedImage(whiteBaseCanvas.getWidth(), whiteBaseCanvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        imageGraphic = outputImage.createGraphics();

        if (JPG == true) {
            imageGraphic.setColor(Color.WHITE);
            imageGraphic.fillRect(0, 0, whiteBaseCanvas.getWidth(), whiteBaseCanvas.getHeight());
        }

        for(int i = 0; i < itemList.size(); i++) {
            currentItem = itemList.get(i);
            currentItem.draw(imageGraphic);
        }
        imageGraphic.dispose();
        return outputImage;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressMouseX = e.getX();
        pressMouseY = e.getY();
        dragStartX = pressMouseX;
        dragStartY = pressMouseY;

        for (int i = itemList.size() - 1; i >= 0; i--) {
            currentItem = itemList.get(i);
            selectedItemX = currentItem.getX();
            selectedItemY = currentItem.getY();
            selectedItemWidth = currentItem.getWidth();
            selectedItemHeight = currentItem.getHeight();

            if (pressMouseX >= selectedItemX && pressMouseX <= selectedItemX + selectedItemWidth && pressMouseY >= selectedItemY && pressMouseY <= selectedItemY + selectedItemHeight) {
                selectedItem = currentItem;
                dragOffsetX = pressMouseX - selectedItemX;
                dragOffsetY = pressMouseY - selectedItemY;
                itemList.remove(i);
                itemList.add(currentItem);
                repaint();
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (selectedItem != null && SwingUtilities.isRightMouseButton(e)) {
            dragX = e.getX() - dragStartX;
            dragY = e.getY() - dragStartY;

            if (Math.abs(dragX) > 30 && Math.abs(dragX) > Math.abs(dragY)) {
                selectedItem.setFlippedHorizontally(!selectedItem.isFlippedHorizontally());
            } else {
                selectedItem.setFlippedVertically(!selectedItem.isFlippedVertically());
            }
            repaint();
        }
        selectedItem = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedItem != null && SwingUtilities.isLeftMouseButton(e)) {
            if (e.isShiftDown()) {
                scaleX = e.getX() - dragStartX;
                scaleY = e.getY() - dragStartY;
                scale = Math.max(scaleX, scaleY);
                scaleFactor = 1.0 + scale / 200.0;

                selectedItem.setScale(scaleFactor);
                repaint();
            } else {
                pressMouseX = e.getX();
                pressMouseY = e.getY();
                selectedItem.setPosition(pressMouseX - dragOffsetX, pressMouseY - dragOffsetY);
                repaint();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    public BufferedImage getPNGCanvasImage() {
        return getComposedImage(false);
    }

    public BufferedImage getJPGCanvasImage() {
        return getComposedImage(true);
    }

    public List<CreationItem> getItems() {
        return new ArrayList<>(itemList);
    }

    public int getSubCanvasWidth() {
        return width;
    }

    public int getSubCanvasHeight() {
        return height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}