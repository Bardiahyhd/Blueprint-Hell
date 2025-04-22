import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu {
    public static void MenuCreator(Stage PrimaryStage) {
        Group nodes = new Group();
        Scene MenuScene = new Scene(nodes);

        MenuScene.setFill(Color.web("#2a273f"));

        Button play = ButtonConfig.Button("Start", -1);

        Button levels = ButtonConfig.Button("Levels", 0);

        Button settings = ButtonConfig.Button("Settings", +1);

        Button exit = ButtonConfig.Button("Exit", +2);
        exit.setOnAction( e -> {
            PrimaryStage.close();
        });

        nodes.getChildren().add(play);
        nodes.getChildren().add(levels);
        nodes.getChildren().add(settings);
        nodes.getChildren().add(exit);

        PrimaryStage.setScene(MenuScene);
    }

}
