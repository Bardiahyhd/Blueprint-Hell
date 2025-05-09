package Graphics;

import Config.Config;
import GameSystem.WireDragger;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static GameSystem.MouseMovement.onMouseDragged;
import static GameSystem.MouseMovement.onMousePressed;

public class PacketSystem {

    public Group elements = new Group();

    // min : 1, max : 3
    private int input;
    private int output;

    private boolean systemLight = false;
    private Rectangle lightShape;
    private Rectangle shape;

    private double systemWidthSize = (double) Config.Config.get("SystemWidthSize");
    private double systemHeightSize = (double) Config.Config.get("SystemHeightSize");

    private Light.Point light = new Light.Point();
    private Lighting lighting = new Lighting();
    private DropShadow shadow = new DropShadow();
    private InnerShadow innerShadow = new InnerShadow();
    private RadialGradient offFill = new RadialGradient(
            0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
            new Stop(0, Color.web("#3a3a3a")),
            new Stop(1, Color.web("#1c1c1c"))
    );

    private ArrayList <Packet> inputPacket = new ArrayList<>();
    private ArrayList <Packet> outputPacket = new ArrayList<>();

    public PacketSystem(Scene scene, Group group, Group wires, int input1, int input2, int output1, int output2, double X, double Y) {
        input = input1 + input2;
        output = output1 + output2;

        innerShadow.setRadius(5);
        innerShadow.setOffsetX(2);
        innerShadow.setOffsetY(2);
        innerShadow.setColor(Color.rgb(30, 30, 30, 0.6));

        shape = new Rectangle(X - systemWidthSize / 2, Y - systemHeightSize / 2, systemWidthSize, systemHeightSize);
        shape.setFill(Color.web("#908caa"));

        shape.setArcWidth(15);
        shape.setArcHeight(15);

        lightShape = new Rectangle(X - systemWidthSize / 2 * 0.5, Y - systemHeightSize / 2 * 0.75, systemWidthSize * 0.5, systemHeightSize * 0.15);
        lightShape.setArcWidth(20);
        lightShape.setArcHeight(20);
        lightShape.setFill(offFill);
        lightShape.setEffect(innerShadow);

        light.setX(systemWidthSize / 2);
        light.setY(systemHeightSize / 2 * 0.3);
        light.setZ(40);
        light.setColor(Color.YELLOW);

        shadow.setColor(Color.web("#f6c177"));
        shadow.setRadius(10);
        shadow.setSpread(0.3);

        lighting.setLight(light);
        lighting.setSurfaceScale(1);

        setDraggable(true);

        elements.getChildren().add(shape);
        elements.getChildren().add(lightShape);

        for (int i = 0; i < input; i++) {
            if (i < input1) {
                Packet temp = new Packet(elements, 1, X - systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (input + 1));
                inputPacket.add(temp);
            } else {
                Packet temp = new Packet(elements, 2, X - systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (input + 1));
                inputPacket.add(temp);
            }
        }

        group.getChildren().add(elements);

        for (int i = 0; i < output; i++) {
            if (i < output1) {
                Packet temp = new Packet(elements, 1, X + systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (output + 1));
                WireDragger wireDragger = new WireDragger(scene, wires, elements, temp);
                inputPacket.add(temp);
            } else {
                Packet temp = new Packet(elements, 2, X + systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (output + 1));
                WireDragger wireDragger = new WireDragger(scene, wires, elements, temp);
                inputPacket.add(temp);
            }
        }
    }

    private void turnOnLight() {
        lightShape.setEffect(null);
        lightShape.setEffect(lighting);
        lightShape.setEffect(shadow);
        lightShape.setFill(Color.YELLOW);
    }

    private void turnOffLight() {
        lightShape.setEffect(null);
        lightShape.setEffect(innerShadow);
        lightShape.setFill(offFill);
    }

    public void setDraggable(boolean bool) {
        if (bool) {
            elements.setOnMousePressed(e -> onMousePressed(e, elements));
            elements.setOnMouseDragged(e -> onMouseDragged(e, elements));
        } else {
            elements.setOnMousePressed(null);
            elements.setOnMouseDragged(null);
        }
    }
}