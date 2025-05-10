package Pages;

import Config.Config;
import Graphics.ButtonConfig;
import Graphics.Packet;
import Graphics.PacketSystem;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class Game {

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

    public static void StartGame(Stage PrimaryStage) {
        Group HUD = new Group();
        Group systems = new Group();
        Group wires = new Group();
        Group nodes = new Group(systems, wires, HUD);
        Scene Game = new Scene(nodes);

        nodes.setCache(true);

        play = ButtonConfig.Button("Play", +2.5);
        play.setLayoutX(StageWidth - 210);
        play.setStyle(
                "-fx-background-color: #191724;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );
        HUD.getChildren().add(play);

        Game.setFill(Color.web("#232136"));

        PacketSystem c = new PacketSystem(Game, systems, wires, 2, 1, 0, 1, StageWidth / 2 - 100, StageHeight / 2, false);
        PacketSystem d = new PacketSystem(Game, systems, wires, 1, 1, 2, 0, StageWidth / 2 + 100, StageHeight / 2, false);
        PacketSystem e = new PacketSystem(Game, systems, wires, 0, 1, 1, 2, StageWidth / 2 + 300, StageHeight / 2, true);
        systemsArray.add(c);
        systemsArray.add(d);
        systemsArray.add(e);

        PrimaryStage.setScene(Game);
    }
}
