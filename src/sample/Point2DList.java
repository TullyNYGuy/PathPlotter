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

    public Point2D getLastPoint() {
        int length = point2DList.size();
        return point2DList.get(length-1);
    }

    public void add(Point2D point2D) {
        point2DList.add(point2D);
    }

    /**
     * Add a different list of Point2Ds to this list. In other words extend this list of Point2D and add the other list
     * of Point2Ds to the end of this list.
     * @param otherPoint2DList
     */
    public void add(Point2DList otherPoint2DList) {
        ArrayList<Point2D> bothLists = new ArrayList<>();
        bothLists.addAll(point2DList);
        bothLists.addAll(otherPoint2DList.getPoint2DList());
        point2DList = bothLists;
    }

    /**
     * Given another list of Point2Ds, look at the last Point2D in this list and get the x and y values from that last
     * Point2D. Then translate the other list of Point2Ds by the last x and y value. Finally extend this list and add
     * the translated list of Point2Ds to this list.
     * @param otherPoint2DList
     */
    public void addWithTranslate(Point2DList otherPoint2DList) {
        int length = this.point2DList.size();
        double lastX = getLastPoint().getX();
        double lastY = getLastPoint().getY();
        otherPoint2DList.translate(lastX, lastY);
        add(otherPoint2DList);
    }

    /**
     * Convert the list of Point2D from CM to inches.
     */
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

    /**
     * Convert the list of Point2D to a XYCharts.Series in prepartion for plotting the list of Point2D.
     * @return
     */
    public XYChart.Series convertToXYChartSeries() {
        XYChart.Series series = new XYChart.Series();
        for (Point2D point : point2DList) {
            series.getData().add(new XYChart.Data(point.getX(), point.getY()));
        }
        return series;
    }

    /**
     * Add a given x value to all of the x values in the list of Point2D. Same for the y. The effect is to shift the
     * list of Point2Ds in the x and y by the given x and y values.
     * @param x
     * @param y
     */
    public void translate(double x, double y) {
        List<Point2D> translatedPoints = new ArrayList<>();
        Point2D translatedPoint;
        for (Point2D point : point2DList) {
            translatedPoint = point.add(x, y);
            translatedPoints.add(translatedPoint);
        }
        point2DList = translatedPoints;
    }

    /**
     * Rotate the list of Point2D by the given angle. Note that 0 degrees is the + x axis.
     * @param angle
     */
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
