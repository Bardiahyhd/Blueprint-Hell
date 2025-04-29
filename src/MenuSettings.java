import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MenuSettings {
    public static void MenuSettingsCreator(Stage PrimaryStage) {
        Group Nodes = new Group();
        Scene MenuSettingsScene = new Scene(Nodes);

        Image MenuBackground = new Image("File:MenuSettingBackground.png");
        ImageView IV = new ImageView(MenuBackground);
        IV.setFitWidth(Main.StageWidth);
        IV.setFitHeight(Main.StageHeight);
        Group Background = new Group(IV);
        Nodes.getChildren().add(Background);

        Button back = ButtonConfig.Button("Back", +1);
        back.setOnAction( e -> {
            Menu.MenuCreator(PrimaryStage);
        });

        Group Buttons = new Group(back);
        Nodes.getChildren().add(Buttons);

        ButtonConfig.VolumeSlider(Buttons);

        PrimaryStage.setScene(MenuSettingsScene);
    }
}
