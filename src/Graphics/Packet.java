package Graphics;

import Config.Config;
import GameSystem.GameSystem;
import Pages.Game;
import Pages.Menu;
import SoundEffects.SoundEffects;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

public class Packet {

    public Polygon packet;
    public int noiseCapacity = 2;

    private static final double packetSize = (double) Config.Config.get("PacketSize");
    private static final double packetSpeed = (double) Config.Config.get("PacketSpeed");

    public int PacketKind;

    private Timeline timeline;
    private FadeTransition fadeTransition;
    private ScaleTransition scaleTransition;
    private KeyFrame keyFrame;

    private boolean onScaleTransition = true;

    public boolean packetonLine = false;
    public boolean onLine = false;
    public PacketSystem onSystem;

    public double startX;
    public double startY;
    public double endX;
    public double endY;
    public PacketSystem otherSystem;

    public Line currentLine;
    public Circle bubble1;
    public Circle bubble2;

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

    private Pane group;
    private Packet pointpacket;

    public int id;
    public Text health;

    public Packet(Pane group, int PacketKind, double startX, double startY, double endX, double endY, Packet pointpacket) {
        packet = new Polygon();
        packet.setCache(false);
        group.getChildren().add(packet);

        health = new Text(Integer.toString(noiseCapacity));
        health.setFill(Color.WHITE);
        // group.getChildren().add(health);

        id = Config.generalid;
        Config.generalid++;

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

        health.setLayoutX(startX);
        health.setLayoutY(startY);
        health.layoutXProperty().bind(packet.layoutXProperty());
        health.layoutYProperty().bind(packet.layoutYProperty());

        KeyValue keyX = new KeyValue(packet.layoutXProperty(), endX);
        KeyValue keyY = new KeyValue(packet.layoutYProperty(), endY);

        if (PacketKind == 2) {
            if (pointpacket.PacketKind == PacketKind) {
                keyFrame = new KeyFrame(Duration.seconds(dis / (packetSpeed / 2)), keyX, keyY);
            } else {
                keyFrame = new KeyFrame(Duration.seconds(dis / (packetSpeed)), keyX, keyY);
            }
        } else {
            if (pointpacket.PacketKind == PacketKind) {
                keyFrame = new KeyFrame(Duration.seconds(dis / (packetSpeed / 2)), keyX, keyY);
            } else {
                Interpolator accelerate = Interpolator.SPLINE(0.5, 0.0, 1.0, 1.0);
                keyX = new KeyValue(packet.layoutXProperty(), endX, accelerate);
                keyY = new KeyValue(packet.layoutYProperty(), endY, accelerate);
                keyFrame = new KeyFrame(Duration.seconds(dis / (packetSpeed / 2)), keyX, keyY);
            }
        }

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(1);

        scaleTransition = new ScaleTransition(Duration.seconds(0.0004 * packetSpeed), packet);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        fadeTransition = new FadeTransition(Duration.seconds(0.0004 * packetSpeed), packet);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        scaleTransition.setOnFinished(e -> {
            if (scaleTransition.getFromX() == 1.0) {
                group.getChildren().remove(packet);
                PacketSystem.movingPackets.remove(this);
                pointpacket.otherSystem.push_element(this);
                pointpacket.packetonLine = false;
                pointpacket.onSystem.lunch();
            } else {
                onScaleTransition = false;
                timeline.play();
            }
        });

        timeline.setOnFinished(e -> {
            onScaleTransition = true;

            scaleTransition.setFromX(1.0);
            scaleTransition.setFromY(1.0);
            scaleTransition.setToX(0.1);
            scaleTransition.setToY(0.1);

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
        scaleTransition.pause();
        fadeTransition.pause();
        timeline.pause();
    }

    public void stop() {
        scaleTransition.stop();
        fadeTransition.stop();
        timeline.stop();
    }

    public void delete() {
        pause();
        health.setText("Died");
        group.getChildren().remove(packet);
        PacketSystem.packs.getChildren().remove(packet);
        PacketSystem.movingPackets.remove(this);

        pointpacket.packetonLine = false;
        pointpacket.onSystem.lunch();

        Game.level.adddes();
    }

    public void ColorRefresh() {
        if (noiseCapacity == 3) {
            packet.setFill(Color.web("#f6c177"));
        }
        if (noiseCapacity == 2 && PacketKind == 2) {
            packet.setFill(Color.web("#9ccfd8"));
        }
        if (noiseCapacity == 2 && PacketKind != 2) {
            packet.setFill(Color.ORANGERED);
        }
        if (noiseCapacity == 1) {
            packet.setFill(Color.DARKRED);
        }
    }

    private double touchmove = 0.4;
    public double Xtranslate;
    public double Ytranslate;
    private double impactradius = 60;
    private double impactjump = 4;
    public boolean vis = false;
    public boolean inVanurable = false;

    public void touched(Double X, Double Y, Packet x, Packet y) {
        if (Game.ontemporal) {
            return;
        }
        SoundEffects.ImpactSoundEffect();
        int cnttt = 0;
        Text O = new Text("X" + cnttt);
        O.setFill(Color.GREENYELLOW);
        O.setFont(new Font(20));
        O.setTabSize(20);
        O.setLayoutX(X);
        O.setLayoutY(Y);
        // group.getChildren().add(O);
        for (Packet ps : PacketSystem.movingPackets) {
            // System.out.println("seen"+ ps.id);
            ps.vis = false;
            if (!Game.impacteffect) {
                ps.vis = true;
                if (ps.id == x.id || ps.id == y.id) {
                    ps.vis = false;
                }
            }
        }

        ArrayList<Packet> abouttodelete = new ArrayList<>();

        for (Packet ps : PacketSystem.movingPackets) {
            // System.out.println("touched"+ ps.id);
            if (!ps.vis) {
                if (!ps.inVanurable) {
                    if (Math.sqrt(Math.pow((ps.packet.getLayoutX() + ps.packet.getTranslateX() - X), 2) + Math.pow((ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y), 2)) <= impactradius) {
                        cnttt++;
                        O.setText("X" + cnttt);
                        if (ps.noiseCapacity <= 1) {
                            abouttodelete.add(ps);
                        } else {
                            if ((Math.signum(ps.packet.getLayoutX() + ps.packet.getTranslateX() - X)) < 0) {
                                ps.packet.setTranslateX(ps.packet.getTranslateX() + Math.max(touchmove * (ps.packet.getLayoutX() + ps.packet.getTranslateX() - X), (Math.signum(ps.packet.getLayoutX() + ps.packet.getTranslateX() - X)) * impactjump));
                            } else {
                                ps.packet.setTranslateX(ps.packet.getTranslateX() + Math.min(touchmove * (ps.packet.getLayoutX() + ps.packet.getTranslateX() - X), (Math.signum(ps.packet.getLayoutX() + ps.packet.getTranslateX() - X)) * impactjump));
                            }
                            if ((Math.signum(ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y)) < 0) {
                                ps.packet.setTranslateY(ps.packet.getTranslateY() + Math.max(touchmove * (ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y), (Math.signum(ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y)) * impactjump));
                            } else {
                                ps.packet.setTranslateY(ps.packet.getTranslateY() + Math.min(touchmove * (ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y), (Math.signum(ps.packet.getLayoutY() + ps.packet.getTranslateY() - Y)) * impactjump));
                            }
                            ps.Xtranslate = ps.packet.getTranslateX();
                            ps.Ytranslate = ps.packet.getTranslateY();
                            ps.noiseCapacity--;
                            ps.ColorRefresh();
                            ps.inVanurable = true;
                            Timeline timer = new Timeline(new KeyFrame(Duration.millis(1), e -> {
                            }));
                            timer.setCycleCount(100);
                            timer.play();
                            timer.setOnFinished(e -> {
                                ps.inVanurable = false;
                            });
                        }
                    }
                }
                ps.vis = true;
            }
        }

        for (Packet pcc : abouttodelete) {
            pcc.delete();
        }
    }

    public void doesTouch(Packet other) {
        Shape intersection = Shape.intersect(packet, other.packet);
        if (intersection.getBoundsInLocal().getWidth() > 0 && intersection.getBoundsInLocal().getHeight() > 0) {
            if (Game.conflicteffect) {
                touched((packet.getLayoutX() + packet.getTranslateX() + other.packet.getLayoutX() + other.packet.getTranslateX()) / 2, (packet.getLayoutY() + packet.getTranslateY() + other.packet.getLayoutY() + other.packet.getTranslateY()) / 2, this, other);
            }
        }
    }

    public boolean knockedout() {
        Shape intersection = Shape.intersect(packet, pointpacket.currentLine);
        Shape intersection2 = Shape.intersect(packet, pointpacket.bubble1);
        Shape intersection3 = Shape.intersect(packet, pointpacket.bubble2);

        if (onScaleTransition)
            return false;

        return !(((intersection3.getBoundsInLocal().getWidth() > 0 && intersection3.getBoundsInLocal().getHeight() > 0) || (intersection2.getBoundsInLocal().getWidth() > 0 && intersection2.getBoundsInLocal().getHeight() > 0) || (intersection.getBoundsInLocal().getWidth() > 0 && intersection.getBoundsInLocal().getHeight() > 0)));
    }
}
