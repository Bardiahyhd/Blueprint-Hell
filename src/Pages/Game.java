package Pages;

import Config.Config;
import Graphics.Packet;
import Graphics.PacketSystem;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static GameSystem.MouseMovement.onMouseDragged;
import static GameSystem.MouseMovement.onMousePressed;
import static java.lang.Double.parseDouble;

public class Game {

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void StartGame(Stage PrimaryStage) {
        Group HUD = new Group();
        Group systems = new Group();
        Group wires = new Group();
        Group nodes = new Group(HUD, systems, wires);
        Scene Game = new Scene(nodes);

        nodes.setCache(true);

        Game.setFill(Color.web("#232136"));

        PacketSystem c = new PacketSystem(Game, systems, wires, 2, 1, 0, 2, StageWidth / 2, StageHeight / 2);
        PacketSystem d = new PacketSystem(Game, systems, wires, 0, 2, 2, 1, StageWidth / 2 + 200, StageHeight / 2);

        PrimaryStage.setScene(Game);
    }
}
