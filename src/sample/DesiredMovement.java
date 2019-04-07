package sample;

import javafx.geometry.Point2D;

public class DesiredMovement {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************

    public enum MovementType {
        CURVE,
        LINE,
        SPIN_TURN
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    private HeadingDistanceLine headingDistanceLine;
    private Curve curve;
    private MovementType movementType;

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

    public DesiredMovement(Curve curve){
        this.curve = curve;
    }

    public DesiredMovement(HeadingDistanceLine headingDistanceLine) {
        this.headingDistanceLine = headingDistanceLine;
    }

    public DesiredMovement(double radius, double initialHeading, double finalHeading, Curve.RotationDirection rotationDirection, Curve.DriveDirection driveDirection) {
        this.movementType = MovementType.CURVE;
        this.curve = new Curve(radius, initialHeading, finalHeading, rotationDirection, driveDirection);
    }

    public DesiredMovement(double heading, double distance, HeadingDistanceLine.DriveDirection driveDirection) {
        this.movementType = MovementType.LINE;
        this.headingDistanceLine = new HeadingDistanceLine(heading, distance, driveDirection);
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public Point2D getLastPoint() {
        Point2D lastPoint = null;
        switch (movementType) {
            case CURVE:
                lastPoint = curve.getLastPoint();
                break;
            case LINE:
                lastPoint = headingDistanceLine.getLastPoint();
                break;
            case SPIN_TURN:
                break;
        }
        return lastPoint;
    }

    public Point2DList getPoint2DList() {
        Point2DList point2DList = null;
        switch (movementType) {
            case CURVE:
                point2DList = curve.getPoint2DList();
                break;
            case LINE:
                point2DList = headingDistanceLine.getPoint2DList();
                break;
            case SPIN_TURN:
                break;
        }
        return point2DList;
    }

    public double getFinalHeading() {
        double finalHeading = 0;
        switch (movementType) {
            case CURVE:
                finalHeading = curve.getFinalHeading();
                break;
            case LINE:
                finalHeading = headingDistanceLine.getHeading();
                break;
            case SPIN_TURN:
                break;
        }
        return finalHeading;
    }
}
