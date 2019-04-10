package sample;

import javafx.event.EventHandler;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.gillius.jfxutils.chart.ChartPanManager;
import org.gillius.jfxutils.chart.JFXChartUtil;

import java.util.function.Function;

public class FieldPlot {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private ScatterChart fieldPlot;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public FieldPlot(ScatterChart chart) {
        this.fieldPlot = chart;

        NumberAxis xAxis = (NumberAxis) fieldPlot.getXAxis();
        // field is 12' x 12'. Put the 0,0 in the center of  the field. So the field becomes -6' to +6'.
        // now convert that to inches. so -72" to +72"
        // autoranging rounds the field up to 80" so turn it off.
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(-72.0);
        xAxis.setUpperBound(72.0);
        // tick mark every inch
        xAxis.setTickUnit(1.0);

        NumberAxis yAxis = (NumberAxis) fieldPlot.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(-72.0);
        yAxis.setUpperBound(72.0);
        yAxis.setTickUnit(1.0);

        fieldPlot.setTitle("Rover Ruckus Field");
        fieldPlot.setStyle("-fx-background-color: transparent;");
        // Use a read in jpg as the background. Make sure to remove the css that set that background before using this.
        //scatterChart.setBackground(setFieldAsBackground());

        // this crashes the compile
        //this.stage.setOnCloseRequest(e -> closeProgram());

//        fieldPlot.setOnMouseMoved( new EventHandler<MouseEvent>() {
//            @Override
//            public void handle( MouseEvent mouseEvent ) {
//                double xStart = fieldPlot.getXAxis().getLocalToParentTransform().getTx();
//                double axisXRelativeMousePosition = mouseEvent.getX() - xStart;
//                outputLabel.setText( String.format(
//                        "%d, %d (%d, %d); %d - %d",
//                        (int) mouseEvent.getSceneX(), (int) mouseEvent.getSceneY(),
//                        (int) mouseEvent.getX(), (int) mouseEvent.getY(),
//                        (int) xStart,
//                        fieldPlot.getXAxis().getValueForDisplay( axisXRelativeMousePosition ).intValue()
//                ) );
//            }
//        } );


        //Panning works via either secondary (right) mouse or primary with ctrl held down
        ChartPanManager panner = new ChartPanManager( fieldPlot );
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
        JFXChartUtil.setupZooming( fieldPlot, new EventHandler<MouseEvent>() {
            @Override
            public void handle( MouseEvent mouseEvent ) {
                if ( mouseEvent.getButton() != MouseButton.PRIMARY || mouseEvent.isShortcutDown() )
                    mouseEvent.consume();
            }
        } );
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public void clear() {
        fieldPlot.getData().clear();
    }

    public void plotPoints(XYChart.Series series, String seriesName) {
        series.setName(seriesName);
        fieldPlot.getData().addAll(series);
    }

    public void plotPoints(Point2DList points, String seriesName) {
        XYChart.Series series = points.convertToXYChartSeries();
        series.setName(seriesName);
        fieldPlot.getData().addAll(series);
    }

    public void plotLine(final Function<Double, Double> function, double negRange, double posRange) {
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        for (double x = negRange; x <= posRange; x = x + 0.01) {
            plotPoint(x, function.apply(x), series);
        }
        fieldPlot.getData().add(series);
    }

    private void plotPoint(double x, double y, XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }

}
