package sample;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class PathPlotterController {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private File logFile;
    private List<File> logFiles;

    @FXML
    private MenuItem openLog;

    @FXML
    private void onOpenLogSelected() {
        logFile = chooseSingleLog();
        System.out.println("log selected: " + logFile.getAbsolutePath());
    }

    @FXML
    private MenuItem openLogs;

    @FXML
    private void onOpenLogsSelected() {
        logFiles = chooseMultipleLogs();
        for (File file: logFiles) {
            System.out.println("Log selected: " + file.getAbsolutePath());
        }
    }

    @FXML
    private MenuItem close;

    @FXML
    private void onCloseSelected() {
        System.out.println("close selected");
    }

    @FXML
    private MenuItem exit;

    @FXML
    private void onExitSelected() {
        System.out.println("exit selected");
    }

    private File chooseSingleLog() {
        FileChooser fileChooser = new FileChooser();
        // Set title for FileChooser
        fileChooser.setTitle("Select One Autonomous Log");
        // Set Initial Directory
        fileChooser.setInitialDirectory(new File("C:/FTC/2018Robot/Robot Logs"));
        // set filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Log Files", "Autonomous*.log"),
                new FileChooser.ExtensionFilter("Any File", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }

    private List<File> chooseMultipleLogs() {
        List<File> fileList;
        FileChooser fileChooser = new FileChooser();
        // Set title for FileChooser
        fileChooser.setTitle("Select Several Autonomous Log");
        // Set Initial Directory
        fileChooser.setInitialDirectory(new File("C:/FTC/2018Robot/Robot Logs"));
        // set filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Log Files", "Autonomous*.log"),
                new FileChooser.ExtensionFilter("Any File", "*.*")
        );
        fileList = fileChooser.showOpenMultipleDialog(stage);
        return fileList;
    }

}
