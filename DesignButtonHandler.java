import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DesignButtonHandler {
    
    private Component parentComponent;
    private JFileChooser folderChooser;
    private File libraryDir = new File("library");
    private int result;

    public DesignButtonHandler(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    public void openDesignLibrary() {
        if (libraryDir.exists() == false) {
            libraryDir.mkdirs();
        }

        folderChooser = new JFileChooser(libraryDir);
        folderChooser.setDialogTitle("Select or Create a Folder in Library");
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setApproveButtonText("Select Folder");

        result = folderChooser.showOpenDialog(parentComponent);
    }
}
