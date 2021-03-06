package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
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
        logFileReader.readLogFile();
        scatterChart.getData().addAll(logFileReader.getRobotMovementActualList().convertToXYChartSeries());
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
        plotSeries();
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
    private MenuItem plotCenters;

    @FXML
    private void onPlotCentersSelected(){
        XYChart.Series series;
        Curve curve = new Curve(10,45,30, Curve.DriveDirection.BACKWARD, Curve.CurveDirection.CW);
        scatterChart.getData().addAll(curve.getCenterAsSeries());

        Curve curve2 = new Curve(10,45,45+15, Curve.DriveDirection.BACKWARD, Curve.CurveDirection.CCW);
        scatterChart.getData().addAll(curve2.getCenterAsSeries());
    }

    @FXML
    private MenuItem testDesiredLine;

    @FXML
    private void onTestDesiredLineSelected() {
        XYChart.Series series;
        HeadingDistanceLine headingDistanceLine = new HeadingDistanceLine(45, 100);
        series = populateSeriesFromListOfPoints(headingDistanceLine.convertToXY().getPoint2DList());
        scatterChart.getData().addAll(series);
    }

    @FXML
    private MenuItem plotHeadingPointList;

    @FXML
    private void onPlotHeadingPointList() {
        RobotMovementsActual driveStraightUsingIMUActual = new RobotMovementsActual();
        driveStraightUsingIMUActual.createTestData();
        XYChart.Series headingDistanceXYPlotData = driveStraightUsingIMUActual.convertHeadingDistanceToXYChartSeries();
        scatterChart.getData().addAll(headingDistanceXYPlotData);
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
        // Use a read in jpg as the background. Make sure to remove the css that set that background before using this.
        //scatterChart.setBackground(setFieldAsBackground());

        scatterChart.setOnMouseMoved( new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                double xStart = scatterChart.getXAxis().getLocalToParentTransform().getTx();
                double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
//                outputLabel.setText( String.format(
//                        "%d, %d (%d, %d); %d - %d",
//                        (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(),
//                        (int) mouseEvent.getX(), (int) mouseEvent.getY(),
//                        (int) xStart,
//                        scatterChart.getXAxis().getValueForDisplay( axisXRelativeMousePosition ).intValue()
//                ) );
            }
        } );

        //Panning works via either secondary (right) mouse or primary with ctrl held down
        ChartPanManager panner = new ChartPanManager( scatterChart );
        panner.setMouseFilter( new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() == MouseButton.SECONDARY ||
                        ( mouseEvent.getButton() == MouseButton.PRIMARY &&
                                mouseEvent.isShortcutDown() ) ) {
                    //let it through
                } else {
                    mouseEvent.consume();
                }
            }
        } );
        panner.start();

        //Zooming works only via primary mouse button without ctrl held down
        JFXChartUtil.setupZooming( scatterChart, new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() != MouseButton.PRIMARY ||
                        mouseEvent.isShortcutDown() )
                    mouseEvent.consume();
            }
        } );
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

    private List<Point2D> populateListPoints() {
        List<Point2D> points = new ArrayList<Point2D>();

        points.add(new Point2D(0.00035639, 0.02969991));
        points.add(new Point2D(0.074614561, 5.583471315));
        points.add(new Point2D(0.124995353, 10.06819709));
        points.add(new Point2D(0.15946843, 14.46395602));
        points.add(new Point2D(0.228184889, 19.33450464));
        points.add(new Point2D(0.452510906, 24.67592053));
        points.add(new Point2D(0.878191521, 30.57115082));
        points.add(new Point2D(1.241740945, 35.07114156));
        points.add(new Point2D(1.461248485, 39.5802535));
        points.add(new Point2D(1.505471804, 45.60922939));
        points.add(new Point2D(1.452598183, 48.46013578));
        points.add(new Point2D(1.193724876, 55.76190117));

        return points;
    }

    private List<Point2D> translatePoints(List<Point2D> points) {
        List<Point2D> translatedPoints = new ArrayList<Point2D>();

        for (Point2D point : points) {
            point = point.add(10,10);
            translatedPoints.add(point);
        }
    return translatedPoints;
    }

    private List<Point2D> rotatePoints(List<Point2D> points) {
        List<Point2D> rotatedPoints = new ArrayList<Point2D>();
        Rotate rotate = new Rotate();
        rotate.setAngle(-45);
        rotate.setPivotX(0.0);
        rotate.setPivotY(0.0);

        for (Point2D point : points) {
            Point2D rotatedPoint = rotate.transform(point.getX(), point.getY());
            rotatedPoints.add(rotatedPoint);
        }
        return rotatedPoints;
    }

    private XYChart.Series populateSeriesFromListOfPoints(List<Point2D> points) {
        XYChart.Series series = new XYChart.Series();

        for (Point2D point : points) {
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        return series;
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


    private void plotSeries() {
        List<Point2D> originalPoints = new ArrayList<Point2D>();
        originalPoints = populateListPoints();
        scatterChart.getData().addAll(populateSeriesFromListOfPoints(originalPoints));

        List<Point2D> translatedPoints = new ArrayList<Point2D>();
        translatedPoints = translatePoints(originalPoints);
        scatterChart.getData().addAll(populateSeriesFromListOfPoints(translatedPoints));

        List<Point2D> rotatedPoints = new ArrayList<Point2D>();
        rotatedPoints = rotatePoints(originalPoints);
        scatterChart.getData().addAll(populateSeriesFromListOfPoints(rotatedPoints));
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
