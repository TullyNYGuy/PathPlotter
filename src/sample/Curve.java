package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;
import jdk.nashorn.internal.runtime.events.RecompilationEvent;

public class Curve {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************
    public enum DriveDirection {
        FORWARD,
        BACKWARD
    }

    public enum CurveDirection {
        CW,
        CCW
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private double radius;
    private double startHeading;
    private double stopHeading;
    private Point2D centerCords;
    private Point2DList curveXYPoints;
    private DriveDirection driveDirection;
    private CurveDirection curveDirection;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public Point2D getCenterCords() {
        return centerCords;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************
    public Curve(double radius, double startHeading, double stopHeading, DriveDirection driveDirection, CurveDirection curveDirection) {
        this.radius = radius;
        this.startHeading = startHeading;
        this.stopHeading = stopHeading;
        this.driveDirection = driveDirection;
        this.curveDirection = curveDirection;
        getCenter();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    private void getCenter() {
        double xCenter = 0;
        double yCenter = 0;
        if (curveDirection == CurveDirection.CW && driveDirection == DriveDirection.FORWARD) {
            xCenter = +radius * Math.cos(Math.toRadians(90 - startHeading));
            yCenter = -radius * Math.sin(Math.toRadians(90 - startHeading));
        }
        if (curveDirection == CurveDirection.CW && driveDirection == DriveDirection.BACKWARD) {
            double revStartHeading;
            revStartHeading = startHeading - 180;
            xCenter = +radius * Math.cos(Math.toRadians(90 - revStartHeading));
            yCenter = -radius * Math.sin(Math.toRadians(90 - revStartHeading));
        }
        if (curveDirection == CurveDirection.CCW && driveDirection == DriveDirection.FORWARD) {
            xCenter = -radius * Math.cos(Math.toRadians(90 - startHeading));
            yCenter = +radius * Math.sin(Math.toRadians(90 - startHeading));
        }
        if (curveDirection == CurveDirection.CCW && driveDirection == DriveDirection.BACKWARD) {
            double revStartHeading;
            revStartHeading = startHeading - 180;
            xCenter = -radius * Math.cos(Math.toRadians(90 - revStartHeading));
            yCenter = +radius * Math.sin(Math.toRadians(90 - revStartHeading));
        }
        centerCords = new Point2D(xCenter, yCenter);
    }

    private void getCurveXYPoints(){
        double x;
        double y;
        if (curveDirection == CurveDirection.CW && driveDirection == DriveDirection.FORWARD){
            double differenceInHeading = stopHeading - startHeading;
            double headingIncrement = differenceInHeading/20;
            for (int i = 0; i < 20; i++){
                x = centerCords.getX() - radius * Math.cos((Math.toRadians(90 - headingIncrement * i)));
                y = centerCords.getY() - radius * Math.sin((Math.toRadians(90 - headingIncrement * i)));
                // put all x and y values into a "point2d" list
            }
        }
    }

    public XYChart.Series getCenterAsSeries() {
        XYChart.Series xyChart = new XYChart.Series();
        xyChart.getData().add(new XYChart.Data(centerCords.getX(), centerCords.getY()));
        return xyChart;
    }
}
