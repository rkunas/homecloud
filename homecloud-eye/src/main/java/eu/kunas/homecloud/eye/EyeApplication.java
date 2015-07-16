package eu.kunas.homecloud.eye;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by ramazan on 16.07.15.
 */
public class EyeApplication extends Application {

    private Stage primaryStage;

    static public void main(final String[] args) {
        EyeApplication.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Group root = new Group();
        final Scene scene = new Scene(root);

        this.primaryStage.setTitle("EYE - Homecloud");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

}
