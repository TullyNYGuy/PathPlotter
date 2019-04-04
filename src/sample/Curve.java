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
    private Point2D center;
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
        center = getCenter();
        convertToXY();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    private Point2D getCenter() {
        double x = 0;
        double y = 0;
        switch (rotationDirection) {
            case CCW:
                switch (driveDirection) {
                    case FORWARDS:
                        x = radius * Math.cos(Math.toRadians(90 - initialHeading));
                        y = -radius * Math.sin(Math.toRadians(90 - initialHeading));
                        break;
                    case BACKWARDS:
                        x = radius * Math.cos(Math.toRadians(-90 - initialHeading));
                        y = -radius * Math.sin(Math.toRadians(-90 - initialHeading));
                        break;
                }
                break;
            case CW:
                switch (driveDirection) {
                    case FORWARDS:
                        x = -radius * Math.cos(Math.toRadians(90 - initialHeading));
                        y = radius * Math.sin(Math.toRadians(90 - initialHeading));
                        break;
                    case BACKWARDS:
                        x = -radius * Math.cos(Math.toRadians(-90 - initialHeading));
                        y = radius * Math.sin(Math.toRadians(-90 - initialHeading));
                        break;
                }
                break;
        }
        return new Point2D(x, y);
    }

    private Point2D getPointOnCurve(double heading) {
        double x = 0;
        double y = 0;
        switch (rotationDirection) {
            case CCW:
                switch (driveDirection) {
                    case FORWARDS:
                        x = center.getX() + radius * Math.cos(Math.toRadians(90 - heading));
                        y = center.getY() - radius * Math.sin(Math.toRadians(90 - heading));
                        break;
                    case BACKWARDS:
                        x = center.getX() + radius * Math.cos(Math.toRadians(-90 - heading));
                        y = center.getY() - radius * Math.sin(Math.toRadians(-90 - heading));
                        break;
                }
                break;
            case CW:
                switch (driveDirection) {
                    case FORWARDS:
                        x = center.getX() - radius * Math.cos(Math.toRadians(90 - heading));
                        y = center.getY() + radius * Math.sin(Math.toRadians(90 - heading));
                        break;
                    case BACKWARDS:
                        x = center.getX() - radius * Math.cos(Math.toRadians(-90 - heading));
                        y = center.getY() + radius * Math.sin(Math.toRadians(-90 - heading));
                        break;
                }
                break;
        }
        return new Point2D(x, y);
    }

    private void convertToXY() {
        double heading = initialHeading;
        double headingChange = finalHeading - initialHeading;
        for (int i = 0; i < 20; i++) {
            heading = initialHeading + headingChange / i;
            point2DList.add(getPointOnCurve(heading));
        }
        point2DList.add(getPointOnCurve(finalHeading));
    }
}