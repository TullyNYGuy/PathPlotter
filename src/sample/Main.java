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
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
