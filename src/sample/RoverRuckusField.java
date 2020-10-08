package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;
import org.omg.PortableServer.POA;

public class RoverRuckusField {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************

    public enum StartLocation {
        CRATER_SIDE_RED,
        DEPOT_SIDE_RED,
        CRATER_SIDE_BLUE,
        DEPOT_SIDE_BLUE,
        DEMO
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    static double xTranslate = 20.63;
    static double yTranslate = -1.889;

    private Point2DList fieldPoints;
    private boolean plotted = false;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public Point2DList getFieldPoints() {
        return fieldPoints;
    }

    public boolean isPlotted() {
        return plotted;
    }

    public void setPlotted(boolean plotted) {
        this.plotted = plotted;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public RoverRuckusField() {
        fieldPoints = new Point2DList();
        setFieldPoints();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public static double getRotationAngleForStartPosition(StartLocation startLocation) {
        double angle = 0;
        switch (startLocation) {
            case CRATER_SIDE_RED:
                angle = 135;
                break;
            case DEPOT_SIDE_RED:
                angle = 225;
                break;
            case CRATER_SIDE_BLUE:
                angle = 305;
                break;
            case DEPOT_SIDE_BLUE:
                angle = 45;
                break;
            case DEMO:
                angle = 0;
                break;
        }
        return angle;
    }

    public XYChart.Series getFieldPointsAsXYChartSeries() {
        XYChart.Series fieldPointsAsXYChartSeries = fieldPoints.convertToXYChartSeries();
        fieldPointsAsXYChartSeries.setName("Field");
        return fieldPointsAsXYChartSeries;
    }

    private void setFieldPoints() {
            fieldPoints.add(getLanderBodyPoints());
            fieldPoints.add(getLanderLegPoints());
            fieldPoints.add(getMineralPoints());
            fieldPoints.add(getWallPoints());
            fieldPoints.add(getDepotPoints());
    }

    private Point2DList getLanderBodyPoints() {
        Point2DList landerBodyPoints = new Point2DList();

        for (int i = 0; i < 4; i++ ) {
            landerBodyPoints.add(getLanderBodySidePoints());
            landerBodyPoints.rotate(90);
        }
        return landerBodyPoints;
    }

    private Point2DList getLanderBodySidePoints() {
        double minX = 0;
        double maxX = 16.3;
        double y = 0;
        double x = minX;
        double b = 16.3;
        int numberPoints = 30;
        Point2DList landerBodySidePoints = new Point2DList();

        for (int i = 0; i < numberPoints; i++) {
            y = -x + b;
            landerBodySidePoints.add(new Point2D(x, y));
            x = x + (maxX - minX) / numberPoints;
        }
        landerBodySidePoints.add(new Point2D(maxX, 0));
        return landerBodySidePoints;

    }

    private Point2DList getLanderLegPoints() {
        Point2DList landerLegPoints = new Point2DList();

        for (int i = 0; i < 4; i++ ) {
            landerLegPoints.add(getLanderSingleLegPoints());
            landerLegPoints.rotate(90);
        }
        return landerLegPoints;
    }

    private Point2DList getLanderSingleLegPoints() {
        double minX = 16.3;
        double maxX = 31.2;
        double y = 0;
        double x = minX;
        int numberPoints = 30;
        Point2DList landerLegPoints = new Point2DList();

        for (int i = 0; i < numberPoints; i++) {
            x = x + (maxX - minX) / numberPoints;
            landerLegPoints.add(new Point2D(x, y));
        }
        landerLegPoints.add(new Point2D(maxX, 0));
        return landerLegPoints;
    }

    private Point2DList getMineralPoints() {
        Point2DList mineralPoints = new Point2DList();

        for (int i = 0; i < 4; i++ ) {
            mineralPoints.add(getMineralSingleSetPoints());
            mineralPoints.rotate(90);
        }
        mineralPoints.rotate(45);
        return mineralPoints;
    }

    private Point2DList getMineralSingleSetPoints() {
        Point2DList mineralPoints = new Point2DList();
        mineralPoints.add(new Point2D(0, 50.35));
        mineralPoints.add(new Point2D(50.35, 14.85));
        mineralPoints.add(new Point2D(50.35, -14.85));
        return mineralPoints;
    }

    private void getCraterPoints() {

    }

    private Point2DList getWallPoints() {
        Point2DList wallSinglePoints = new Point2DList();

        for (int i = 0; i < 4; i++ ) {
            wallSinglePoints.add(getSingleWallPoints());
            wallSinglePoints.rotate(90);
        }
        return wallSinglePoints;
    }

    private Point2DList getSingleWallPoints() {
        double minY = -70.5;
        double maxY = 70.5;
        double y = minY;
        double x = 70.5;
        int numberPoints = 250;
        Point2DList wallSinglePoints = new Point2DList();

        for (int i = 0; i < numberPoints; i++) {
            y = y + (maxY - minY) / numberPoints;
            wallSinglePoints.add(new Point2D(x, y));
        }
        wallSinglePoints.add(new Point2D(x, maxY));
        return wallSinglePoints;
    }

    private Point2DList getDepotPoints() {
        double minY = -70.5;
        double maxY = -48.0;
        double y = 0;

        double minX = -70.5;
        double maxX = -48.0;
        double x = 0;
        int numberPoints = 30;
        Point2DList depotPoints = new Point2DList();

        // lower left corner
        x = -70.5;
        for (int i = 0; i < numberPoints; i++) {
            y = -48.0;
            x = x + (maxX - minX) / numberPoints;
            depotPoints.add(new Point2D(x, y));
        }
        depotPoints.add(new Point2D(x, maxY));

        y = -70.5;
        for (int i = 0; i < numberPoints; i++) {
            x = -48.0;
            y = y + (maxY - minY) / numberPoints;
            depotPoints.add(new Point2D(x, y));
        }
        depotPoints.add(new Point2D(x, maxY));

        // upper right corner
        minY = 48.0;
        maxY = 70.5;
        minX = 48.0;
        maxX = 70.5;

        x = 48;
        for (int i = 0; i < numberPoints; i++) {
            y = +48.0;
            x = x + (maxX - minX) / numberPoints;
            depotPoints.add(new Point2D(x, y));
        }
        depotPoints.add(new Point2D(x, maxY));

        y = 48;
        for (int i = 0; i < numberPoints; i++) {
            x = +48.0;
            y = y + (maxY - minY) / numberPoints;
            depotPoints.add(new Point2D(x, y));
        }
        depotPoints.add(new Point2D(x, maxY));

        return depotPoints;
    }
}
