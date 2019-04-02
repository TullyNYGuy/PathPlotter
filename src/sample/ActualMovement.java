package sample;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class ActualMovement {

    public enum MovementType{
        CURVE,
        DRIVE_STRAIGHT_USING_IMU,
        SPIN_TURN
    }
    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private HeadingDistancePointList headingDistancePointList;

    private MovementType movementType;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public HeadingDistancePointList getHeadingDistancePointList() {
        return headingDistancePointList;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************
    public ActualMovement() {
        headingDistancePointList = new HeadingDistancePointList();
    }


    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void add(HeadingDistancePoint headingDistancePoint) {
        headingDistancePointList.add(headingDistancePoint);
    }

    //*********************************************************************************************
    //          TEST METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void createTestData() {
        headingDistancePointList.add(new HeadingDistancePoint(.6875, 0.0));
        headingDistancePointList.add(new HeadingDistancePoint(0.6875, 0.11880819366852886));
        headingDistancePointList.add(new HeadingDistancePoint(0.8125, 0.14851024208566108));
        headingDistancePointList.add(new HeadingDistancePoint(0.9375, 0.8613594040968342));
        headingDistancePointList.add(new HeadingDistancePoint(1.0, 1.2771880819366852));
        headingDistancePointList.add(new HeadingDistancePoint(0.875, 1.8712290502793296));
        headingDistancePointList.add(new HeadingDistancePoint(0.75, 2.6434823091247672));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 3.4751396648044692));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 4.366201117318436));
        headingDistancePointList.add(new HeadingDistancePoint(0.6875, 5.702793296089385));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 7.039385474860335));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 7.989851024208566));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 8.821508379888268));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 9.50465549348231));
        headingDistancePointList.add(new HeadingDistancePoint(0.625, 10.18780260707635));
        headingDistancePointList.add(new HeadingDistancePoint(0.5625, 10.98975791433892));
        headingDistancePointList.add(new HeadingDistancePoint(0.4375, 11.732309124767225));
        headingDistancePointList.add(new HeadingDistancePoint(0.375, 12.59366852886406));
        headingDistancePointList.add(new HeadingDistancePoint(0.3125, 13.514432029795158));
        headingDistancePointList.add(new HeadingDistancePoint(0.3125, 14.583705772811918));
        headingDistancePointList.add(new HeadingDistancePoint(0.5, 15.445065176908752));
        headingDistancePointList.add(new HeadingDistancePoint(0.75, 16.48463687150838));
    }
}
