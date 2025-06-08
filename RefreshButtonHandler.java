import javax.swing.*;
import java.awt.*;

// RefreshButtonHandler class handles the refresh functionality for the right canvas
// It prompts the user to save changes if there are unsaved changes in the right canvas
public class RefreshButtonHandler { 
    private RightCanvasPanel rightCanvas;
    private Component parentComponent;
    private FileButtonHandler fileButtonHandler;
    private int choice;
    private boolean success;

    // Constructor to initialize the parent component and right canvas panel
    public RefreshButtonHandler(Component parentComponent, RightCanvasPanel rightCanvas, FileButtonHandler fileButtonHandler) {
        this.parentComponent = parentComponent;
        this.rightCanvas = rightCanvas;
        this.fileButtonHandler = fileButtonHandler;
    }

    // Method to handle the refresh action for the right canvas
    public void handlerRefresh() {
        // if canvas is empty, show a message and return
        if (rightCanvas.isEmpty() == true) {
            JOptionPane.showMessageDialog(parentComponent, "Right Canvas Is Already Empty");
            return;
        }
        // if there are no unsaved changes, clear the canvas
        if (rightCanvas.hasUnsavedChanges() == false) {
            rightCanvas.clearCanvas();
            return;
        }

        choice = JOptionPane.showConfirmDialog(parentComponent, "Do you want to save your drawing before refreshing?", 
                                                                        "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            return;
        }
        // if user chooses to save, call saveChange() to update the unSavedChanges Status and saveRightCanvas()
        if (choice == JOptionPane.YES_OPTION) {
            success = fileButtonHandler.saveRightCanvas();
            if (success) {
                rightCanvas.clearCanvas();
                JOptionPane.showMessageDialog(parentComponent, "Right canvas has been refreshed.");
            }
        } else {
            // if user chooses not to save, just clear the canvas
            rightCanvas.clearCanvas();
            JOptionPane.showMessageDialog(parentComponent, "Right canvas has been refreshed.");
        }
    }
}