package sample;

import javafx.geometry.Point2D;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class RobotMovementsActualList {

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

    /**
     * Hold a list of actual robot movements
     */
    private List<RobotMovementsActual> robotMovementsActualList;

    /**
     * Holds a list of the x,y points for all of the movements combined.
     */
    private Point2DList point2DList;

    private double lastX = 0;
    private double lastY = 0;

    private Point2DList finalPoint2DList;
    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public List<RobotMovementsActual> getRobotMovementsActualList() {
        return robotMovementsActualList;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************

    public RobotMovementsActualList() {
        robotMovementsActualList = new ArrayList<>();
        finalPoint2DList = new Point2DList();
    }


    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    /**
     * Add a single robot movement to the list of robot movements.
     *
     * @param robotMovementsActual
     */
    public void add(RobotMovementsActual robotMovementsActual) {
        robotMovementsActualList.add(robotMovementsActual);
    }

    // START HERE - I THINK THIS SHOULD BE IN THE POINT2DLIST CLASS - IT SHOULD KNOW HOW TO ADD TO ITSELF

    /**
     * Create a complete continuous list of x,y points for all of the robot movements combined.
     */
    public void createCompletePoint2DList() {
        Point2DList completePoint2DList = new Point2DList();
        Point2DList temporaryPoint2DList = new Point2DList();
        for (RobotMovementsActual robotMovementsActual : robotMovementsActualList) {
            temporaryPoint2DList = robotMovementsActual.getPoint2DList();
            temporaryPoint2DList.translate(lastX, lastY);
            lastX = temporaryPoint2DList.getLastPoint2D().getX();
            lastY = temporaryPoint2DList.getLastPoint2D().getY();
            completePoint2DList.add(temporaryPoint2DList);
            finalPoint2DList = completePoint2DList;
        }
        finalPoint2DList.toInches();
        finalPoint2DList.rotate(135);
    }

    public XYChart.Series convertToXYChartSeries() {
        createCompletePoint2DList();
        return finalPoint2DList.convertToXYChartSeries();
    }
}
