import Config.Config;
import Pages.Menu;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

import static java.lang.Double.parseDouble;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    public static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        Config.LoadConfig();

        final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
        final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

        File BackgroundSong = new File("Guitarmass-Infected_Mushroom.mp3");
        if (!BackgroundSong.exists()) {
            System.out.println("File not found: " + BackgroundSong.getAbsolutePath());
            return;
        }

        Media MenuSong = new Media(BackgroundSong.toURI().toString());
        mediaPlayer = new MediaPlayer(MenuSong);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        mediaPlayer.setVolume((double) (Config.Config.get("Volume")) / 100.0);

        PrimaryStage.setWidth(StageWidth);
        PrimaryStage.setHeight(StageHeight);

        PrimaryStage.setResizable(false);

        Menu.MenuCreator(PrimaryStage, mediaPlayer);

        PrimaryStage.show();
    }
}

// the light should only turn on when the wires are fully connected for one system before playing the game
// game should be playable only when all the systems have their lights on
// we have limited amount of wire to use and we should show it in a bar in the game
// the impact make noises on a radius for packets and each packet has a noise maximum
// temporal progress bar in the game