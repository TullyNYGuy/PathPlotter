package sample;

public class RoverRuckusField {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************

    public enum StartLocation {
        CRATER_SIDE_RED,
        DEPOT_SIDE_RED,
        CRATER_SIDE_BLUE,
        DEPOT_SIDE_BLUE
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************

    static double xTranslate = 20.63;
    static double yTranslate = -1.889;

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

    public RoverRuckusField() {}

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************

    public static double getRotationAngleForStartPosition (StartLocation startLocation) {
        double angle = 0;
        switch (startLocation) {
            case CRATER_SIDE_RED:
                angle = 135;
                break;
            case DEPOT_SIDE_RED:
                angle = 225;
                break;
            case CRATER_SIDE_BLUE:
                angle = 305;
                break;
            case DEPOT_SIDE_BLUE:
                angle = 45;
                break;
        }
        return angle;
    }
}
