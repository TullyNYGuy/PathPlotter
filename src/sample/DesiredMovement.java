package sample;

public class DesiredMovement {

    //*********************************************************************************************
    //          ENUMERATED TYPES
    //
    // user defined types
    //
    //*********************************************************************************************
    public enum MovementType {
        CURVE,
        LINE,
        SPIN_TURN
    }

    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private HeadingDistanceLine headingDistanceLine;
    private Curve curve;
    private MovementType movementType;

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

    public DesiredMovement(Curve curve){
        this.curve = curve;
    }

    public DesiredMovement(HeadingDistanceLine headingDistanceLine) {
        this.headingDistanceLine = headingDistanceLine;
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
}
