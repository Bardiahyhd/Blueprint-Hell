package Pages;

import Config.Config;
import Graphics.ButtonConfig;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;

public class MenuSettings {

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void MenuSettingsCreator(Stage PrimaryStage, MediaPlayer mediaPlayer) {
        Group Nodes = new Group();
        Scene MenuSettingsScene = new Scene(Nodes);

        Image MenuBackground = new Image("File:MenuSettingBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(StageWidth);
        IV.setFitHeight(StageHeight);
        Group Background = new Group(IV);
        Nodes.getChildren().add(Background);

        Button back = ButtonConfig.Button("Back", +1);
        back.setOnAction(e -> {
            Menu.MenuCreator(PrimaryStage, mediaPlayer);
        });

        Group Buttons = new Group(back);
        Nodes.getChildren().add(Buttons);

        ButtonConfig.VolumeSlider(Buttons, mediaPlayer);

        PrimaryStage.setScene(MenuSettingsScene);
    }
}
