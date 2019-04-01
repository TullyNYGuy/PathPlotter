package sample;

import javafx.geometry.Point2D;

public class HeadingDistanceLine {

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    private double heading;
    private double distance;

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

    public HeadingDistanceLine(double heading, double distance){
        this.heading = heading;
        this.distance = distance;
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    private double findSlope(){
        double slope = Math.tan(Math.toRadians(heading));
        return slope;
    }

    private double maximumX(){
        double maximumX = distance/Math.sqrt(1 + Math.pow(Math.tan(Math.toRadians(heading)), 2));
        return maximumX;
    }

    public Point2DList convertToXY(){
        double y;
        Point2DList point2DList = new Point2DList();
        for (double x = 0; x < maximumX(); x = x + maximumX() / 25){
            y = Math.tan(Math.toRadians(heading))*x;
            point2DList.add(new Point2D(x,y));
        }
        y = Math.tan(Math.toRadians(heading))*maximumX();
        point2DList.add(new Point2D(maximumX(),y));
        return point2DList;
    }

}
