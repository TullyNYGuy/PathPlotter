package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PathPlotter.fxml"));
        Parent root = (Parent) loader.load();
        PathPlotterController controller = loader.getController();
        // set the stage so the controller can use it later
        controller.setStage(primaryStage);

        primaryStage.setTitle("Path Plotter");
        Scene scene = new Scene(root, 1000, 1000);
        scene.getStylesheets().add("sample/chart.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
