import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    static double StageWidth = 1200;
    static double StageHeight = 800;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Config.LoadConfig();

        PrimaryStage.setWidth(StageWidth);
        PrimaryStage.setHeight(StageHeight);

        PrimaryStage.setResizable(false);

        Menu.MenuCreator(PrimaryStage);

        PrimaryStage.show();
    }
}

// the light should only turn on when the wires are fully connected for one system before playing the game
// game should be playable only when all the systems have their lights on
// we have limited amount of wire to use and we should show it in a bar in the game