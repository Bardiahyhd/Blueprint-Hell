import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonConfig {

    private static double ButtonSizeX = 200;
    private static double ButtonSizeY = 90;

    public static Button Button(String name, double Pos) {
        Button button = new Button(name);

        button.setFont(Font.font("Comic Sans MS", 37));

        button.setPrefWidth(ButtonSizeX);
        button.setPrefHeight(ButtonSizeY);

        button.setLayoutX(Main.StageWidth / 2 - ButtonSizeX / 2);
        button.setLayoutY(Main.StageHeight / 2 + Pos * (ButtonSizeY * 1.1));

        button.setTextFill(Color.web("#e0def4"));
        button.setStyle(
                "-fx-background-color: #6e6a86;" +
                "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );

        return button;
    }
}
