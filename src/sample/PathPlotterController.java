package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
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
        LogFileReader logFileReader= new LogFileReader(logFile.getAbsolutePath());
        fieldPlot.plotPoints(logFileReader.getActualMovementXYChartSeries(), "Actual Movement");
        fieldPlot.plotPoints(logFileReader.getDesiredMovementXYChartSeries(), "Desired Movement");
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

//    @FXML
//    private MenuItem plotSeries;
//
//    @FXML
//    private void onPlotSeriesSelected() {
//        plotSeries();
//    }
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
        closeProgram();
    }

    @FXML
    private MenuItem clear;

    @FXML
    private void onClearSelected() {
        fieldPlot.clear();
    }

    @FXML
    private MenuItem testDesiredLine;

    @FXML
    private void onTestDesiredLineSelected() {
        XYChart.Series series;
        HeadingDistanceLine headingDistanceLine = new HeadingDistanceLine(45, 100, HeadingDistanceLine.DriveDirection.FORWARD);
        series = headingDistanceLine.getXYChartSeries();
        fieldPlot.plotPoints(series,"Test Line");
    }

    @FXML
    private MenuItem testDesiredCurve;

    @FXML
    private void onTestDesiredCurveSelected() {
        XYChart.Series series;
        Curve curve = new Curve(40, -45, -135, Curve.RotationDirection.CW, Curve.DriveDirection.BACKWARD);
        series = curve.getXYChartSeriesTest();
        fieldPlot.plotPoints(series,"Test Curve");
    }

    @FXML
    private ScatterChart scatterChart;

    private FieldPlot fieldPlot;

    /**
     * This method gets called just after the view loads.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fieldPlot = new FieldPlot(scatterChart);
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

    private void closeProgram() {
        this.stage.close();
    }

//    private List<Point2D> populateListPoints() {
//        List<Point2D> points = new ArrayList<Point2D>();
//
//        points.add(new Point2D(0.00035639, 0.02969991));
//        points.add(new Point2D(0.074614561, 5.583471315));
//        points.add(new Point2D(0.124995353, 10.06819709));
//        points.add(new Point2D(0.15946843, 14.46395602));
//        points.add(new Point2D(0.228184889, 19.33450464));
//        points.add(new Point2D(0.452510906, 24.67592053));
//        points.add(new Point2D(0.878191521, 30.57115082));
//        points.add(new Point2D(1.241740945, 35.07114156));
//        points.add(new Point2D(1.461248485, 39.5802535));
//        points.add(new Point2D(1.505471804, 45.60922939));
//        points.add(new Point2D(1.452598183, 48.46013578));
//        points.add(new Point2D(1.193724876, 55.76190117));
//
//        return points;
//    }
//
//    private List<Point2D> translatePoints(List<Point2D> points) {
//        List<Point2D> translatedPoints = new ArrayList<Point2D>();
//
//        for (Point2D point : points) {
//            point = point.add(10,10);
//            translatedPoints.add(point);
//        }
//    return translatedPoints;
//    }
//
//    private List<Point2D> rotatePoints(List<Point2D> points) {
//        List<Point2D> rotatedPoints = new ArrayList<Point2D>();
//        Rotate rotate = new Rotate();
//        rotate.setAngle(-45);
//        rotate.setPivotX(0.0);
//        rotate.setPivotY(0.0);
//
//        for (Point2D point : points) {
//            Point2D rotatedPoint = rotate.transform(point.getX(), point.getY());
//            rotatedPoints.add(rotatedPoint);
//        }
//        return rotatedPoints;
//    }
//
//    private XYChart.Series populateSeriesFromListOfPoints(List<Point2D> points) {
//        XYChart.Series series = new XYChart.Series();
//
//        for (Point2D point : points) {
//            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
//        }
//        return series;
//    }
//
//
//    private XYChart.Series populateSeries() {
//        XYChart.Series series1 = new XYChart.Series();
//
//        series1.setName("Move Straight");
//        series1.getData().add(new XYChart.Data(0.00035639, 0.02969991));
//        series1.getData().add(new XYChart.Data(0.074614561, 5.583471315));
//        series1.getData().add(new XYChart.Data(0.124995353, 10.06819709));
//        series1.getData().add(new XYChart.Data(0.15946843, 14.46395602));
//        series1.getData().add(new XYChart.Data(0.228184889, 19.33450464));
//        series1.getData().add(new XYChart.Data(0.452510906, 24.67592053));
//        series1.getData().add(new XYChart.Data(0.878191521, 30.57115082));
//        series1.getData().add(new XYChart.Data(1.241740945, 35.07114156));
//        series1.getData().add(new XYChart.Data(1.461248485, 39.5802535));
//        series1.getData().add(new XYChart.Data(1.505471804, 45.60922939));
//        series1.getData().add(new XYChart.Data(1.452598183, 48.46013578));
//        series1.getData().add(new XYChart.Data(1.193724876, 55.76190117));
//
//        return series1;
//    }
//
//
//    private void plotSeries() {
//        List<Point2D> originalPoints = new ArrayList<Point2D>();
//        originalPoints = populateListPoints();
//        scatterChart.getData().addAll(populateSeriesFromListOfPoints(originalPoints));
//
//        List<Point2D> translatedPoints = new ArrayList<Point2D>();
//        translatedPoints = translatePoints(originalPoints);
//        scatterChart.getData().addAll(populateSeriesFromListOfPoints(translatedPoints));
//
//        List<Point2D> rotatedPoints = new ArrayList<Point2D>();
//        rotatedPoints = rotatePoints(originalPoints);
//        scatterChart.getData().addAll(populateSeriesFromListOfPoints(rotatedPoints));
//    }

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
