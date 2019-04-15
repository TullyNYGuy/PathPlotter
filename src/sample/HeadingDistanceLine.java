package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;

public class HeadingDistanceLine {

    public enum DriveDirection {
        REVERSE,
        FORWARD
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    private double heading;
    private double distance;
    private Point2DList point2DList;
    private DriveDirection driveDirection;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public Point2DList getPoint2DList() {
        return point2DList;
    }

    public double getHeading() {
        return heading;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public HeadingDistanceLine(double heading, double distance, DriveDirection driveDirection){
        this.heading = heading;
        this.distance = distance;
        this.driveDirection = driveDirection;
        convertToXY();
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

    private double maximumX(double heading){
        double maximumX = distance/Math.sqrt(1 + Math.pow(Math.tan(Math.toRadians(heading)), 2));
        if ((heading > 90 && heading < 270) || (heading < -90 && heading > -270)) {
            maximumX = - maximumX;
        }
        return maximumX;
    }

    private void convertToXY(){
        double y;
        double maximumX;
        double adjustedHeading = heading;
        point2DList = new Point2DList();

        // if the robot is driving in reverse, the actual movement is not in the direction of the heading. It is 180
        // degrees opposite of it. So make the adjustment.
        if (driveDirection == DriveDirection.REVERSE) {
            adjustedHeading = heading + 180;
        }

        maximumX = maximumX(adjustedHeading);

        // create a set of x,y points by incrementing x from 0 to maximumX. The for loop has to be different when
        // a set of points from x = 0 to a Positive is being created than when x=0 to a negative is being created.
        if (maximumX >= 0) {
            for (double x = 0; x < maximumX; x = x + maximumX / 25){
                y = Math.tan(Math.toRadians(heading))*x;
                point2DList.add(new Point2D(x,y));
            }
            // create the last point
            y = Math.tan(Math.toRadians(heading))*maximumX;
            point2DList.add(new Point2D(maximumX,y));
        } else {
            for (double x = 0; x > maximumX; x = x + maximumX / 25){
                y = Math.tan(Math.toRadians(heading))*x;
                point2DList.add(new Point2D(x,y));
            }
            // create the last point
            y = Math.tan(Math.toRadians(heading))*maximumX;
            point2DList.add(new Point2D(maximumX,y));
        }


//        if (driveDirection == DriveDirection.FORWARD) {
//            for (double x = 0; x < maximumX(); x = x + maximumX() / 25){
//                y = Math.tan(Math.toRadians(heading))*x;
//                point2DList.add(new Point2D(x,y));
//            }
//            y = Math.tan(Math.toRadians(heading))*maximumX();
//            point2DList.add(new Point2D(maximumX(),y));
//        } else {
//            // driving backwards
//            for (double x = 0; x > -maximumX(); x = x - maximumX() / 25){
//                y = Math.tan(Math.toRadians(heading))*x;
//                point2DList.add(new Point2D(x,y));
//            }
//            y = Math.tan(Math.toRadians(heading))*-maximumX();
//            point2DList.add(new Point2D(-maximumX(),y));
//        }

    }

    public Point2D getLastPoint() {
        return point2DList.getLastPoint();
    }

    public XYChart.Series getXYChartSeries() {
        return point2DList.convertToXYChartSeries();
    }

}
