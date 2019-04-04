package sample;

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
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public void add(DesiredMovement desiredMovement) {
        desiredMovementList.add(desiredMovement);
    }

    public HeadingDistancePointList getAllHeadingDistancePoints(){
        HeadingDistancePointList allHeadingDistancePoints = new HeadingDistancePointList();
        for(DesiredMovement desiredMovement : desiredMovementList) {
            //allHeadingDistancePoints.add(desiredMovement.getHeadingDistancePointList());
        }
        return allHeadingDistancePoints;
    }
}
