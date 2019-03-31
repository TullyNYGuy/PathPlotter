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
    private List<HeadingDistancePoint> headingDistancePointList;


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
    }


    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void add(HeadingDistancePoint headingDistancePoint) {
        headingDistancePointList.add(headingDistancePoint);
    }

    public Point2DList convert() {
        double heading;
        double distance;
        double x;
        double y;
        Point2DList point2DList = new Point2DList();
        Point2D initialPoint = new Point2D(0, 0);
        point2DList.add(initialPoint);
        for (HeadingDistancePoint headingDistancePoint : headingDistancePointList) {
            heading = headingDistancePoint.getHeading();
            distance = headingDistancePoint.getDistance();
            x = distance*Math.cos(Math.toRadians(heading));
            y = distance*Math.sin(Math.toRadians(heading));
            point2DList.add(new Point2D(x,y));
        }
        return point2DList;
    }
//    public Point2DList convert(double x, double y){
//       Point2DList point2DList = convert();
//       for(Point2D point2D : point2DList){
//           point2D.add(x, y);
//       }
//    }
}


