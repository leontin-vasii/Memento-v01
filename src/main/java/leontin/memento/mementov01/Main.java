package leontin.memento.mementov01;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Test ControlsFX: Create a ToggleSwitch
        ToggleSwitch toggleSwitch = new ToggleSwitch("Enable Reminder");

        // Test Ikonli: Create a Button with a FontAwesome Icon
        Button iconButton = new Button("Submit");
        iconButton.setGraphic(new FontIcon(FontAwesomeSolid.CHECK_CIRCLE));

        // Layout
        HBox hbox = new HBox(10, toggleSwitch, iconButton);
        Scene scene = new Scene(hbox, 300, 100);

        primaryStage.setTitle("JavaFX with ControlsFX and Ikonli");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
