import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonConfig {

    private static double ButtonSizeX = 200;
    private static double ButtonSizeY = 90;
    private static double ButtonYMove = 20;

    public static Button Button(String name, double Pos) {
        Button button = new Button(name);

        button.setFont(Font.font("Comic Sans MS", 37));

        button.setPrefWidth(ButtonSizeX);
        button.setPrefHeight(ButtonSizeY);

        button.setLayoutX(Main.StageWidth / 2 - ButtonSizeX / 2);
        button.setLayoutY(Main.StageHeight / 2 + Pos * (ButtonSizeY * 1.1) + ButtonYMove);

        button.setTextFill(Color.web("#e0def4"));
        button.setStyle(
                "-fx-background-color: #6e6a86;" +
                "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(10);
        shadow.setSpread(0.3);

        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        return button;
    }

    private static double volumePanelSizeX = 400;
    private static double volumePanelSizeY = 90;
    private static double volumePanelMove = 54;

    public static void VolumeSlider(Group Node) {
        Rectangle volumePanel = new Rectangle(Main.StageWidth / 2 - volumePanelSizeX / 2, Main.StageHeight / 2 - volumePanelSizeY / 2 + volumePanelMove, volumePanelSizeX, volumePanelSizeY);

        volumePanel.setFill(Color.web("#6e6a86"));

        volumePanel.setArcWidth(100);
        volumePanel.setArcHeight(100);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(10);
        shadow.setSpread(0.01);

        volumePanel.setEffect(shadow);

        volumePanel.setOnMouseEntered(e -> {
            shadow.setSpread(0.3);
            volumePanel.setEffect(shadow);
        });
        volumePanel.setOnMouseExited(e -> {
                shadow.setSpread(0.01);
                volumePanel.setEffect(shadow);
        });

        Circle thumb = new Circle();


        Node.getChildren().add(volumePanel);
        Node.getChildren().add(thumb);
    }
}
