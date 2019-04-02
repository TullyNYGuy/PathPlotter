package sample;

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
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public void add(ActualMovement actualMovement) {
        actualMovementList.add(actualMovement);
    }

    public HeadingDistancePointList getAllHeadingDistancePoints(){
        HeadingDistancePointList allHeadingDistancePoints = new HeadingDistancePointList();
        for(ActualMovement actualMovement : actualMovementList) {
            allHeadingDistancePoints.add(actualMovement.getHeadingDistancePointList());
        }
        return allHeadingDistancePoints;
    }
}
