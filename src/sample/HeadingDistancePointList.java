package sample;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class HeadingDistancePointList {
    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    /**
     * Hold a list of points in the form of heading, distance
     */
    private List<HeadingDistancePoint> headingDistancePointList;
    private Point2DList point2DList;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public List<HeadingDistancePoint> getHeadingDistancePointList() {
        return headingDistancePointList;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public HeadingDistancePointList() {
        headingDistancePointList = new ArrayList<>();
        point2DList = new Point2DList();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    /**
     * Add a single headingDistancePoint to the list
     *
     * @param headingDistancePoint
     */
    public void add(HeadingDistancePoint headingDistancePoint) {
        headingDistancePointList.add(headingDistancePoint);
    }

    /**
     * Convert the list of headingDistancePoints to a list of x,y points (Point2D)
     *
     * @return
     */
    public Point2DList convertToXY() {
        double heading;
        double distance;
        double x;
        double y;
        HeadingDistancePoint firstPoint = headingDistancePointList.get(0);
        Point2DList point2DList = new Point2DList();
        Point2D initialPoint = new Point2D(0, 0);
        point2DList.add(initialPoint);
        for (HeadingDistancePoint headingDistancePoint : headingDistancePointList) {
            heading = headingDistancePoint.getHeading();
            distance = headingDistancePoint.getDistance() - firstPoint.getDistance();
            x = distance * Math.cos(Math.toRadians(heading));
            y = distance * Math.sin(Math.toRadians(heading));
            point2DList.add(new Point2D(x, y));
        }
        this.point2DList = point2DList;
        return point2DList;
    }
}


