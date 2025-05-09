package GameSystem;

import Graphics.Packet;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class WireDragger {

    private Line currentLine = new Line();
    private boolean isDrawing = false;
    Circle startbubble = new Circle();
    Circle endbubble = new Circle();
    public boolean isThereALine = false;

    public WireDragger(Scene scene, Group group, Group pack, Packet packet) {
        currentLine.setStrokeWidth(2);
        currentLine.setStroke(Color.web("#f6c177"));
        startbubble.setFill(Color.web("#f6c177"));
        startbubble.setRadius(4);
        endbubble.setFill(Color.web("#f6c177"));
        endbubble.setRadius(4);
        currentLine.setMouseTransparent(true);
        startbubble.setMouseTransparent(true);
        endbubble.setMouseTransparent(true);

        packet.packet.setOnMouseClicked(event -> {
            if (!isThereALine) {
                startbubble.setCenterX(packet.packet.getLayoutX() + pack.getTranslateX());
                startbubble.setCenterY(packet.packet.getLayoutY() + pack.getTranslateY());
                endbubble.setCenterX(packet.packet.getLayoutX() + pack.getTranslateX());
                endbubble.setCenterY(packet.packet.getLayoutY() + pack.getTranslateY());
                currentLine.setStartX(packet.packet.getLayoutX() + pack.getTranslateX());
                currentLine.setStartY(packet.packet.getLayoutY() + pack.getTranslateY());
                currentLine.setEndX(packet.packet.getLayoutX() + pack.getTranslateX());
                currentLine.setEndY(packet.packet.getLayoutY() + pack.getTranslateY());
                group.getChildren().add(currentLine);
                group.getChildren().add(startbubble);
                group.getChildren().add(endbubble);
                isThereALine = true;
                isDrawing = true;
            } else {
                group.getChildren().remove(currentLine);
                group.getChildren().remove(startbubble);
                group.getChildren().remove(endbubble);
                isThereALine = false;
                isDrawing = false;
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            if (isDrawing) {
                currentLine.setEndX(event.getX());
                currentLine.setEndY(event.getY());
                endbubble.setCenterX(event.getX());
                endbubble.setCenterY(event.getY());
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (isDrawing && event.getTarget() != packet.packet) {
                currentLine.setEndX(event.getX());
                currentLine.setEndY(event.getY());
                endbubble.setCenterX(event.getX());
                endbubble.setCenterY(event.getY());
                isThereALine = true;
                isDrawing = false;
            }
        });

    }
}
