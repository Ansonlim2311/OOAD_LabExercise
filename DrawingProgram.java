import java.awt.*;
import javax.swing.*;

public class DrawingProgram extends JFrame {

		private PenButtonHandler penHandler;
		private EraserButtonHandler eraserHandler;
		private LeftCanvasPanel leftCanvas;
		private RightCanvasPanel rightCanvas;
		private FileButtonHandler fileHandler;
		private AddButtonHandler addHandler;
		private DesignButtonHandler designHandler;
		private RefreshButtonHandler refreshHandler;
		private ToolBar toolBar;
		private JSplitPane splitPane;

	public DrawingProgram() {
		super("Drawing Studio Pro");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		penHandler = new PenButtonHandler(this);
		eraserHandler = new EraserButtonHandler();
		leftCanvas = new LeftCanvasPanel();
		rightCanvas = new RightCanvasPanel(penHandler, eraserHandler);
		fileHandler = new FileButtonHandler(this, leftCanvas, rightCanvas);
		addHandler = new AddButtonHandler(this, leftCanvas);
		designHandler = new DesignButtonHandler(this, leftCanvas);
		refreshHandler = new RefreshButtonHandler(this, rightCanvas);

		toolBar = new ToolBar(fileHandler, addHandler, designHandler, penHandler, eraserHandler, refreshHandler);
		this.add(toolBar.getToolBar(), BorderLayout.NORTH);

		splitPane = new JSplitPane(
			JSplitPane.HORIZONTAL_SPLIT,
			leftCanvas,
			rightCanvas
		);

		splitPane.setDividerLocation(400);
        this.add(splitPane, BorderLayout.CENTER);
		setVisible(true);
	}

	public static void main(String[] a){
		new DrawingProgram();
	}
}