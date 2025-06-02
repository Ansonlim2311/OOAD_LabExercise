import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class RotationSlider extends JPanel implements ChangeListener {
    private JSlider rotationSlider;
    private LeftSubCanvas subCanvas;
    private JLabel label;
    private double angle;

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

    public void setSubCanvas(LeftSubCanvas subCanvas) {
        this.subCanvas = subCanvas;
    }

    public void stateChanged(ChangeEvent e) {
        if (subCanvas != null) {
            angle = rotationSlider.getValue();
            subCanvas.setCanvasRotation(angle);
        }
    }
}