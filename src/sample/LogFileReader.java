package sample;

import javafx.scene.chart.XYChart;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LogFileReader {
    //*********************************************************************************************
    //          PRIVATE DATA FIELDS
    //
    // can be accessed only by this class, or by using the public
    // getter and setter methods
    //*********************************************************************************************
    private String filePath = null;
    private ActualMovementList actualMovementList;
    private DesiredMovementList desiredMovementList;

    //*********************************************************************************************
    //          GETTER and SETTER Methods
    //
    // allow access to private data fields for example setMotorPower,
    // getPositionInTermsOfAttachment
    //*********************************************************************************************

    public ActualMovementList getActualMovementList() {
        return actualMovementList;
    }

    public DesiredMovementList getDesiredMovementList() {
        return desiredMovementList;
    }

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************
    public LogFileReader(String file) {
        this.filePath = file;
        actualMovementList = new ActualMovementList();
        desiredMovementList = new DesiredMovementList();
        readLogFile();
    }

    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void readLogFile() {
        File file = new File(filePath);
        String time;
        String action;
        String movement;
        String line;
        String value;
        double heading;
        double distance;
        HeadingDistanceLine.DriveDirection driveDirectionLine;
        double radius;
        double initialHeading;
        double finalHeading;
        Curve.RotationDirection rotationDirection;
        Curve.DriveDirection driveDirectionCurve;
        Scanner scannerFile = null;
        Scanner scannerLine = null;
        ActualMovement driveStraightUsingIMUActual = null;
        ActualMovement driveCurve = null;
        ActualMovement spinTurn = null;
        String lastMovement = "";

        try {
            scannerFile = new Scanner(file);
            scannerFile.useDelimiter(", | ");
            if (scannerFile.hasNextLine()) {
                scannerFile.nextLine();
                scannerFile.nextLine();
                System.out.println("threwAwayFirstTwoLines");
            }
            while (scannerFile.hasNextLine()) {
                line = scannerFile.nextLine();
                if (!line.isEmpty()) {
                    scannerLine = new Scanner(line);
                    scannerLine.useDelimiter(", | ");
                    // parse the time
                    if (scannerLine.hasNext()) {
                        time = scannerLine.next();
                    }
                    // parse the movement
                    if (scannerLine.hasNext()) {
                        movement = scannerLine.next();
                        switch (movement) {

                            case "DRIVE_STRAIGHT_USING_IMU":
                                if (scannerLine.hasNext()) {
                                    action = scannerLine.next();
                                    switch (action) {
                                        case "DESIRED":
                                            // heading
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            heading = scannerLine.nextDouble();
                                            // speed
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // speed value
                                            scannerLine.next();
                                            // distance
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            distance = scannerLine.nextDouble();
                                            // drive_direction
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // FORWARD
                                            driveDirectionLine = HeadingDistanceLine.DriveDirection.valueOf(scannerLine.next());
                                            desiredMovementList.add(new DesiredMovement(heading, distance, driveDirectionLine));
                                            break;
                                        case "INITIAL_HEADING_DISTANCE":
                                            driveStraightUsingIMUActual = new ActualMovement();
                                            driveStraightUsingIMUActual.setMovementType(ActualMovement.MovementType.DRIVE_STRAIGHT_USING_IMU);
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "HEADING_DISTANCE":
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "FINAL_HEADING_DISTANCE":
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            // add this to the list of movements
                                            actualMovementList.add(driveStraightUsingIMUActual);
                                            break;
                                    }
                                }
                                lastMovement = "DRIVE_STRAIGHT_USING_IMU";
                                break;

                            case "CURVE":
                                if (scannerLine.hasNext()) {
                                    action = scannerLine.next();
                                    switch (action) {
                                        case "DESIRED":
                                            //radius
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // radius value
                                            radius = scannerLine.nextDouble();
                                            // speed
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // speed value
                                            scannerLine.next();
                                            // angle
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // heading value
                                            finalHeading = scannerLine.nextDouble();
                                            // curve_direction
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // CW
                                            rotationDirection = Curve.RotationDirection.valueOf(scannerLine.next());
                                            // drive_direction
                                            scannerLine.next();
                                            // =
                                            scannerLine.next();
                                            // BACKWARD
                                            driveDirectionCurve = Curve.DriveDirection.valueOf(scannerLine.next());
                                            initialHeading = desiredMovementList.getFinalHeading();
                                            desiredMovementList.add(new DesiredMovement(radius, initialHeading, finalHeading, rotationDirection, driveDirectionCurve));
                                            break;
                                        case "INITIAL_HEADING_DISTANCE":
                                            driveCurve = new ActualMovement();
                                            driveCurve.setMovementType(ActualMovement.MovementType.CURVE);
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "HEADING_DISTANCE_RATE":
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "FINAL_HEADING_DISTANCE":
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            // add this to the list of movements
                                            actualMovementList.add(driveCurve);
                                            break;
                                    }
                                }
                                lastMovement = "CURVE";
                                break;

                            case "SPIN_TURN":
                                if (scannerLine.hasNext()) {
                                    action = scannerLine.next();
                                    switch (action) {
                                        case "DESIRED":
                                            break;
                                        case "INITIAL_HEADING":
                                            spinTurn = new ActualMovement();
                                            spinTurn.setMovementType(ActualMovement.MovementType.SPIN_TURN);
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            break;
                                        case "HEADING_CORRECTION":
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            break;
                                        case "FINAL_HEADING":
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            // add this to the list of movements
                                            actualMovementList.add(spinTurn);
                                            break;
                                    }
                                }
                                break;
                            case "Voting":
                                switch (lastMovement) {
                                    case "CURVE":
                                        actualMovementList.add(driveCurve);
                                        break;
                                    case "DRIVE_STRAIGHT_USING_IMU":
                                        actualMovementList.add(driveStraightUsingIMUActual);
                                        break;
                                    case "SPIN_TURN":
                                        actualMovementList.add(spinTurn);
                                        break;
                                }

                            default:
                                break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XYChart.Series getActualMovementXYChartSeries() {
        return actualMovementList.getXYChartSeries(RoverRuckusField.xTranslate, RoverRuckusField.yTranslate, RoverRuckusField.getRotationAngleForStartPosition(RoverRuckusField.StartLocation.DEMO));
    }

    public XYChart.Series getDesiredMovementXYChartSeries() {
        XYChart.Series result;
        if (desiredMovementList.size() > 0) {
            result = desiredMovementList.getXYChartSeries(RoverRuckusField.xTranslate, RoverRuckusField.yTranslate, RoverRuckusField.getRotationAngleForStartPosition(RoverRuckusField.StartLocation.DEMO));
        } else {
            result = null;
        }
        return result;
    }
}

