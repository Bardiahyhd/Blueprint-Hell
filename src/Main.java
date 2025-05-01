import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    static MediaPlayer mediaPlayer;

    static double StageWidth = 1200;
    static double StageHeight = 800;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Config.LoadConfig();

        Media MenuSong = new Media(Objects.requireNonNull(getClass().getResource("/Guitarmass-Infected_Mushroom.mp3")).toExternalForm());
        mediaPlayer = new MediaPlayer(MenuSong);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer.setVolume((double)(Config.Config.get("Volume")) / 100.0 * 0.3);

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