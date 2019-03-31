package sample;

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
    public LogFileReader(String file){
        this.filePath=file;

    }
    //*********************************************************************************************
    //          MAJOR METHODS
    //
    // public methods that give the class its functionality
    //*********************************************************************************************
    public void readLogFile() {
        File file = new File(filePath);
        String key;
        double time;
        String value;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            scanner.useDelimiter(", | ");
            if (scanner.hasNextLine()){
                scanner.nextLine();
                scanner.nextLine();
                System.out.println("threwAwayFirstTwoLines");
            }
            while (scanner.hasNextLine()) {
                time = scanner.nextDouble();
                key = scanner.next();
                switch (key) {
                    case "DRIVE_STRAIGHT_USING_IMU":
                        System.out.println("foundDriveStright");
                        scanner.nextLine();
                        break;
                    case "CURVE":
                        System.out.println("foundCurve");
                        scanner.nextLine();
                        break;
                    default:
                        if (scanner.hasNextLine()) {
                            scanner.nextLine();
                        }
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

