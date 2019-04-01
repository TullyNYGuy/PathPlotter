package sample;

import javafx.scene.chart.ScatterChart;

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

    //*********************************************************************************************
    //          Constructors
    //
    // the function that builds the class when an object is created
    // from it
    //*********************************************************************************************
    public LogFileReader(String file) {
        this.filePath = file;

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
        Scanner scannerFile = null;
        Scanner scannerLine = null;
        ActualMovement driveStraightUsingIMUActual = null;
        ActualMovement driveCurve = null;
        ActualMovement spinTurn = null;

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
                                            break;
                                        case "INITIAL_HEADING_DISTANCE":
                                            driveStraightUsingIMUActual = new ActualMovement();
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "HEADING_DISTANCE":
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "FINAL_HEADING_DISTANCE":
                                            driveStraightUsingIMUActual.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            // add this to the list of movements
                                            break;
                                    }
                                }
                                break;

                            case "CURVE":
                                if (scannerLine.hasNext()) {
                                    action = scannerLine.next();
                                    switch (action) {
                                        case "DESIRED":
                                            break;
                                        case "INITIAL_HEADING_DISTANCE":
                                            driveCurve = new ActualMovement();
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "HEADING_DISTANCE_RATE":
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            break;
                                        case "FINAL_HEADING_DISTANCE":
                                            driveCurve.add(new HeadingDistancePoint(scannerLine.nextDouble(), scannerLine.nextDouble()));
                                            // add this to the list of movements
                                            break;
                                    }
                                }
                                break;

                            case "SPIN_TURN":
                                if (scannerLine.hasNext()) {
                                    action = scannerLine.next();
                                    switch (action) {
                                        case "DESIRED":
                                            break;
                                        case "INITIAL_HEADING":
                                            spinTurn = new ActualMovement();
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            break;
                                        case "HEADING_CORRECTION":
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            break;
                                        case "FINAL_HEADING":
                                            spinTurn.add(new HeadingDistancePoint(scannerLine.nextDouble(), 0.0));
                                            // add this to the list of movements
                                            break;
                                    }
                                }
                                break;
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
}

