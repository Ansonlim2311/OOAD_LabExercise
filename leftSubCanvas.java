import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// LeftSubCanvas class is a JPanel that allows users to create and manipulate items on a canvas.
// It supports adding items, dragging them, scaling, rotating, and flipping them.
public class LeftSubCanvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    private int width, height, centerX, centerY;
    private int pressMouseX, pressMouseY, dragOffsetX, dragOffsetY;
    private List<CreationItem> itemList = new ArrayList<>();
    private BufferedImage whiteBaseCanvas, outputImage;
    private Graphics2D whiteCanvasGraphics, paintGraphics, imageGraphic;
    private CreationItem currentItem;
    private CreationItem selectedItem = null;
    private int selectedItemX, selectedItemY, selectedItemWidth, selectedItemHeight;
    private int dragStartX, dragStartY, dragX, dragY;
    private int scaleX, scaleY, canvasCenterX, canvasCenterY;
    private double scale, scaleFactor, currentRotation, canvasRotation;

    // Constructor initializes the canvas with specified width and height.
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
        addKeyListener(this);

        setFocusable(true);
        requestFocusInWindow();
    }

    // add an item to the canvas, centering it based on the canvas dimensions
    public void addItem(CreationItem item) {
        centerX = (width - item.getWidth()) / 2;
        centerY = (height - item.getHeight()) / 2;
        item.setPosition(centerX, centerY);
        itemList.add(item);
    }

    // paint the canvas and all item, and apply rotation if set
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGraphics = (Graphics2D) g.create();

        canvasCenterX = getWidth() / 2;
        canvasCenterY = getHeight() / 2;
        paintGraphics.translate(canvasCenterX, canvasCenterY);
        paintGraphics.rotate(Math.toRadians(canvasRotation));
        paintGraphics.translate(-canvasCenterX, -canvasCenterY);

        paintGraphics.drawImage(whiteBaseCanvas, 0, 0, null);
        for(int i = 0; i < itemList.size(); i++) {
            currentItem = itemList.get(i);
            currentItem.draw(paintGraphics);
        }
        paintGraphics.dispose();
    }

    // compose the image with all items drawn on the white base canvas
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

    // mouse event handlers for moving, scaling, rotating, and flipping items
    @Override
    public void mousePressed(MouseEvent e) {
        requestFocusInWindow();
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

            if (pressMouseX >= selectedItemX && pressMouseX <= selectedItemX + selectedItemWidth && 
                pressMouseY >= selectedItemY && pressMouseY <= selectedItemY + selectedItemHeight) {
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

    // if right mouse button is released, flip the item based on drag direction
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
    public void mouseMoved(MouseEvent e) {}

    // mouseDragged handles both scaling and moving the selected item
    @Override
    public void mouseDragged(MouseEvent e) {
        if (selectedItem != null && SwingUtilities.isLeftMouseButton(e)) {
            if (e.isShiftDown()) {
                scaleX = e.getX() - dragStartX;
                scaleY = e.getY() - dragStartY;
                scale = Math.max(scaleX, scaleY);
                scaleFactor = 1.0 + scale / 200.0;
                selectedItem.setScale(scaleFactor);
            } else {
                pressMouseX = e.getX();
                pressMouseY = e.getY();
                selectedItem.setPosition(pressMouseX - dragOffsetX, pressMouseY - dragOffsetY);
            }
            repaint();
        }
    }

    // keyPressed handles rotation of the selected item
    @Override
    public void keyPressed(KeyEvent e) {
        if (selectedItem != null) {
            currentRotation = selectedItem.getRotation();
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                selectedItem.setRotation(currentRotation - 15);
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                selectedItem.setRotation(currentRotation + 15);
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    // return the composed image in PNG format
    public BufferedImage getPNGCanvasImage() {
        return getComposedImage(false);
    }

// return the composed image in JPG format
    public BufferedImage getJPGCanvasImage() {
        return getComposedImage(true);
    }

    // return the list of items on the canvas
    public List<CreationItem> getItems() {
        return new ArrayList<>(itemList);
    }

    // return the width of the subcanvas
    public int getSubCanvasWidth() {
        return width;
    }

    // return the height of the subcanvas
    public int getSubCanvasHeight() {
        return height;
    }

    // return the preferred size of the panel
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    // set the rotation angle for the left canvas
    public void setCanvasRotation(double angle) {
        this.canvasRotation = angle % 360;
        repaint();
    }
}