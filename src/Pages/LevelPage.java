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

public class LevelPage {
    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void LevelPageCreator(Stage PrimaryStage, MediaPlayer mediaPlayer) {
        Group Nodes = new Group();
        Scene MenuSettingsScene = new Scene(Nodes);

        Image MenuBackground = new Image("MenuSettingBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(StageWidth);
        IV.setFitHeight(StageHeight);
        Group Background = new Group(IV);
        Nodes.getChildren().add(Background);

        Button lvl1 = ButtonConfig.Button("Level 1", -1);
        lvl1.setOnAction(e -> {
            Game.StartGame(PrimaryStage, mediaPlayer, 1);
        });

        Button lvl2 = ButtonConfig.Button("Level 2", 0);
        lvl2.setOnAction(e -> {
            Game.StartGame(PrimaryStage, mediaPlayer, 2);
        });

        Button back = ButtonConfig.Button("Back", +1);
        back.setOnAction(e -> {
            Menu.MenuCreator(PrimaryStage, mediaPlayer);
        });

        Group Buttons = new Group(back, lvl1, lvl2);
        Nodes.getChildren().add(Buttons);

        PrimaryStage.setScene(MenuSettingsScene);
    }
}
