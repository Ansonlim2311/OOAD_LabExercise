import java.awt.*;
import javax.swing.*;

// Main Class for Drawing Studio Pro
public class DrawingStudioPro extends JFrame {
	// canvas panels and button handlers
	private LeftCanvasPanel leftCanvas;
	private RightCanvasPanel rightCanvas;
	private PenButtonHandler penHandler;
	private EraserButtonHandler eraserHandler;
	private FileButtonHandler fileHandler;
	private AddButtonHandler addHandler;
	private DesignButtonHandler designHandler;
	private RefreshButtonHandler refreshHandler;
	private ToolBar toolBar;
	private JSplitPane splitPane;

	public DrawingStudioPro() {
		// Set up main window
		super("Drawing Studio Pro");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Initialize the button handlers and canvas panels
		penHandler = new PenButtonHandler(this);
		eraserHandler = new EraserButtonHandler();
		leftCanvas = new LeftCanvasPanel();
		rightCanvas = new RightCanvasPanel(penHandler, eraserHandler);
		fileHandler = new FileButtonHandler(this, leftCanvas, rightCanvas);
		addHandler = new AddButtonHandler(this, leftCanvas);
		designHandler = new DesignButtonHandler(this, leftCanvas);
		refreshHandler = new RefreshButtonHandler(this, rightCanvas);

		// Create and add toolBar at top
		toolBar = new ToolBar(fileHandler, addHandler, designHandler, penHandler, eraserHandler, refreshHandler);
		this.add(toolBar.getToolBar(), BorderLayout.NORTH);

		// Split the main window into left and right panels
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCanvas, rightCanvas);
		splitPane.setDividerLocation(400);
        this.add(splitPane, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] a){
		new DrawingStudioPro();
	}
}