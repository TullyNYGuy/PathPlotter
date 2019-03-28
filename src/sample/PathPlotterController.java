package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PathPlotterController implements Initializable {

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
    private MenuItem plotSeries;

    @FXML
    private void onPlotSeriesSelected() {
        plotSeries(populateSeries());
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

    @FXML
    private ScatterChart scatterChart;

    /**
     * This method gets called just after the view loads.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NumberAxis xAxis = (NumberAxis) scatterChart.getXAxis();
        // field is 12' x 12'. Put the 0,0 in the center of  the field. So the field becomes -6' to +6'.
        // now convert that to inches. so -72" to +72"
        // autoranging rounds the field up to 80" so turn it off.
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-72.0);
        xAxis.setUpperBound(72.0);
        // tick mark every inch
        xAxis.setTickUnit(1.0);

        NumberAxis yAxis = (NumberAxis) scatterChart.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-72.0);
        yAxis.setUpperBound(72.0);
        yAxis.setTickUnit(1.0);

        scatterChart.setTitle("Rover Ruckus Field");
        scatterChart.setStyle("-fx-background-color: transparent;");
        // changed to using a css for setting background
        //scatterChart.setBackground(setFieldAsBackground());
    }

    /**
     * Lets the user select a single log file
     * @return File object with the selected file.
     */
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

    /**
     * Lets the user select multiple log files.
     * @return List of File objects.
     */
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

    private XYChart.Series populateSeries() {
        XYChart.Series series1 = new XYChart.Series();

        series1.setName("Move Straight");
        series1.getData().add(new XYChart.Data(0.00035639, 0.02969991));
        series1.getData().add(new XYChart.Data(0.074614561, 5.583471315));
        series1.getData().add(new XYChart.Data(0.124995353, 10.06819709));
        series1.getData().add(new XYChart.Data(0.15946843, 14.46395602));
        series1.getData().add(new XYChart.Data(0.228184889, 19.33450464));
        series1.getData().add(new XYChart.Data(0.452510906, 24.67592053));
        series1.getData().add(new XYChart.Data(0.878191521, 30.57115082));
        series1.getData().add(new XYChart.Data(1.241740945, 35.07114156));
        series1.getData().add(new XYChart.Data(1.461248485, 39.5802535));
        series1.getData().add(new XYChart.Data(1.505471804, 45.60922939));
        series1.getData().add(new XYChart.Data(1.452598183, 48.46013578));
        series1.getData().add(new XYChart.Data(1.193724876, 55.76190117));

        return series1;
    }

    private void plotSeries(XYChart.Series series) {
        scatterChart.getData().addAll(series);
    }

    private Background setFieldAsBackground() {
        Background background = Background.EMPTY;
        // create a input stream
        try{
            FileInputStream input = new FileInputStream("C:\\FTC\\2018Robot\\Robot Logs\\field.jpg");
            // create a image
            Image image = new Image(input);

            BackgroundSize imageSize = new BackgroundSize(.95, .95, true, true, false, false);
            BackgroundPosition imagePosition = new BackgroundPosition(Side.LEFT, .80, true, Side.TOP, .5, true);

            // create a background image
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    imagePosition,
                    imageSize);
            // create Background
            background = new Background(backgroundimage);
        }
        catch(Exception e) {
            System.out.println(e);
        }



        return background;
    }

}
