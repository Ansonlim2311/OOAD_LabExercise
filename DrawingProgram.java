import java.awt.*;
import javax.swing.*;

public class DrawingProgram extends JFrame {

	public DrawingProgram() {
		super("Drawing Studio Pro");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		PenButtonHandler penHandler = new PenButtonHandler(this);
		EraserButtonHandler eraserHandler = new EraserButtonHandler();
		LeftCanvasPanel leftCanvas = new LeftCanvasPanel();
		RightCanvasPanel rightCanvas = new RightCanvasPanel(penHandler, eraserHandler);
		FileButtonHandler fileHandler = new FileButtonHandler(this, leftCanvas, rightCanvas);
		RefreshButtonHandler refreshHandler = new RefreshButtonHandler(this, rightCanvas);

		ToolBar toolBar = new ToolBar(fileHandler, penHandler, eraserHandler, refreshHandler);
		this.add(toolBar.getToolBar(), BorderLayout.NORTH);

		JSplitPane splitPane = new JSplitPane(
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