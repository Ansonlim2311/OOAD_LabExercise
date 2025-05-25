import javax.swing.*;

public class PenButtonHandler {
    private JFrame parentFrame;

    public PenButtonHandler(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void openPenDialog() {
        PenButtonDialog penDialog = new PenButtonDialog(parentFrame);
        penDialog.setVisible(true);
    }
}
