package Pages;

import Config.Config;
import Graphics.Packet;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;

public class Game {

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void StartGame(Stage PrimaryStage) {
        Group HUD = new Group();
        Group nodes = new Group(HUD);
        Scene Game = new Scene(nodes);

        nodes.setCache(true);

        Game.setFill(Color.web("#1f1d2e"));

        Packet a = new Packet(nodes, 2, 550,  500, 150, 100);
        Packet b = new Packet(nodes, 1, 450, 500, 50, 100);

        a.play();

        PrimaryStage.setScene(Game);
    }
}
