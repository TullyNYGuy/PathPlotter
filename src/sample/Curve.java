package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;

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
        BACKWARD,
        FORWARD
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    private double initialHeading = 0;
    private double finalHeading = 0;

    /**
     * This heading is used to account for the place where the heading crosses from -180 to +180 in an instant. If the
     * path is crossing thorugh that region, then we have to adjust the headings so that the path is plotted properly.
     * This heading is used for that adjustment.
     */
    private double internalInitalHeading = 0;

    /**
     * This heading is used to account for the place where the heading crosses from -180 to +180 in an instant. If the
     * path is crossing thorugh that region, then we have to adjust the headings so that the path is plotted properly.
     * This heading is used for that adjustment.
     */
    private double internalFinalHeading = 0;

    private double radius = 0;
    private Point2D center;
    private RotationDirection rotationDirection = RotationDirection.CW;
    private DriveDirection driveDirection = DriveDirection.FORWARD;
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

    public Curve(double radius, double initialHeading, double finalHeading, RotationDirection rotationDirection, DriveDirection driveDirection) {
        this.radius = radius;
        this.initialHeading = initialHeading;
        this.internalInitalHeading = initialHeading;
        this.finalHeading = finalHeading;
        this.internalFinalHeading = finalHeading;
        this.rotationDirection = rotationDirection;
        this.driveDirection = driveDirection;
        point2DList = new Point2DList();
        getCenter();
        convertToXY();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    private void getCenter() {
        double x = 0;
        double y = 0;
        switch (rotationDirection) {
            case CCW:
                switch (driveDirection) {
                    case FORWARD:
                        x = -radius * Math.cos(Math.toRadians(90 - initialHeading));
                        y = +radius * Math.sin(Math.toRadians(90 - initialHeading));
                        break;
                    case BACKWARD:
                        x = -radius * Math.cos(Math.toRadians(-90 - initialHeading));
                        y = +radius * Math.sin(Math.toRadians(-90 - initialHeading));
                        break;
                }
                break;
            case CW:
                switch (driveDirection) {
                    case FORWARD:
                        x = +radius * Math.cos(Math.toRadians(90 - initialHeading));
                        y = -radius * Math.sin(Math.toRadians(90 - initialHeading));
                        break;
                    case BACKWARD:
                        x = +radius * Math.cos(Math.toRadians(-90 - initialHeading));
                        y = -radius * Math.sin(Math.toRadians(-90 - initialHeading));
                        break;
                }
                break;
        }
        center = new Point2D(x, y);
    }

    private Point2D getPointOnCurve(double heading) {
        double x = 0;
        double y = 0;
        switch (rotationDirection) {
            case CCW:
                switch (driveDirection) {
                    case FORWARD:
                        x = center.getX() + radius * Math.cos(Math.toRadians(90 - heading));
                        y = center.getY() - radius * Math.sin(Math.toRadians(90 - heading));
                        break;
                    case BACKWARD:
                        x = center.getX() + radius * Math.cos(Math.toRadians(-90 - heading));
                        y = center.getY() - radius * Math.sin(Math.toRadians(-90 - heading));
                        break;
                }
                break;
            case CW:
                switch (driveDirection) {
                    case FORWARD:
                        x = center.getX() - radius * Math.cos(Math.toRadians(90 - heading));
                        y = center.getY() + radius * Math.sin(Math.toRadians(90 - heading));
                        break;
                    case BACKWARD:
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
        double headingChange = getHeadingChange();
        int numberOfPoints = 20;
        for (int i = 0; i < numberOfPoints; i++) {
            heading = internalInitalHeading + headingChange * i / numberOfPoints;
            point2DList.add(getPointOnCurve(heading));
        }
        point2DList.add(getPointOnCurve(internalFinalHeading));
    }

    public Point2D getLastPoint() {
        return point2DList.getLastPoint();
    }

    public XYChart.Series getXYChartSeries() {
        return point2DList.convertToXYChartSeries();
    }

    public XYChart.Series getXYChartSeriesTest() {
        XYChart.Series testSeries = point2DList.convertToXYChartSeries();
        testSeries.getData().add(new XYChart.Data(center.getX(), center.getY()));
        return testSeries;
    }

    private double getHeadingChange() {
        double headingChange = 0;
        if ((initialHeading > 0 && finalHeading > 0) || initialHeading < 0 && finalHeading < 0) {
            // the headings start and end as positive headings or as negative heading.
            // no adjustement is needed
            headingChange = finalHeading - initialHeading;
        } else {
            // final and initial headings have different signs. Now we have to do some checking.
            if (initialHeading > 0 && finalHeading < 0) {
                if (rotationDirection == RotationDirection.CW) {
                    // the rotation does not cross the -180 / +180 jump point
                    headingChange = finalHeading = initialHeading;
                } else {
                    // the rotation DOES cross the -180 / + 180 jump point. Adjust the final heading to a 0-360 heading
                    internalFinalHeading = 360 - finalHeading;
                    headingChange = internalFinalHeading - initialHeading;

                }
            }
            if (initialHeading < 0 && finalHeading > 0) {
                if (rotationDirection == RotationDirection.CW) {
                    // the rotation DOES cross the -180 / + 180 jump point. Adjust the final heading to a 0-360 heading
                    internalFinalHeading = 360 - finalHeading;
                    headingChange = internalFinalHeading - initialHeading;
                } else {
                    // the rotation does not cross the -180 / +180 jump point
                    headingChange = finalHeading = initialHeading;
                }
            }
        }
        return headingChange;
    }

}
