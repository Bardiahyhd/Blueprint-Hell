package Graphics;

import Config.Config;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;

public class Packet {

    public Polygon packet;
    public int noiseCapacity;

    private static final double packetSize = (double) Config.Config.get("PacketSize");
    private static final double packetSpeed = (double) Config.Config.get("PacketSpeed");

    public int PacketKind;

    private Timeline timeline;
    private FadeTransition fadeTransition;
    private ScaleTransition scaleTransition;

    private boolean onScaleTransition = true;

    public boolean packetonLine = false;
    public boolean onLine = false;
    public PacketSystem onSystem;

    public double startX;
    public double startY;
    public double endX;
    public double endY;
    public PacketSystem otherSystem;

    public Packet(Group group, int PacketKind, double startX, double startY, PacketSystem onSystem) {
        packet = new Polygon();
        group.getChildren().add(packet);

        this.PacketKind = PacketKind;
        this.onSystem = onSystem;

        double smallRatio = 1.2;

        this.startX = startX;
        this.startY = startY;

        // triangle
        if (PacketKind == 1) {
            packet.setFill(Color.web("#d61157"));
            packet.getPoints().addAll(0.0, -0.577 * packetSize * smallRatio);
            packet.getPoints().addAll(0.5 * packetSize * 0.9, 0.288 * packetSize * smallRatio);
            packet.getPoints().addAll(-0.5 * packetSize * smallRatio, 0.288 * packetSize * smallRatio);
            noiseCapacity = 3;
        }

        // rectangle
        if (PacketKind == 2) {
            packet.setFill(Color.web("#7cafb8"));
            packet.getPoints().addAll(-0.5 * packetSize * smallRatio, -0.5 * packetSize * smallRatio);
            packet.getPoints().addAll(0.5 * packetSize * smallRatio, -0.5 * packetSize * smallRatio);
            packet.getPoints().addAll(0.5 * packetSize * smallRatio, 0.5 * packetSize * smallRatio);
            packet.getPoints().addAll(-0.5 * packetSize * smallRatio, 0.5 * packetSize * smallRatio);
            noiseCapacity = 2;
        }

        packet.setLayoutX(startX);
        packet.setLayoutY(startY);
    }

    private Group group;
    private Packet pointpacket;

    public Packet(Group group, int PacketKind, double startX, double startY, double endX, double endY, Packet pointpacket) {
        packet = new Polygon();
        group.getChildren().add(packet);

        this.group = group;
        this.pointpacket = pointpacket;
        this.PacketKind = PacketKind;

        double dis = Math.sqrt(Math.pow((endX - startX), 2) + Math.pow((endY - startY), 2));

        // triangle
        if (PacketKind == 1) {
            packet.setFill(Color.web("#f6c177"));
            packet.getPoints().addAll(0.0, -0.577 * packetSize);
            packet.getPoints().addAll(0.5 * packetSize, 0.288 * packetSize);
            packet.getPoints().addAll(-0.5 * packetSize, 0.288 * packetSize);
            noiseCapacity = 3;
        }

        // rectangle
        if (PacketKind == 2) {
            packet.setFill(Color.web("#9ccfd8"));
            packet.getPoints().addAll(-0.5 * packetSize, -0.5 * packetSize);
            packet.getPoints().addAll(0.5 * packetSize, -0.5 * packetSize);
            packet.getPoints().addAll(0.5 * packetSize, 0.5 * packetSize);
            packet.getPoints().addAll(-0.5 * packetSize, 0.5 * packetSize);
            noiseCapacity = 2;
        }

        packet.setLayoutX(startX);
        packet.setLayoutY(startY);

        KeyValue keyX = new KeyValue(packet.layoutXProperty(), endX);
        KeyValue keyY = new KeyValue(packet.layoutYProperty(), endY);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(dis/ packetSpeed), keyX, keyY);

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);

        scaleTransition = new ScaleTransition(Duration.seconds(0.0015 * packetSpeed), packet);
        scaleTransition.setFromX(0.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        fadeTransition = new FadeTransition(Duration.seconds(0.0015 * packetSpeed), packet);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        scaleTransition.setOnFinished(e -> {
            if (scaleTransition.getFromX() == 1.0) {
                group.getChildren().remove(packet);
                PacketSystem.movingPackets.remove(this);
                pointpacket.otherSystem.push_element(PacketKind);
                pointpacket.packetonLine = false;
                pointpacket.onSystem.lunch();
            }
            else {
                timeline.play();
            }

            onScaleTransition = false;
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

    public void delete() {
        pause();
        group.getChildren().remove(packet);
        PacketSystem.movingPackets.remove(this);
        pointpacket.packetonLine = false;
        pointpacket.onSystem.lunch();
    }

    private double touchmove = 1;
    public double Xtranslate;
    public double Ytranslate;

    public void touched(Double X, Double Y) {
        if (noiseCapacity == 0) {
            delete();
        } else {
            packet.setTranslateX(touchmove * (packet.getLayoutX() + packet.getTranslateX() - X));
            packet.setTranslateY(touchmove * (packet.getLayoutY() + packet.getTranslateY() - Y));
            noiseCapacity--;
        }
    }

    public void doesTouch(Packet other) {
        Shape intersection = Shape.intersect(packet, other.packet);

        if (intersection.getBoundsInLocal().getWidth() > 0 && intersection.getBoundsInLocal().getHeight() > 0) {
            touched(intersection.getBoundsInLocal().getCenterX(), intersection.getBoundsInLocal().getCenterY());
            other.touched(intersection.getBoundsInLocal().getCenterX(), intersection.getBoundsInLocal().getCenterY());
        }
    }
}
