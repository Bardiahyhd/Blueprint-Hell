package GameSystem;

import Graphics.Packet;
import Graphics.PacketSystem;
import Pages.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class WireDragger {

    private static boolean online = false;
    private Packet otherSide;
    private Line currentLine = new Line();
    private boolean isDrawing = false;
    Circle startbubble = new Circle();
    Circle endbubble = new Circle();
    public boolean isThereALine = false;

    public double lineLength() {
        return Math.sqrt(Math.pow(currentLine.getEndX() - currentLine.getStartX(), 2) + Math.pow(currentLine.getEndY() - currentLine.getStartY(), 2));
    }

    public WireDragger(Scene scene, Group group, Group main, Group pack, Packet packet, GameSystem gameSystem) {
        currentLine.setStrokeWidth(3);
        currentLine.setStroke(Color.web("#f6c177"));
        startbubble.setFill(Color.web("#f6c177"));
        startbubble.setRadius(5);
        endbubble.setFill(Color.web("#f6c177"));
        endbubble.setRadius(5);
        currentLine.setMouseTransparent(true);
        startbubble.setMouseTransparent(true);
        endbubble.setMouseTransparent(true);

        packet.packet.setOnMouseClicked(event -> {
            if (Game.live == false) {
                if (!online && !isThereALine) {
                    online = true;
                    packet.onSystem.connectedOutput++;
                    packet.onSystem.checkDraggable();
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
                    gameSystem.wireUsed -= lineLength();
                    Game.wireUsedBar.setWidth((gameSystem.wireLimit - gameSystem.wireUsed) / gameSystem.wireLimit * 392);
                    online = false;
                    packet.onSystem.connectedOutput--;
                    packet.onSystem.checkDraggable();
                    packet.onSystem.checkLight();
                    if (otherSide != null) {
                        otherSide.onSystem.connectedInput--;
                        otherSide.onLine = false;
                        otherSide.onSystem.checkDraggable();
                        otherSide.onSystem.checkLight();
                    }
                    otherSide = null;
                    group.getChildren().remove(currentLine);
                    group.getChildren().remove(startbubble);
                    group.getChildren().remove(endbubble);
                    isThereALine = false;
                    isDrawing = false;
                }
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            if (isDrawing) {
                boolean isValid = false;
                if (packet.PacketKind == 1) {
                    for (Packet other : PacketSystem.triangleIn) {
                        if (other.onSystem != packet.onSystem && !other.onLine && event.getTarget() == other.packet) {
                            isValid = true;
                        }
                    }
                }
                if (packet.PacketKind == 2) {
                    for (Packet other : PacketSystem.rectIn) {
                        if (other.onSystem != packet.onSystem && !other.onLine && event.getTarget() == other.packet) {
                            isValid = true;
                        }
                    }
                }

                if (isValid) {
                    currentLine.setStroke(Color.web("#a3ff61"));
                    startbubble.setFill(Color.web("#a3ff61"));
                    endbubble.setFill(Color.web("#a3ff61"));
                } else {
                    currentLine.setStroke(Color.web("#f6c177"));
                    startbubble.setFill(Color.web("#f6c177"));
                    endbubble.setFill(Color.web("#f6c177"));
                }
                currentLine.setEndX(event.getX());
                currentLine.setEndY(event.getY());
                endbubble.setCenterX(event.getX());
                endbubble.setCenterY(event.getY());
                Game.wireUsedBar.setWidth((Math.max(gameSystem.wireLimit - gameSystem.wireUsed - lineLength(), 0)) / gameSystem.wireLimit * 392);
            }
        });

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (isDrawing) {
                if (packet.PacketKind == 1) {
                    for (Packet other : PacketSystem.triangleIn) {
                        if (other.onSystem != packet.onSystem && !other.onLine && event.getTarget() == other.packet && lineLength() + gameSystem.wireUsed <= gameSystem.wireLimit) {
                            gameSystem.wireUsed += lineLength();
                            Game.wireUsedBar.setWidth((gameSystem.wireLimit - gameSystem.wireUsed) / gameSystem.wireLimit * 392);
                            packet.onSystem.checkLight();
                            packet.currentLine = currentLine;
                            packet.bubble1 = startbubble;
                            packet.bubble2 = endbubble;
                            otherSide = other;
                            other.onSystem.connectedInput++;
                            other.onSystem.checkDraggable();
                            other.onLine = true;
                            other.onSystem.checkLight();
                            currentLine.setStroke(Color.web("#e0def4"));
                            startbubble.setFill(Color.web("#e0def4"));
                            endbubble.setFill(Color.web("#e0def4"));
                            currentLine.setEndX(event.getX());
                            currentLine.setEndY(event.getY());
                            endbubble.setCenterX(event.getX());
                            endbubble.setCenterY(event.getY());
                            isThereALine = true;
                            isDrawing = false;
                            online = false;
                            packet.otherSystem = other.onSystem;
                            packet.endX = event.getX();
                            packet.endY = event.getY();
                            main.getChildren().add(currentLine);
                            main.getChildren().add(startbubble);
                            main.getChildren().add(endbubble);
                            group.getChildren().remove(currentLine);
                            group.getChildren().remove(startbubble);
                            group.getChildren().remove(endbubble);
                            // vasl shod! triangle
                        }
                    }
                }
                if (packet.PacketKind == 2) {
                    for (Packet other : PacketSystem.rectIn) {
                        if (other.onSystem != packet.onSystem && !other.onLine && event.getTarget() == other.packet && lineLength() + gameSystem.wireUsed <= gameSystem.wireLimit) {
                            gameSystem.wireUsed += lineLength();
                            Game.wireUsedBar.setWidth((gameSystem.wireLimit - gameSystem.wireUsed) / gameSystem.wireLimit * 392);
                            packet.onSystem.checkLight();
                            packet.currentLine = currentLine;
                            packet.bubble1 = startbubble;
                            packet.bubble2 = endbubble;
                            otherSide = other;
                            other.onSystem.connectedInput++;
                            other.onLine = true;
                            other.onSystem.checkDraggable();
                            other.onSystem.checkLight();
                            currentLine.setStroke(Color.web("#e0def4"));
                            startbubble.setFill(Color.web("#e0def4"));
                            endbubble.setFill(Color.web("#e0def4"));
                            currentLine.setEndX(event.getX());
                            currentLine.setEndY(event.getY());
                            endbubble.setCenterX(event.getX());
                            endbubble.setCenterY(event.getY());
                            isThereALine = true;
                            isDrawing = false;
                            online = false;
                            packet.otherSystem = other.onSystem;
                            packet.endX = event.getX();
                            packet.endY = event.getY();
                            main.getChildren().add(currentLine);
                            main.getChildren().add(startbubble);
                            main.getChildren().add(endbubble);
                            group.getChildren().remove(currentLine);
                            group.getChildren().remove(startbubble);
                            group.getChildren().remove(endbubble);
                            // vasl shod rectangle
                        }
                    }
                }
            }
        });
    }
}
