import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.PrivilegedAction;

public class Menu {
    public static void MenuCreator(Stage PrimaryStage) {
        Group Nodes = new Group();
        Scene MenuScene = new Scene(Nodes);

        Image MenuBackground = new Image("File:MenuBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(Main.StageWidth);
        IV.setFitHeight(Main.StageHeight);
        Group Background = new Group(IV);
        Nodes.getChildren().add(Background);

        MenuScene.setFill(Color.web("#2a273f"));

        Button play = ButtonConfig.Button("", -1);
        if (Config.Config.get("Start").equals(0.0)) {
            play.setText("Start");
        } else {
            play.setText("Continue");
        }

        Button levels = ButtonConfig.Button("Levels", 0);

        Button settings = ButtonConfig.Button("Settings", +1);
        settings.setOnAction(e -> {
            MenuSettings.MenuSettingsCreator(PrimaryStage);
        });

        Button exit = ButtonConfig.Button("Exit", +2);
        exit.setOnAction( e -> {
            try {
                Config.RefreshConfig();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            PrimaryStage.close();
        });

        Group Buttons = new Group(play, levels, settings, exit);
        Nodes.getChildren().add(Buttons);

        PrimaryStage.setScene(MenuScene);
    }

}
