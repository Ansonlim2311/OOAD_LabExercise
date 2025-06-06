import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

// RotationSlider class is to control the rotation of a subcanvas
// using a JSlider range from 0 to 360 degrees.
public class RotationSlider extends JPanel implements ChangeListener {
    private JSlider rotationSlider;
    private LeftSubCanvas subCanvas;
    private JLabel label;
    private double angle;

    // constructor to initialize the rotation slider and label
    public RotationSlider() {
        setLayout(new BorderLayout());
        label = new JLabel("Rotate Subcanvas", JLabel.CENTER);

        rotationSlider = new JSlider(0, 360, 0);
        rotationSlider.setMajorTickSpacing(90);
        rotationSlider.setMinorTickSpacing(15);
        rotationSlider.setPaintTicks(true);
        rotationSlider.setPaintLabels(true);
        rotationSlider.addChangeListener(this);

        add(label, BorderLayout.NORTH);
        add(rotationSlider, BorderLayout.CENTER);
    }

    // set the subcanvas for the rotation slider
    public void setSubCanvas(LeftSubCanvas subCanvas) {
        this.subCanvas = subCanvas;
    }

    // called when the slider value changed
    // updates the rotation angle of the subcanvas
    public void stateChanged(ChangeEvent e) {
        if (subCanvas != null) {
            angle = rotationSlider.getValue();
            subCanvas.setCanvasRotation(angle);
        }
    }
}