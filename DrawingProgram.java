import java.awt.*;
import javax.swing.*;

public class DrawingProgram extends JFrame {

	public DrawingProgram() {
		super("Drawing Studio Pro");
		setSize(800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		LeftCanvasPanel leftCanvas = new LeftCanvasPanel();
		RightCanvasPanel rightCanvas = new RightCanvasPanel();
		FileButtonHandler fileHandler = new FileButtonHandler(this, leftCanvas, rightCanvas);
		PenButtonHandler penHandler = new PenButtonHandler(this);
		EraserButtonHandler eraserHandler = new EraserButtonHandler();

		ToolBar toolBar = new ToolBar(fileHandler, penHandler, eraserHandler);
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