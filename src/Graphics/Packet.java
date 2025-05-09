package Graphics;

import Config.Config;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Packet {

    Polygon packet;

    private static final double PacketSize = (double) Config.Config.get("PacketSize");
    private static final double PacketSpeed = (double) Config.Config.get("PacketSpeed");

    private int PacketKind;

    private Timeline timeline;
    private FadeTransition fadeTransition;
    private ScaleTransition scaleTransition;

    private boolean onScaleTransition = true;

    public Packet(Group group, int PacketKind, double startX, double startY, double endX, double endY) {
        packet = new Polygon();
        group.getChildren().add(packet);

        this.PacketKind = PacketKind;

        double dis = Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));

        // triangle
        if (PacketKind == 1) {
            packet.setFill(Color.web("#f6c177"));
            packet.getPoints().addAll(0.0, -0.577 * PacketSize);
            packet.getPoints().addAll(0.5 * PacketSize, 0.288 * PacketSize);
            packet.getPoints().addAll(-0.5 * PacketSize, 0.288 * PacketSize);
        }

        // rectangle
        if (PacketKind == 2) {
            packet.setFill(Color.web("#9ccfd8"));
            packet.getPoints().addAll(-0.5 * PacketSize, -0.5 * PacketSize);
            packet.getPoints().addAll(0.5 * PacketSize, -0.5 * PacketSize);
            packet.getPoints().addAll(0.5 * PacketSize, 0.5 * PacketSize);
            packet.getPoints().addAll(-0.5 * PacketSize, 0.5 * PacketSize);
        }

        packet.setLayoutX(startX);
        packet.setLayoutY(startY);

        KeyValue keyX = new KeyValue(packet.layoutXProperty(), endX);
        KeyValue keyY = new KeyValue(packet.layoutYProperty(), endY);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(dis/PacketSpeed), keyX, keyY);

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);

        scaleTransition = new ScaleTransition(Duration.seconds(0.0015 * PacketSpeed), packet);
        scaleTransition.setFromX(0.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        fadeTransition = new FadeTransition(Duration.seconds(0.0015 * PacketSpeed), packet);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        scaleTransition.setOnFinished(e -> {
            onScaleTransition = false;

            timeline.play();
        });

        timeline.setOnFinished(e -> {
            onScaleTransition = true;

            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(0.0);
            scaleTransition.setToY(0.0);

            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);

            scaleTransition.play();
            fadeTransition.play();
        });
    }

    public void play() {
        if (onScaleTransition) {
            scaleTransition.play();
            fadeTransition.play();
        } else {
            timeline.play();
        }
    }

    public void pause() {
        if (onScaleTransition) {
            scaleTransition.pause();
            fadeTransition.pause();
        } else {
            timeline.pause();
        }
    }
}
