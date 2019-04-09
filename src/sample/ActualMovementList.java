package sample;

import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class ActualMovementList {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    private List<ActualMovement> actualMovementList;
    private HeadingDistancePointList headingDistancePointList;
    private Point2DList point2DList;

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

    public ActualMovementList() {
        actualMovementList = new ArrayList<>();
        headingDistancePointList = new HeadingDistancePointList();
        point2DList = new Point2DList();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public void add(ActualMovement actualMovement) {
        actualMovementList.add(actualMovement);
    }

    private void combineAllHeadingDistancePoints(){
        HeadingDistancePointList allHeadingDistancePoints = new HeadingDistancePointList();
        for(ActualMovement actualMovement : actualMovementList) {
            allHeadingDistancePoints.add(actualMovement.getHeadingDistancePointList());
        }
        headingDistancePointList = allHeadingDistancePoints;
    }

    public XYChart.Series getXYChartSeries(double xShift, double yshift, double rotateAngle) {
        // add all of the movements to one another to get a single Point2DList
        combineAllHeadingDistancePoints();
        point2DList = headingDistancePointList.convertToXY();
        // convert the list to inches
        point2DList.toInches();
        // translate the list
        point2DList.translate(xShift, yshift);
        // rotate the list
        point2DList.rotate(rotateAngle);
        // convert the Point2D list to XYChart.Series
        return point2DList.convertToXYChartSeries();
    }
}