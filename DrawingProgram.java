import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrawingProgram extends JFrame implements ChangeListener {

    private JSlider penSize = new JSlider(JSlider.HORIZONTAL, 1, 30, 4);

	public DrawingProgram() {
		super("Drawing Studio Pro");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		LeftCanvasPanel leftCanvas = new LeftCanvasPanel();
		RightCanvasPanel rightCanvas = new RightCanvasPanel();
		FileButtonHandler fileHandler = new FileButtonHandler(this, leftCanvas, rightCanvas);

		ToolBar toolBar = new ToolBar(fileHandler);
		this.add(toolBar.getToolBar(), BorderLayout.NORTH);
        
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(new Label("Drag mouse to draw (RIGHT)"));
        toolbar.add(penSize);
		this.add(toolbar, BorderLayout.SOUTH);
        penSize.addChangeListener(this);

		JSplitPane splitPane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			leftCanvas,
			rightCanvas
		);

		splitPane.setDividerLocation(400);
        this.add(splitPane, BorderLayout.CENTER);
		setVisible(true);
	}

    public void stateChanged (ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            RightCanvasPanel.pen = (int)source.getValue();
        }
    }

	public static void main(String[] a){
		new DrawingProgram();
	}
}