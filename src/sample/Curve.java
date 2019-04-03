package sample;

import javafx.geometry.Point2D;

public class Curve {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************
    public enum RotationDirection {
        CCW,
        CW
    }

    public enum DriveDirection {
        BACKWARDS,
        FORWARDS
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private double initialHeading = 0;
    private double finalHeading = 0;
    private double radius = 0;
    private RotationDirection rotationDirection = RotationDirection.CW;
    private DriveDirection driveDirection = DriveDirection.FORWARDS;
    private Point2DList point2DList;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public Point2DList getPoint2DList() {
        return point2DList;
    }

    public double getFinalHeading() {
        return finalHeading;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public Curve(double initialHeading, double finalHeading, RotationDirection rotationDirection, DriveDirection driveDirection) {
        this.initialHeading = initialHeading;
        this.finalHeading = finalHeading;
        this.rotationDirection = rotationDirection;
        this.driveDirection = driveDirection;
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    //private Point2D getCenter() {
    //}

    private void convertToXY() {

    }
}
