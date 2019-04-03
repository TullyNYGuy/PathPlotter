package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

public class Point2DList {
    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private List<Point2D> point2DList;


    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************


    public List<Point2D> getPoint2DList() {
        return point2DList;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************
    public Point2DList() {
        point2DList = new ArrayList<>();
    }


    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void add(Point2D point2D) {
        point2DList.add(point2D);
    }

    public void toInches() {
        double x;
        double y;
        List<Point2D> pointsInInches = new ArrayList<>();
        for (Point2D point : point2DList) {
            x = point.getX() / 2.54;
            y = point.getY() / 2.54;
            pointsInInches.add(new Point2D(x, y));
        }
        point2DList = pointsInInches;
    }

    public XYChart.Series convertToXYChartSeries() {
        XYChart.Series series = new XYChart.Series();
        for (Point2D point : point2DList) {
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        return series;
    }

    public void translate(double x, double y) {
        List<Point2D> translatedPoints = new ArrayList<>();
        Point2D translatedPoint;
        for (Point2D point : point2DList) {
            translatedPoint = point.add(x, y);
            translatedPoints.add(translatedPoint);
        }
        point2DList = translatedPoints;
    }

    public void rotate(double angle) {
        List<Point2D> rotatedPoints = new ArrayList<Point2D>();
        Rotate rotate = new Rotate();
        rotate.setAngle(angle);
        rotate.setPivotX(0.0);
        rotate.setPivotY(0.0);

        for (Point2D point : point2DList) {
            Point2D rotatedPoint = rotate.transform(point.getX(), point.getY());
            rotatedPoints.add(rotatedPoint);
        }
        point2DList = rotatedPoints;
    }
}
