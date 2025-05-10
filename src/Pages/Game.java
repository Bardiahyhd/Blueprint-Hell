package Pages;

import Config.Config;
import GameSystem.GameSystem;
import Graphics.ButtonConfig;
import Graphics.PacketSystem;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class Game {

    public static Rectangle wireUsedBar = new Rectangle(124, 24, (GameSystem.wireLimit - GameSystem.wireUsed) / GameSystem.wireLimit * 392, 32);

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    private static ArrayList<PacketSystem> systemsArray = new ArrayList<>();

    public static Button play = new Button();

    public static void isPlayable() {
         for (PacketSystem ps : systemsArray) {
             if (!ps.systemLight) {
                 play.setStyle(
                         "-fx-background-color: #191724;" +
                                 "-fx-padding: 10 20;" +
                                 "-fx-background-radius: 30;"
                 );
                 return;
             }
         }
        play.setStyle(
                "-fx-background-color: #409106;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );
    }

    public static void StartGame(Stage PrimaryStage, MediaPlayer mediaPlayer) {
        Group HUD = new Group();
        Group systems = new Group();
        Group wires = new Group();
        Group nodes = new Group(systems, wires, HUD);
        Scene Game = new Scene(nodes);

        nodes.setCache(true);

        Text wire = new Text();
        wire.setText("Wire");
        wire.setX(20);
        wire.setY(50);
        wire.setFont(Font.font("Comic Sans MS", 37));
        wire.setFill(Color.WHITE);

        Rectangle wireBar = new Rectangle(120, 20, 400, 40);
        wireBar.setFill(Color.web("#908caa"));
        wireBar.setArcWidth(35);
        wireBar.setArcHeight(35);

        wireUsedBar.setFill(Color.web("#6be612"));
        wireUsedBar.setArcWidth(33);
        wireUsedBar.setArcHeight(33);

        HUD.getChildren().add(wire);
        HUD.getChildren().add(wireBar);
        HUD.getChildren().add(wireUsedBar);

        play = ButtonConfig.Button("Play", +2.5);
        play.setLayoutX(StageWidth - 210);
        play.setStyle(
                "-fx-background-color: #191724;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );
        HUD.getChildren().add(play);

        Game.setFill(Color.web("#232136"));

        PacketSystem c = new PacketSystem(Game, systems, wires, 2, 1, 0, 1, StageWidth / 2 + 300, StageHeight / 2, false);
        PacketSystem d = new PacketSystem(Game, systems, wires, 1, 1, 2, 0, StageWidth / 2 + 20, StageHeight / 2 - 100, false);
        PacketSystem v = new PacketSystem(Game, systems, wires, 0, 1, 1, 2, StageWidth / 2 - 300, StageHeight / 2 + 100, true);
        systemsArray.add(c);
        systemsArray.add(d);
        systemsArray.add(v);

        PrimaryStage.setScene(Game);

        Group Pause = new Group();

        Image MenuBackground = new Image("File:MenuSettingBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(StageWidth);
        IV.setFitHeight(StageHeight);
        Group Background = new Group(IV);
        Pause.getChildren().add(Background);

        Button back = ButtonConfig.Button("Menu", +1);
        back.setOnAction( e -> {
            Menu.MenuCreator(PrimaryStage, mediaPlayer);
        });

        Button play = ButtonConfig.Button("Play", -1.7);
        play.setOnAction(e -> {
            nodes.getChildren().remove(Pause);
        });

        Group Buttons = new Group(play, back);
        Pause.getChildren().add(Buttons);

        ButtonConfig.VolumeSlider(Buttons, mediaPlayer);

        Game.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (nodes.getChildren().contains(Pause)) {
                    nodes.getChildren().remove(Pause);
                }else {
                    nodes.getChildren().add(Pause);
                }
            }
        });
    }
}
