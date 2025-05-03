package Pages;

import Config.Config;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.Double.parseDouble;

public class Game {

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static void StartGame(Stage PrimaryStage) {
        Group HUD = new Group();
        Group nodes = new Group(HUD);
        Scene Game = new Scene(nodes);




        PrimaryStage.setScene(Game);
    }
}
