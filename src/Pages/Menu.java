package Pages;

import Config.Config;
import Graphics.ButtonConfig;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Double.parseDouble;

public class Menu {

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void MenuCreator(Stage PrimaryStage, MediaPlayer mediaPlayer) {
        Group Nodes = new Group();
        Scene MenuScene = new Scene(Nodes);

        Image MenuBackground = new Image("MenuBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(StageWidth);
        IV.setFitHeight(StageHeight);
        Group Background = new Group(IV);
        Nodes.getChildren().add(Background);

        MenuScene.setFill(Color.web("#2a273f"));

        Button play = ButtonConfig.Button("", -1);
        if (Config.Config.get("Start").equals(0.0)) {
            play.setText("Start");
        } else {
            play.setText("Continue");
        }
        play.setOnAction(e -> {
            Game.StartGame(PrimaryStage, mediaPlayer, 1);
        });

        Button levels = ButtonConfig.Button("Levels", 0);
        levels.setOnAction(e -> {
            LevelPage.LevelPageCreator(PrimaryStage, mediaPlayer);
        });

        Button settings = ButtonConfig.Button("Settings", +1);
        settings.setOnAction(e -> {
            MenuSettings.MenuSettingsCreator(PrimaryStage, mediaPlayer);
        });

        Button exit = ButtonConfig.Button("Exit", +2);
        exit.setOnAction(e -> {
            try {
                Config.RefreshConfig();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            PrimaryStage.close();
        });

        Group Buttons = new Group(play, levels, settings, exit);
        Nodes.getChildren().add(Buttons);

        PrimaryStage.setScene(MenuScene);
    }

}
