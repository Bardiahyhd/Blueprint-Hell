package Graphics;

import Config.Config;
import GameSystem.GameSystem;
import GameSystem.WireDragger;
import Pages.Game;
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
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PacketSystem {

    private boolean prime = false;
    public Text text = new Text();

    public static Group packs = new Group();

    private Group in = new Group();
    private Group out = new Group();
    private Group elements = new Group();

    public static ArrayList<Packet> triangleIn = new ArrayList<>();
    public static ArrayList<Packet> triangleOut = new ArrayList<>();
    public static ArrayList<Packet> rectIn = new ArrayList<>();
    public static ArrayList<Packet> rectOut = new ArrayList<>();
    public static ArrayList<Packet> movingPackets = new ArrayList<>();

    private int input;
    private int output;
    public int connectedInput = 0;
    public int connectedOutput = 0;

    public boolean systemLight = false;
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

    private ArrayList<Packet> inputPacketTriangle = new ArrayList<>();
    public ArrayList<Packet> outputPacketTriangle = new ArrayList<>();
    private ArrayList<Packet> inputPacketRect = new ArrayList<>();
    public ArrayList<Packet> outputPacketRect = new ArrayList<>();
    public ArrayList<WireDragger> wiredraggers = new ArrayList<>();

    private Polygon createStar(double centerX, double centerY, double outerRadius, double innerRadius, int numPoints) {
        Polygon star = new Polygon();
        double angleStep = Math.PI / numPoints;

        for (int i = 0; i < 2 * numPoints; i++) {
            double angle = i * angleStep;
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            double x = centerX + radius * Math.cos(angle - Math.PI / 2);
            double y = centerY + radius * Math.sin(angle - Math.PI / 2);
            star.getPoints().addAll(x, y);
        }

        return star;
    }

    GameSystem gm;

    public PacketSystem(Scene scene, Group group, Group wires, Group finalWires, int input1, int input2, int output1, int output2, double X, double Y, boolean prime, GameSystem gameSystem) {
        input = input1 + input2;
        output = output1 + output2;

        this.prime = prime;
        this.gm = gameSystem;

        innerShadow.setRadius(5);
        innerShadow.setOffsetX(2);
        innerShadow.setOffsetY(2);
        innerShadow.setColor(Color.rgb(30, 30, 30, 0.6));

        shape = new Rectangle(X - systemWidthSize / 2, Y - systemHeightSize / 2, systemWidthSize, systemHeightSize);
        shape.setFill(Color.web("#908caa"));

        shape.setArcWidth(15);
        shape.setArcHeight(15);

        text.setFont(Font.font("Comic Sans MS", 37));
        text.setFill(Color.web("#e0def4"));
        text.setStyle(
                "-fx-background-color: #6e6a86;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 30;"
        );

        text.setX(X - 13);
        text.setY(Y + 25);
        text.setText("0");

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

        // setDraggable(true);

        elements.getChildren().add(shape);
        if (!prime) {
            elements.getChildren().add(text);
        }
        elements.getChildren().add(lightShape);

        for (int i = 0; i < input; i++) {
            if (i < input1) {
                Packet temp = new Packet(in, 1, X - systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (input + 1), this);
                inputPacketTriangle.add(temp);
                triangleIn.add(temp);
            } else {
                Packet temp = new Packet(in, 2, X - systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (input + 1), this);
                inputPacketRect.add(temp);
                rectIn.add(temp);
            }
        }

        for (int i = 0; i < output; i++) {
            if (i < output1) {
                Packet temp = new Packet(out, 1, X + systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (output + 1), this);
                WireDragger wireDragger = new WireDragger(scene, wires, finalWires, elements, temp, gameSystem);
                wiredraggers.add(wireDragger);
                outputPacketTriangle.add(temp);
                triangleOut.add(temp);
            } else {
                Packet temp = new Packet(out, 2, X + systemWidthSize / 2, Y - systemHeightSize / 2 + (i + 1) * systemHeightSize / (output + 1), this);
                WireDragger wireDragger = new WireDragger(scene, wires, finalWires, elements, temp, gameSystem);
                wiredraggers.add(wireDragger);
                outputPacketRect.add(temp);
                rectOut.add(temp);
            }
        }

        if (prime) {
            Polygon star = createStar(X, Y + 15, 20, 10, 5);
            star.setFill(Color.GOLD);
            star.setStroke(Color.BLACK);
            elements.getChildren().add(star);
        }

        elements.getChildren().add(in);
        elements.getChildren().add(out);
        group.getChildren().add(elements);
    }

    public Queue<Integer> packetstored = new LinkedList<>();
    private int packetstoredlimit = 5;

    public void lunch() {
        if (!packetstored.isEmpty()) {
            if (packetstored.peek() == 1) {
                for (Packet node : outputPacketTriangle) {
                    if (!node.packetonLine) {
                        packetstored.poll();
                        text.setText(Integer.toString(packetstored.size()));
                        node.packetonLine = true;
                        Packet x = new Packet(packs, 1, node.startX, node.startY, node.endX, node.endY, node);
                        movingPackets.add(x);
                        x.play();
                        return;
                    }
                }
                for (Packet node : outputPacketRect) {
                    if (!node.packetonLine) {
                        packetstored.poll();
                        text.setText(Integer.toString(packetstored.size()));
                        node.packetonLine = true;
                        Packet x = new Packet(packs, 1, node.startX, node.startY, node.endX, node.endY, node);
                        movingPackets.add(x);
                        x.play();
                        return;
                    }
                }
            }
            if (packetstored.peek() == 2) {
                for (Packet node : outputPacketRect) {
                    if (!node.packetonLine) {
                        packetstored.poll();
                        text.setText(Integer.toString(packetstored.size()));
                        node.packetonLine = true;
                        Packet x = new Packet(packs, 2, node.startX, node.startY, node.endX, node.endY, node);
                        movingPackets.add(x);
                        x.play();
                        return;
                    }
                }
                for (Packet node : outputPacketTriangle) {
                    if (!node.packetonLine) {
                        packetstored.poll();
                        text.setText(Integer.toString(packetstored.size()));
                        node.packetonLine = true;
                        Packet x = new Packet(packs, 2, node.startX, node.startY, node.endX, node.endY, node);
                        movingPackets.add(x);
                        x.play();
                        return;
                    }
                }
            }
        }
    }

    public void checkLight() {
        if (input == connectedInput && output == connectedOutput) {
            turnOnLight();
        } else {
            turnOffLight();
        }
    }

    private void turnOnLight() {
        systemLight = true;
        Game.isPlayable();
        lightShape.setEffect(null);
        lightShape.setEffect(lighting);
        lightShape.setEffect(shadow);
        lightShape.setFill(Color.YELLOW);
    }

    private void turnOffLight() {
        systemLight = false;
        Game.isPlayable();
        lightShape.setEffect(null);
        lightShape.setEffect(innerShadow);
        lightShape.setFill(offFill);
    }

    public void checkDraggable() {
        if (connectedInput == 0 && connectedOutput == 0) {
            setDraggable(true);
        } else {
            setDraggable(false);
        }
    }

    private void setDraggable(boolean bool) {
        /*
        if (bool) {
            elements.setOnMousePressed(e -> onMousePressed(e, elements));
            elements.setOnMouseDragged(e -> onMouseDragged(e, elements));
        } else {
            elements.setOnMousePressed(null);
            elements.setOnMouseDragged(null);
        }
        */
    }

    public void push_element(int packetKind) {
        if (!prime) {
            if (packetstored.size() + 1 <= packetstoredlimit) {
                packetstored.add(packetKind);
                text.setText(Integer.toString(packetstored.size()));
                lunch();
            } else {
                gm.destroyedpackets++;
            }
        } else {
            gm.packetsReceived++;
        }
        if (packetKind == 1) {
            gm.coins += 2;
        } else if (packetKind == 2) {
            gm.coins++;
        }
        Game.lostpackets.setText("Destroyed Packets : " + gm.destroyedpackets + "/" + gm.totalPackets);
        Game.packets.setText("Packets : " + gm.packetsReceived + "/" + gm.totalPackets);
        Game.coins.setText("Coins :" + gm.coins);
    }
}