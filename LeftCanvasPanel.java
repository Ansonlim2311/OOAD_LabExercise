import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LeftCanvasPanel extends JPanel {

    private List<CreationItem> oldItems = new ArrayList<>();
    private JPanel centerPanel;
    private GridBagConstraints gbc;
    private JScrollPane scrollPane;
    private LeftSubCanvas newSubCanvas, subCanvas;
    private CreationItem item;
    private RotationSlider rotationSlider;

    // constructor for set up panel and initialize image drop handler and rotation slider
    public LeftCanvasPanel() {
        setTransferHandler(new ImageDropHandler());
        setPreferredSize(new Dimension(400, 600));
        setBackground(Color.LIGHT_GRAY);

        rotationSlider = new RotationSlider();
        add(rotationSlider, BorderLayout.SOUTH);
    }

    // set the sub canvas and add it to the center panel of left canvas
    public void setSubCanvas(LeftSubCanvas subCanvas) {
        this.subCanvas = subCanvas;
        removeAll();

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        centerPanel.add(subCanvas, gbc);

        scrollPane = new JScrollPane(centerPanel);
        scrollPane.setPreferredSize(new Dimension(200, 300));
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        rotationSlider.setSubCanvas(subCanvas); 
        add(rotationSlider, BorderLayout.SOUTH);
        subCanvas.setOpaque(false);
        revalidate();
        repaint();
    }

    // create new subcanvas with specified width and height
    // and copy the old item list to the new subcanvas
    public void newSubCanvas(int width, int height) {
        if (subCanvas == null) {
            subCanvas = new LeftSubCanvas(width, height);
        } else {
            newSubCanvas = new LeftSubCanvas(width, height);
            oldItems = subCanvas.getItems();
            for (int i = 0; i < oldItems.size(); i++) {
                item = oldItems.get(i);
                newSubCanvas.addItem(item);
            }
            subCanvas = newSubCanvas;
        }
        setSubCanvas(subCanvas);
    }

    // check wehter there is a sub canvas
    public boolean hasSubCanvas() {
        return subCanvas != null;
    }

    // get the current sub canvas
    public LeftSubCanvas getSubCanvas() {
        return subCanvas;
    }
}