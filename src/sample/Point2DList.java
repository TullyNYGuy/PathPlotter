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

    /**
     * Hold a list of Point2D (x,y) points.
     */
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

    // SHOULD ADD METHODS TO GET THE LAST X AND LAST Y.

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

    /**
     * Add a Point2D to the list.
     * @param point2D
     */
    public void add(Point2D point2D) {
        point2DList.add(point2D);
    }

    // START HERE

    /**
     * Add a Point2DList to the list contained in this class.
     * @param secondPoint2DList
     * @return
     */
//    public Point2DList add(Point2DList secondPoint2DList){
//        List<Point2D> temporaryPoint2Dlist = new ArrayList<>();
//        temporaryPoint2Dlist.addAll(point2DList);
//        temporaryPoint2Dlist.addAll(secondPoint2DList.getPoint2DList());
//        return temporaryPoint2Dlist;
//    }

    /**
     * Shift (translate) the list of Point2D by a x and y value. Shifts the points around the plot. Modifies the list
     * contained in this class. The original Point2D values are not retained.
     * @param x
     * @param y
     * @return
     */
    public List<Point2D> translate(double x, double y) {
        double newX;
        double newY;
        Point2DList newPoint2DList = new Point2DList();
        for (Point2D point2D : point2DList) {
            newX = point2D.getX() + x;
            newY = point2D.getY() + y;

            newPoint2DList.add(new Point2D(newX, newY));
        }
        point2DList = newPoint2DList.getPoint2DList();
        return point2DList;
    }

    /**
     * Rotate the list of Point2D by an angle. Modified the list of Point2Ds contained in this class. The
     * original Point2D values are not retained.
     * @param angle
     * @return
     */
    public List<Point2D> rotate(double angle) {
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
        return rotatedPoints;
    }

    /**
     * Convert the list of Point2Ds into a XYChart.Series. This can be plotted using an XYChart object.
     * @return
     */
    public XYChart.Series convertToXYChartSeries() {
        XYChart.Series series = new XYChart.Series();
        double x;
        double y;
        XYChart.Data xyChartData;

        for (Point2D point : point2DList) {
            x = point.getX();
            y = point.getY();
            xyChartData = new XYChart.Data(x, y);
            series.getData().add(xyChartData);
        }
        return series;
    }

}
