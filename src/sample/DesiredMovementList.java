package sample;

import javafx.scene.chart.XYChart;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class DesiredMovementList {

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

    private List<DesiredMovement> desiredMovementList;
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

    public DesiredMovementList() {
        desiredMovementList = new ArrayList<>();
        point2DList = new Point2DList();
    }


    //*********************************************************************************************
    //          INTERNAL PRIVATE METHODS
    //
    // private methods that the class uses
    //*********************************************************************************************

    private void combineAllMovementsToPoint2DList() {
        point2DList = desiredMovementList.get(0).getPoint2DList();
        for (int i =1; i< desiredMovementList.size(); i++) {
            point2DList.addWithTranslate(desiredMovementList.get(i).getPoint2DList());
        }
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public void add(DesiredMovement desiredMovement) {
        desiredMovementList.add(desiredMovement);
    }

    public double getFinalHeading() {
        double finalHeading = 0;
        int size = desiredMovementList.size();
        if (size == 0) {
            finalHeading = 0;
        } else {
            // get the last desired movement (remember this is 0 indexed so last is size -1
            DesiredMovement lastDesiredMovement = desiredMovementList.get(size -1);
            finalHeading = lastDesiredMovement.getFinalHeading();
        }
        return finalHeading;
    }

    public XYChart.Series getXYChartSeries(double xShift, double yshift, double rotateAngle) {
        // add all of the movements to one another to get a single Point2DList
        combineAllMovementsToPoint2DList();
        // convert the list to inches
        point2DList.toInches();
        // translate the list
        point2DList.translate(xShift, yshift);
        // rotate the list
        point2DList.rotate(rotateAngle);
        // convert the Point2D list to XYChart.Series
        return point2DList.convertToXYChartSeries();
    }

    public int size() {
        return desiredMovementList.size();
    }
}
