package Graphics;

import Config.Config;
import Pages.Game;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static java.lang.Double.parseDouble;

public class ButtonConfig {

    private static final double StageWidth = (double) (Config.Config.get("StageWidth"));
    private static final double StageHeight = (double) (Config.Config.get("StageHeight"));

    private static double ButtonSizeX = 300;
    private static double ButtonSizeY = 90;
    private static double ButtonYMove = 20;

    public static Button Button(String name, double Pos) {
        Button button = new Button(name);

        button.setFont(Font.font("Comic Sans MS", 37));

        button.setPrefWidth(ButtonSizeX);
        button.setPrefHeight(ButtonSizeY);

        button.setLayoutX(StageWidth / 2 - ButtonSizeX / 2);
        button.setLayoutY(StageHeight / 2 + Pos * (ButtonSizeY * 1.1) + ButtonYMove);

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

    private static double volumeTitleSizeX = 200;
    private static double volumeTitleSizeY = 60;
    private static double volumeTitleMove = -20;

    private static double volumePanelSizeX = 400;
    private static double volumePanelSizeY = 90;
    private static double volumePanelMove = 64;

    private static double thumbXPos;

    private static double temporalTitleSizeX = 220;
    private static double temporalTitleSizeY = 60;
    private static double temporalTitleMove = 64;

    private static double temporalPanelSizeX = 450;
    private static double temporalPanelSizeY = 60;
    private static double temporalPanelMove = 64;
    private static double temporalXmove = -195;
    private static double temporalYmove = 250;

    private static double thumbXPos2;

    public static double temporalNum = 0;

    public static Button show;

    public static void temporal(Group Node) {
        temporalNum = 0;

        Rectangle temporalPanel = new Rectangle(StageWidth / 2 - temporalPanelSizeX / 2 + temporalXmove, StageHeight / 2 - temporalPanelSizeY / 2 + temporalPanelMove + temporalYmove, temporalPanelSizeX, temporalPanelSizeY);

        Rectangle temporalTitle = new Rectangle(-20 + StageWidth / 2 + temporalXmove + temporalPanelSizeX / 2 + temporalPanelSizeY / 2, StageHeight / 2 - temporalTitleSizeY / 2 + temporalTitleMove + temporalYmove, temporalTitleSizeX, temporalTitleSizeY);

        Text temporalText = new Text("Time Temporal 0s");
        temporalText.setFont(Font.font("Comic Sans MS", 20));
        temporalText.setFill(Color.WHITE);
        temporalText.setX(-20 + StageWidth / 2 + 20 + temporalXmove + temporalPanelSizeX / 2 + temporalPanelSizeY / 2);
        temporalText.setY(StageHeight / 2 + temporalTitleMove + 6 + temporalYmove);

        temporalTitle.setFill(Color.web("#6e6a86"));

        temporalTitle.setArcWidth(60);
        temporalTitle.setArcHeight(60);

        temporalPanel.setFill(Color.web("#6e6a86"));

        temporalPanel.setArcWidth(60);
        temporalPanel.setArcHeight(60);

        DropShadow rectangleShadow = new DropShadow();
        rectangleShadow.setColor(Color.WHITE);
        rectangleShadow.setRadius(10);
        rectangleShadow.setSpread(0.005);

        temporalPanel.setOnMouseEntered(e -> temporalPanel.setEffect(rectangleShadow));
        temporalPanel.setOnMouseExited(e -> temporalPanel.setEffect(null));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(10);
        shadow.setSpread(0.01);

        thumbXPos2 = StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 + (double) Config.Config.get("Volume") * (temporalPanelSizeX - temporalPanelSizeY) / 100;
        Circle thumb = new Circle(thumbXPos2 + temporalXmove, StageHeight / 2 + temporalPanelMove + temporalYmove, temporalPanelSizeY / 2 * 0.8);

        Rectangle bar = new Rectangle(StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 * 0.2 + temporalXmove, StageHeight / 2 - temporalPanelSizeY / 2 + temporalPanelMove + temporalPanelSizeY / 2 * 0.2 + temporalYmove, thumb.getCenterX() - (StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 * 0.2) + temporalPanelSizeY * 0.8 / 2, temporalPanelSizeY - temporalPanelSizeY / 2 * 0.4);

        bar.setFill(Color.web("#6be612"));

        bar.setArcWidth(50);
        bar.setArcHeight(50);

        thumb.setFill(Color.web("#d4c8e3"));
        thumb.setEffect(shadow);

        thumb.setOnMouseEntered(e -> shadow.setSpread(0.3));
        thumb.setOnMouseExited(e -> shadow.setSpread(0.01));

        thumb.setOnMousePressed((MouseEvent event) -> {
            if (!Game.ontemporal) {
                thumbXPos2 = event.getSceneX() - thumb.getCenterX();
            }
        });

        thumb.setOnMouseDragged((MouseEvent event) -> {
            if (!Game.ontemporal) {
                double newX = event.getSceneX() - thumbXPos2;
                newX = Math.max(StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 + temporalXmove, Math.min(newX, StageWidth / 2 + temporalPanelSizeX / 2 - temporalPanelSizeY / 2 + temporalXmove));

                double temporalCalc = (thumb.getCenterX() - (StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2)) / (temporalPanelSizeX - temporalPanelSizeY) * 100;

                temporalNum = ((int) ((temporalCalc + 50) / 100 * Game.level.timelimit));
                temporalText.setText("Time Temporal " + ((int) ((temporalCalc + 50) / 100 * Game.level.timelimit)) + "s");

                bar.setWidth(thumb.getCenterX() - (StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 * 0.2) + temporalPanelSizeY * 0.8 / 2 - temporalXmove);

                thumb.setCenterX(newX);
            }
        });

        thumb.setOnMouseReleased(e -> {
            if (!Game.ontemporal) {
                double temporalCalc = (thumb.getCenterX() - (StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2)) / (temporalPanelSizeX - temporalPanelSizeY) * 100;

                temporalNum = ((int) ((temporalCalc + 50) / 100 * Game.level.timelimit));
                temporalText.setText("Time Temporal " + ((int) ((temporalCalc + 50) / 100 * Game.level.timelimit)) + "s");

                bar.setWidth(thumb.getCenterX() - (StageWidth / 2 - temporalPanelSizeX / 2 + temporalPanelSizeY / 2 * 0.2) + temporalPanelSizeY * 0.8 / 2 - temporalXmove);
            }
        });

        thumb.setOnMouseEntered(e -> temporalPanel.setEffect(rectangleShadow));
        thumb.setOnMouseExited(e -> temporalPanel.setEffect(null));

        bar.setOnMouseEntered(e -> temporalPanel.setEffect(rectangleShadow));
        bar.setOnMouseExited(e -> temporalPanel.setEffect(null));

        Node.getChildren().add(temporalTitle);
        Node.getChildren().add(temporalText);
        Node.getChildren().add(temporalPanel);
        Node.getChildren().add(bar);
        Node.getChildren().add(thumb);

        show = new Button("Show");

        show.setFont(Font.font("Comic Sans MS", 20));

        show.setPrefWidth(temporalTitleSizeX * 0.7);
        show.setPrefHeight(temporalTitleSizeY);

        show.setTextFill(Color.web("#e0def4"));
        show.setStyle(
                "-fx-background-color: #6e6a86;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );

        show.setOnMouseEntered(e -> show.setEffect(shadow));
        show.setOnMouseExited(e -> show.setEffect(null));

        show.setLayoutX(85 + StageWidth / 2 + temporalXmove - temporalPanelSizeX / 2 - temporalPanelSizeY / 2 - temporalTitleSizeX);
        show.setLayoutY(StageHeight / 2 - temporalTitleSizeY / 2 + temporalTitleMove + temporalYmove);

        ButtonConfig.show.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        Node.getChildren().add(show);
    }

    public static void VolumeSlider(Group Node, MediaPlayer mediaPlayer) {
        Rectangle volumeTitle = new Rectangle(StageWidth / 2 - volumeTitleSizeX / 2, StageHeight / 2 - volumeTitleSizeY / 2 + volumeTitleMove, volumeTitleSizeX, volumeTitleSizeY);

        Text volumeText = new Text("Volume " + ((int) ((double) Config.Config.get("Volume"))));
        volumeText.setFont(Font.font("Comic Sans MS", 20));
        volumeText.setFill(Color.WHITE);
        volumeText.setX(StageWidth / 2 - volumeTitleSizeX / 2 + 45);
        volumeText.setY(StageHeight / 2 + volumeTitleMove + 6);

        volumeTitle.setFill(Color.web("#6e6a86"));

        volumeTitle.setArcWidth(60);
        volumeTitle.setArcHeight(60);

        Rectangle volumePanel = new Rectangle(StageWidth / 2 - volumePanelSizeX / 2, StageHeight / 2 - volumePanelSizeY / 2 + volumePanelMove, volumePanelSizeX, volumePanelSizeY);

        volumePanel.setFill(Color.web("#6e6a86"));

        volumePanel.setArcWidth(100);
        volumePanel.setArcHeight(100);

        DropShadow rectangleShadow = new DropShadow();
        rectangleShadow.setColor(Color.WHITE);
        rectangleShadow.setRadius(10);
        rectangleShadow.setSpread(0.005);

        volumePanel.setOnMouseEntered(e -> volumePanel.setEffect(rectangleShadow));
        volumePanel.setOnMouseExited(e -> volumePanel.setEffect(null));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.WHITE);
        shadow.setRadius(10);
        shadow.setSpread(0.01);

        thumbXPos = StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2 + (double) Config.Config.get("Volume") * (volumePanelSizeX - volumePanelSizeY) / 100;
        Circle thumb = new Circle(thumbXPos, StageHeight / 2 + volumePanelMove, volumePanelSizeY / 2 * 0.8);

        Rectangle bar = new Rectangle(StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2 * 0.2, StageHeight / 2 - volumePanelSizeY / 2 + volumePanelMove + volumePanelSizeY / 2 * 0.2, thumb.getCenterX() - (StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2 * 0.2) + volumePanelSizeY * 0.8 / 2, volumePanelSizeY - volumePanelSizeY / 2 * 0.4);

        bar.setFill(Color.web("#c4a7e7"));

        bar.setArcWidth(80);
        bar.setArcHeight(80);

        thumb.setFill(Color.web("#d4c8e3"));
        thumb.setEffect(shadow);

        thumb.setOnMouseEntered(e -> shadow.setSpread(0.3));
        thumb.setOnMouseExited(e -> shadow.setSpread(0.01));

        thumb.setOnMousePressed((MouseEvent event) -> {
            thumbXPos = event.getSceneX() - thumb.getCenterX();
        });

        thumb.setOnMouseDragged((MouseEvent event) -> {
            double newX = event.getSceneX() - thumbXPos;
            newX = Math.max(StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2, Math.min(newX, StageWidth / 2 + volumePanelSizeX / 2 - volumePanelSizeY / 2));

            double volumeCalc = (thumb.getCenterX() - (StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2)) / (volumePanelSizeX - volumePanelSizeY) * 100;

            Config.Config.put("Volume", volumeCalc);
            mediaPlayer.setVolume((double) (Config.Config.get("Volume")) / 100.0);

            bar.setWidth(thumb.getCenterX() - (StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2 * 0.2) + volumePanelSizeY * 0.8 / 2);

            volumeText.setText("Volume " + ((int) ((double) Config.Config.get("Volume"))));

            thumb.setCenterX(newX);
        });

        thumb.setOnMouseReleased(e -> {
            double volumeCalc = (thumb.getCenterX() - (StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2)) / (volumePanelSizeX - volumePanelSizeY) * 100;

            Config.Config.put("Volume", volumeCalc);
            mediaPlayer.setVolume((double) (Config.Config.get("Volume")) / 100.0);

            bar.setWidth(thumb.getCenterX() - (StageWidth / 2 - volumePanelSizeX / 2 + volumePanelSizeY / 2 * 0.2) + volumePanelSizeY * 0.8 / 2);

            volumeText.setText("Volume " + ((int) ((double) Config.Config.get("Volume"))));
        });

        thumb.setOnMouseEntered(e -> volumePanel.setEffect(rectangleShadow));
        thumb.setOnMouseExited(e -> volumePanel.setEffect(null));

        bar.setOnMouseEntered(e -> volumePanel.setEffect(rectangleShadow));
        bar.setOnMouseExited(e -> volumePanel.setEffect(null));

        Node.getChildren().add(volumeTitle);
        Node.getChildren().add(volumeText);
        Node.getChildren().add(volumePanel);
        Node.getChildren().add(bar);
        Node.getChildren().add(thumb);
    }
}
