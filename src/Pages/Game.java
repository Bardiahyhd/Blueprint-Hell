package Pages;

import Config.Config;
import GameSystem.GameSystem;
import Graphics.ButtonConfig;
import Graphics.Packet;
import Graphics.PacketSystem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class Game {

    private static String timeformatter(int totalSeconds) {

        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public static Text coins = new Text();
    public static Text packets = new Text();
    public static Text lostpackets = new Text();

    public static Text time;
    public static Rectangle timeBar;
    public static Rectangle timeUsedBar = new Rectangle(124, 74, 392, 32);
    public static Text wire;
    public static Text timelimit;
    public static Rectangle wireBar;
    public static Rectangle wireUsedBar = new Rectangle(124, 24, 392, 32);

    private static final double StageWidth = parseDouble(Config.Config.get("StageWidth").toString());
    private static final double StageHeight = parseDouble(Config.Config.get("StageHeight").toString());

    public static Text temporal;
    public static Rectangle temporalBar;
    public static Rectangle temporalUsedBar = new Rectangle(224, StageHeight - 100 + 4, 392, 32);

    private static ArrayList<PacketSystem> systemsArray = new ArrayList<>();

    public static Button play = new Button();

    public static boolean isPlayable() {
        for (PacketSystem ps : systemsArray) {
            if (!ps.systemLight) {
                play.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
                ButtonConfig.show.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
                return false;
            }
        }
        play.setStyle("-fx-background-color: #409106;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        ButtonConfig.show.setStyle("-fx-background-color: #409106;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        return true;
    }

    private static int cnt = 0;
    public static GameSystem level;

    public static boolean ontemporal = false;
    public static boolean live = false;

    public static boolean onShop = false;

    public static void applyStrokeToAllShapes(Node node) {
        if (node instanceof Shape) {
            Shape shape = (Shape) node;
            if (!(shape instanceof Line)) {
                shape.setStroke(Color.BLACK);
                shape.setStrokeWidth(1);
            }
        }

        if (node instanceof Parent) {
            Parent parent = (Parent) node;
            for (Node child : parent.getChildrenUnmodifiable()) {
                applyStrokeToAllShapes(child);
            }
        }
    }

    public static boolean ended = false;

    public static boolean impacteffect = true;
    public static boolean conflicteffect = true;

    public static void StartGame(Stage PrimaryStage, MediaPlayer mediaPlayer, int lvl) {
        Group HUD = new Group();
        Pane systems = new Pane();
        Group wires = new Group();
        Group finalWires = new Group();
        Group gameNodes = new Group(systems, finalWires, wires, PacketSystem.packs, HUD);
        Pane nodes = new Pane(gameNodes);
        Scene Game = new Scene(nodes);

        ended = false;

        impacteffect = true;
        conflicteffect = true;

        Timeline infinitetimer = new Timeline(new KeyFrame(Duration.millis(100), e -> {
            applyStrokeToAllShapes(nodes);
        }));
        infinitetimer.setCycleCount(Timeline.INDEFINITE);
        // infinitetimer.play();

        live = false;
        if (lvl == 1) {
            level = new GameSystem(3000, 2, 3, 75);
        } else if (lvl == 2) {
            level = new GameSystem(6000, 10, 12, 240);
        }

        nodes.setCache(false);

        onShop = false;

        Timeline impactdisable = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
        }));
        infinitetimer.setCycleCount(1);

        impactdisable.setOnFinished(e -> {
            impacteffect = true;
        });

        Timeline conflictdisable = new Timeline(new KeyFrame(Duration.seconds(5), e -> {

        }));
        infinitetimer.setCycleCount(1);

        conflictdisable.setOnFinished(e -> {
            conflicteffect = true;
        });

        Rectangle item1 = new Rectangle(120, 135, 400, 50);
        item1.setFill(Color.web("#908caa"));
        item1.setArcWidth(35);
        item1.setArcHeight(35);

        Text item1text = new Text("O' Atar (3 coin) : Disables packets impact effect for 10 second.");
        item1text.setLayoutX(135);
        item1text.setLayoutY(165);
        item1text.setFont(Font.font("Comic Sans MS", 12));
        item1text.setFill(Color.WHITE);
        item1text.setMouseTransparent(true);

        item1.setOnMouseClicked(e -> {
            if (live && !ontemporal && level.coins > 2) {
                level.coins -= 3;
                coins.setText("Coins :" + level.coins);
                impacteffect = false;
            }
        });

        Rectangle item2 = new Rectangle(120, 195, 400, 50);
        item2.setFill(Color.web("#908caa"));
        item2.setArcWidth(35);
        item2.setArcHeight(35);

        Text item2text = new Text("O’ Airyaman (4 coin) : Disables packets conflict effect for 5 second.");
        item2text.setLayoutX(135);
        item2text.setLayoutY(225);
        item2text.setFont(Font.font("Comic Sans MS", 12));
        item2text.setFill(Color.WHITE);
        item2text.setMouseTransparent(true);

        item2.setOnMouseClicked(e -> {
            if (live && !ontemporal && level.coins > 3) {
                level.coins -= 4;
                coins.setText("Coins :" + level.coins);
                conflicteffect = false;
            }
        });

        Rectangle item3 = new Rectangle(120, 255, 400, 50);
        item3.setFill(Color.web("#908caa"));
        item3.setArcWidth(35);
        item3.setArcHeight(35);

        Text item3text = new Text("O' Anahita (5 coin) : Sets the noise of every packets to zero.");
        item3text.setLayoutX(135);
        item3text.setLayoutY(285);
        item3text.setFont(Font.font("Comic Sans MS", 12));
        item3text.setFill(Color.WHITE);
        item3text.setMouseTransparent(true);

        item3.setOnMouseClicked(e -> {
            if (live && !ontemporal && level.coins >= 5) {
                level.coins -= 5;
                coins.setText("Coins :" + level.coins);

                for (PacketSystem x : systemsArray) {
                    for (Packet y : x.packetstored) {
                        if (y.PacketKind == 1) {
                            y.noiseCapacity = 3;
                            y.ColorRefresh();
                        } else {
                            y.noiseCapacity = 2;
                            y.ColorRefresh();
                        }
                    }
                }

                for (Packet y : PacketSystem.movingPackets) {
                    if (y.PacketKind == 1) {
                        y.noiseCapacity = 3;
                        y.ColorRefresh();
                    } else {
                        y.noiseCapacity = 2;
                        y.ColorRefresh();
                    }
                }
            }
        });

        item1.setStroke(Color.BLACK);
        item1.setStrokeWidth(3);
        item2.setStroke(Color.BLACK);
        item2.setStrokeWidth(3);
        item3.setStroke(Color.BLACK);
        item3.setStrokeWidth(3);

        Group shop = new Group(item1, item1text, item2, item2text, item3, item3text);

        Circle shopCircle = new Circle(55, 155, 35);
        shopCircle.setStroke(Color.BLACK);
        shopCircle.setStrokeWidth(3);
        shopCircle.setFill(Color.WHITE);
        Image cart = new Image("cart.png");
        ImageView IV = new ImageView(cart);
        IV.setFitWidth(50);
        IV.setFitHeight(50);
        IV.setLayoutX(28);
        IV.setLayoutY(133);
        IV.setMouseTransparent(true);

        HUD.getChildren().add(shopCircle);
        HUD.getChildren().add(IV);

        ButtonConfig.temporal(HUD);

        wire = new Text();
        wire.setText("Wire");
        wire.setX(20);
        wire.setY(50);
        wire.setFont(Font.font("Comic Sans MS", 37));
        wire.setFill(Color.WHITE);

        wireBar = new Rectangle(120, 20, 400, 40);
        wireBar.setFill(Color.web("#908caa"));
        wireBar.setArcWidth(35);
        wireBar.setArcHeight(35);

        wireUsedBar.setWidth(392);
        wireUsedBar.setFill(Color.web("#6be612"));
        wireUsedBar.setArcWidth(33);
        wireUsedBar.setArcHeight(33);

        HUD.getChildren().add(wire);
        HUD.getChildren().add(wireBar);
        HUD.getChildren().add(wireUsedBar);

        time = new Text();
        time.setText("Time");
        time.setX(20);
        time.setY(100);
        time.setFont(Font.font("Comic Sans MS", 37));
        time.setFill(Color.WHITE);

        timeBar = new Rectangle(120, 70, 400, 40);
        timeBar.setFill(Color.web("#908caa"));
        timeBar.setArcWidth(35);
        timeBar.setArcHeight(35);

        timeUsedBar.setWidth(392);
        timeUsedBar.setFill(Color.web("#6be612"));
        timeUsedBar.setArcWidth(33);
        timeUsedBar.setArcHeight(33);

        timelimit = new Text(timeformatter(level.timelimit));
        timelimit.setFill(Color.WHITE);
        timelimit.setLayoutX(StageWidth / 2 - 50);
        timelimit.setLayoutY(100);
        timelimit.setFont(Font.font("Comic Sans MS", 37));
        timelimit.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        HUD.getChildren().add(time);
        HUD.getChildren().add(timeBar);
        HUD.getChildren().add(timeUsedBar);
        HUD.getChildren().add(timelimit);

        coins.setText("Coins : 0");
        coins.setFill(Color.WHITE);
        coins.setLayoutX(StageWidth / 2 - 50);
        coins.setLayoutY(50);
        coins.setFont(Font.font("Comic Sans MS", 37));
        coins.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        HUD.getChildren().add(coins);

        packets.setText("Packets : " + level.packetsReceived + "/" + level.totalPackets);
        packets.setFill(Color.WHITE);
        packets.setLayoutX(StageWidth / 2 + 150);
        packets.setLayoutY(50);
        packets.setFont(Font.font("Comic Sans MS", 37));
        packets.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        HUD.getChildren().add(packets);

        lostpackets.setText("Packet Loss : " + level.destroyedpackets + "/" + level.totalPackets);
        lostpackets.setFill(Color.WHITE);
        lostpackets.setLayoutX(StageWidth / 2 + 150);
        lostpackets.setLayoutY(100);
        lostpackets.setFont(Font.font("Comic Sans MS", 37));
        lostpackets.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        HUD.getChildren().add(lostpackets);

        play = ButtonConfig.Button("Play", +2.5);
        play.setLayoutX(StageWidth - 310);
        play.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");
        HUD.getChildren().add(play);

        Game.setFill(Color.web("#232136"));
        nodes.setBackground(new Background(new BackgroundFill(Color.web("#232136"), CornerRadii.EMPTY, Insets.EMPTY)));

        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            cnt++;
            timeUsedBar.setWidth((1.0 - ((((double) (cnt / 1000)) / level.timelimit))) * 392);
            timelimit.setText(timeformatter((int) ((level.timelimit - ((double) cnt / 1000)))));
        }));
        timer.setCycleCount(level.timelimit * 1000);

        PacketSystem p1;
        PacketSystem p2;

        if (lvl == 1) {
            p1 = new PacketSystem(Game, systems, wires, finalWires, 0, 1, 1, 2, StageWidth / 2 - 400, StageHeight / 2 + 100, true, level);
        } else if (lvl == 2) {
            p1 = new PacketSystem(Game, systems, wires, finalWires, 0, 0, 1, 2, StageWidth / 2 - 500, StageHeight / 2, true, level);
            p2 = new PacketSystem(Game, systems, wires, finalWires, 3, 1, 0, 0, StageWidth / 2 + 450, StageHeight / 2, true, level);

            systemsArray.add(p2);
        } else {
            p1 = null;
        }
        systemsArray.add(p1);

        if (lvl == 1) {
            PacketSystem s1 = new PacketSystem(Game, systems, wires, finalWires, 2, 1, 0, 1, StageWidth / 2 + 300, StageHeight / 2, false, level);
            PacketSystem s2 = new PacketSystem(Game, systems, wires, finalWires, 1, 1, 2, 0, StageWidth / 2 + 20, StageHeight / 2 - 100, false, level);

            systemsArray.add(s1);
            systemsArray.add(s2);
        } else if (lvl == 2) {
            PacketSystem s2 = new PacketSystem(Game, systems, wires, finalWires, 1, 1, 2, 0, StageWidth / 2 - 300, StageHeight / 2 - 100, false, level);
            PacketSystem s1 = new PacketSystem(Game, systems, wires, finalWires, 1, 1, 0, 1, StageWidth / 2 - 150, StageHeight / 2 + 100, false, level);
            PacketSystem s4 = new PacketSystem(Game, systems, wires, finalWires, 1, 0, 2, 1, StageWidth / 2, StageHeight / 2 - 100, false, level);
            PacketSystem s3 = new PacketSystem(Game, systems, wires, finalWires, 1, 1, 1, 1, StageWidth / 2 + 200, StageHeight / 2 + 150, false, level);
            PacketSystem s5 = new PacketSystem(Game, systems, wires, finalWires, 1, 1, 2, 0, StageWidth / 2 + 200, StageHeight / 2 - 50, false, level);

            systemsArray.add(s1);
            systemsArray.add(s2);
            systemsArray.add(s3);
            systemsArray.add(s4);
            systemsArray.add(s5);
        }

        Group random = new Group();
        Pane random2 = new Pane();
        Scene random3 = new Scene(random2);
        GameSystem random4 = new GameSystem(0, 0, 0, 0);
        PacketSystem xpprime = new PacketSystem(random3, random2, random, random, 0, 0, 0, 0, 0, 0, false, random4);
        Packet xp0 = new Packet(random, 0, 0, 0, xpprime);
        Packet xp = new Packet(random2, 1, 0, 0, 0, 0, xp0);


        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
            if (level.packettri > 0) {

                xp.PacketKind = 1;
                xp.noiseCapacity = 3;

                p1.packetstored.add(xp);
                level.packettri--;
            } else if (level.packetrect > 0) {

                xp.PacketKind = 2;
                xp.noiseCapacity = 2;

                p1.packetstored.add(xp);
                level.packetrect--;
            }
            p1.lunch();
            for (Packet x : PacketSystem.movingPackets) {
                x.health.setText(Integer.toString(x.noiseCapacity));
            }

            /*
            coins.setLayoutY(600);
            coins.setLayoutX(200);
            if (impacteffect && conflicteffect) {
                coins.setText("conflicteffect true | impacteffect true");
            }
            if (!impacteffect && conflicteffect) {
                coins.setText("conflicteffect false | impacteffect true");
            }
            if (impacteffect && !conflicteffect) {
                coins.setText("conflicteffect true | impacteffect false");
            }
            if (!impacteffect && !conflicteffect) {
                coins.setText("conflicteffect false | impacteffect false");
            }
            */

            ArrayList<Packet> abouttodelete = new ArrayList<>();

            for (Packet x : PacketSystem.movingPackets) {
                if (x.knockedout()) {
                    abouttodelete.add(x);
                }
            }


            for (Packet pcc : abouttodelete) {
                pcc.delete();
            }

            for (Packet x : PacketSystem.movingPackets) {
                for (Packet y : PacketSystem.movingPackets) {
                    if (x.id != y.id) {
                        if (!x.inVanurable && !y.inVanurable) {
                            x.doesTouch(y);
                        }
                    }
                }
            }
        }));

        Group Pause = new Group();

        Text gameover = new Text("You Lost!");
        gameover.setFont(Font.font("Comic Sans MS", 150));
        gameover.setFill(Color.WHITE);
        gameover.setLayoutX(StageWidth / 2 - 100);
        gameover.setLayoutY(StageHeight / 2 - 100);
        gameover.setLayoutX(StageWidth / 2 - gameover.getLayoutBounds().getWidth() / 2);

        Button playagain = ButtonConfig.Button("Try Again", 0);
        playagain.setOnAction(e -> {
            systemsArray.clear();
            PacketSystem.movingPackets.clear();
            PacketSystem.rectOut.clear();
            PacketSystem.rectIn.clear();
            PacketSystem.triangleIn.clear();
            PacketSystem.triangleOut.clear();
            PacketSystem.packs.getChildren().clear();
            timeline.stop();
            timer.stop();
            cnt = 0;
            StartGame(PrimaryStage, mediaPlayer, lvl);
        });

        Button backtomenu = ButtonConfig.Button("Menu", 1);
        backtomenu.setOnAction(e -> {
            systemsArray.clear();
            PacketSystem.movingPackets.clear();
            PacketSystem.rectOut.clear();
            PacketSystem.rectIn.clear();
            PacketSystem.triangleIn.clear();
            PacketSystem.triangleOut.clear();
            PacketSystem.packs.getChildren().clear();
            timeline.stop();
            timer.stop();
            cnt = 0;
            Menu.MenuCreator(PrimaryStage, mediaPlayer);
        });

        Group end = new Group(gameover, playagain, backtomenu);

        Timeline gamechecktimeline = new Timeline();

        KeyFrame kf = new KeyFrame(Duration.millis(1), event -> {
            if (level.destroyedpackets + level.packetsReceived == level.totalPackets && !ontemporal) {
                // game over won
                ended = true;
                gamechecktimeline.stop();
                for (Packet x : PacketSystem.movingPackets) {
                    x.delete();
                }
                PacketSystem.movingPackets.clear();
                GaussianBlur gaussianBlur = new GaussianBlur();
                gaussianBlur.setRadius(10.5);
                gameNodes.setDisable(true);
                gameNodes.setEffect(gaussianBlur);
                gameNodes.getChildren().remove(wires);
                timeline.pause();
                timer.pause();
                if (lvl == 2) {
                    for (Packet x : PacketSystem.movingPackets) {
                        x.delete();
                    }
                    gameover.setText("You Won!");
                    gameover.setLayoutX(StageWidth / 2 - gameover.getLayoutBounds().getWidth() / 2);
                    Button backtomenu2 = ButtonConfig.Button("Menu", 0);
                    backtomenu2.setOnAction(e -> {
                        systemsArray.clear();
                        PacketSystem.movingPackets.clear();
                        PacketSystem.rectOut.clear();
                        PacketSystem.rectIn.clear();
                        PacketSystem.triangleIn.clear();
                        PacketSystem.triangleOut.clear();
                        PacketSystem.packs.getChildren().clear();
                        timeline.stop();
                        timer.stop();
                        cnt = 0;
                        Menu.MenuCreator(PrimaryStage, mediaPlayer);
                    });

                    end.getChildren().add(backtomenu2);
                    end.getChildren().remove(playagain);
                    end.getChildren().remove(backtomenu);
                } else {
                    for (Packet x : PacketSystem.movingPackets) {
                        x.delete();
                    }
                    gameover.setText("You Won!");
                    gameover.setLayoutX(StageWidth / 2 - gameover.getLayoutBounds().getWidth() / 2);
                    Button nextlvl = ButtonConfig.Button("Next level", 0);
                    nextlvl.setOnAction(e -> {
                        systemsArray.clear();
                        PacketSystem.movingPackets.clear();
                        PacketSystem.rectOut.clear();
                        PacketSystem.rectIn.clear();
                        PacketSystem.triangleIn.clear();
                        PacketSystem.triangleOut.clear();
                        PacketSystem.packs.getChildren().clear();
                        timeline.stop();
                        timer.stop();
                        cnt = 0;
                        StartGame(PrimaryStage, mediaPlayer, 2);
                    });
                    Button backtomenu2 = ButtonConfig.Button("Menu", 1);
                    backtomenu2.setOnAction(e -> {
                        systemsArray.clear();
                        PacketSystem.movingPackets.clear();
                        PacketSystem.rectOut.clear();
                        PacketSystem.rectIn.clear();
                        PacketSystem.triangleIn.clear();
                        PacketSystem.triangleOut.clear();
                        PacketSystem.packs.getChildren().clear();
                        timeline.stop();
                        timer.stop();
                        cnt = 0;
                        Menu.MenuCreator(PrimaryStage, mediaPlayer);
                    });
                    end.getChildren().add(nextlvl);
                    end.getChildren().add(backtomenu2);
                    end.getChildren().remove(playagain);
                    end.getChildren().remove(backtomenu);
                }
                nodes.getChildren().add(end);
            }
            if (level.destroyedpackets > level.totalPackets / 2 && !ontemporal) {
                // lose
                ended = true;
                gamechecktimeline.stop();
                GaussianBlur gaussianBlur = new GaussianBlur();
                gaussianBlur.setRadius(10.5);
                gameNodes.setDisable(true);
                gameNodes.setEffect(gaussianBlur);
                gameNodes.getChildren().remove(wires);
                timeline.pause();
                for (Packet packet : PacketSystem.movingPackets) {
                    packet.pause();
                }
                timer.pause();
                nodes.getChildren().add(end);
            }

            if (ontemporal && live) {
                if (((int) (Math.ceil((((double) cnt / 1000))))) >= Math.min(ButtonConfig.temporalNum, level.timelimit - 2)) {
                    timeline.pause();
                    for (Packet packet : PacketSystem.movingPackets) {
                        packet.pause();
                    }
                    timer.pause();
                }
            }
        });

        gamechecktimeline.getKeyFrames().add(kf);
        gamechecktimeline.stop();

        gamechecktimeline.setCycleCount(Timeline.INDEFINITE);

        timer.setOnFinished(e -> {
            // time over - lose
            ended = true;
            gamechecktimeline.stop();
            GaussianBlur gaussianBlur = new GaussianBlur();
            gaussianBlur.setRadius(10.5);
            gameNodes.setDisable(true);
            gameNodes.setEffect(gaussianBlur);
            gameNodes.getChildren().remove(wires);
            timeline.pause();
            gamechecktimeline.pause();
            for (Packet packet : PacketSystem.movingPackets) {
                packet.pause();
            }
            timer.pause();
            nodes.getChildren().add(end);
        });

        timeline.setCycleCount(Timeline.INDEFINITE);


        ButtonConfig.show.setOnAction(e -> {
            if (isPlayable() && ontemporal == live && !onShop && !ended) {
                if (ButtonConfig.show.getText().equalsIgnoreCase("show")) {
                    ontemporal = true;
                    ButtonConfig.show.setText("Cancel");
                    live = true;
                    timeline.playFromStart();
                    timer.playFromStart();
                    gamechecktimeline.playFromStart();
                } else {
                    ontemporal = false;
                    live = false;
                    PacketSystem.packs.getChildren().clear();
                    ButtonConfig.show.setText("Show");
                    resetStatus(lvl);
                    timeline.stop();
                    timer.stop();
                    gamechecktimeline.stop();
                    for (Packet packet : PacketSystem.movingPackets) {
                        packet.stop();
                    }
                    PacketSystem.movingPackets.clear();
                    PacketSystem.packs.getChildren().clear();
                    for (PacketSystem ps : systemsArray) {
                        for (Packet pc : ps.outputPacketTriangle) {
                            pc.packetonLine = false;
                        }
                        for (Packet pc : ps.outputPacketRect) {
                            pc.packetonLine = false;
                        }
                        ps.packetstored.clear();
                        ps.text.setText(Integer.toString(0));
                    }
                    cnt = 0;
                }
            }
        });

        play.setOnAction(e -> {
            if (isPlayable() && !ontemporal) {
                if (play.getText().equalsIgnoreCase("Play")) {
                    play.setText("Reset");
                    live = true;
                    timeline.playFromStart();
                    timer.playFromStart();
                    gamechecktimeline.playFromStart();
                } else {
                    live = false;
                    PacketSystem.packs.getChildren().clear();
                    play.setText("Play");
                    resetStatus(lvl);
                    timeline.stop();
                    timer.stop();
                    impacteffect = true;
                    conflicteffect = true;
                    impactdisable.stop();
                    conflictdisable.stop();
                    gamechecktimeline.stop();
                    for (Packet packet : PacketSystem.movingPackets) {
                        packet.stop();
                    }
                    PacketSystem.movingPackets.clear();
                    PacketSystem.packs.getChildren().clear();
                    for (PacketSystem ps : systemsArray) {
                        for (Packet pc : ps.outputPacketTriangle) {
                            pc.packetonLine = false;
                        }
                        for (Packet pc : ps.outputPacketRect) {
                            pc.packetonLine = false;
                        }
                        ps.packetstored.clear();
                        ps.text.setText(Integer.toString(0));
                    }
                    cnt = 0;
                }
            }
        });

        PrimaryStage.setScene(Game);

        Button back = ButtonConfig.Button("Menu", +1);
        back.setOnAction(e -> {
            systemsArray.clear();
            PacketSystem.movingPackets.clear();
            PacketSystem.rectOut.clear();
            PacketSystem.rectIn.clear();
            PacketSystem.triangleIn.clear();
            PacketSystem.triangleOut.clear();
            PacketSystem.packs.getChildren().clear();
            timeline.stop();
            timer.stop();
            gamechecktimeline.stop();
            cnt = 0;
            Menu.MenuCreator(PrimaryStage, mediaPlayer);
        });

        Button play2 = ButtonConfig.Button("Play", -1.7);
        play2.setOnAction(e -> {
            nodes.getChildren().remove(Pause);
            gameNodes.setDisable(false);
            gameNodes.setEffect(null);
            gameNodes.getChildren().add(wires);
            if (live) {
                for (Packet packet : PacketSystem.movingPackets) {
                    packet.play();
                }
                timer.play();
                gamechecktimeline.play();
                timeline.play();
                if (!conflicteffect) {
                    conflictdisable.play();
                }
                if (!impacteffect) {
                    impactdisable.play();
                }
            }
        });

        Group Buttons = new Group(play2, back);
        Pause.getChildren().add(Buttons);

        ButtonConfig.VolumeSlider(Buttons, mediaPlayer);

        Game.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE && !onShop) {
                if (nodes.getChildren().contains(Pause)) {
                    nodes.getChildren().remove(Pause);
                    gameNodes.setDisable(false);
                    gameNodes.setEffect(null);
                    gameNodes.getChildren().add(wires);
                    if (live) {
                        for (Packet packet : PacketSystem.movingPackets) {
                            packet.play();
                        }
                        timeline.play();
                        timer.play();
                        gamechecktimeline.play();
                        if (!conflicteffect) {
                            conflictdisable.play();
                        }
                        if (!impacteffect) {
                            impactdisable.play();
                        }
                    }
                } else {
                    GaussianBlur gaussianBlur = new GaussianBlur();
                    gaussianBlur.setRadius(10.5);
                    gameNodes.setDisable(true);
                    gameNodes.setEffect(gaussianBlur);
                    gameNodes.getChildren().remove(wires);
                    nodes.getChildren().add(Pause);
                    if (live) {
                        timeline.pause();
                        for (Packet packet : PacketSystem.movingPackets) {
                            packet.pause();
                        }
                        timer.pause();
                        gamechecktimeline.stop();
                        if (!conflicteffect) {
                            conflictdisable.pause();
                        }
                        if (!impacteffect) {
                            impactdisable.pause();
                        }
                    }
                }
            }
        });

        shopCircle.setOnMouseClicked(e -> {
            if (!onShop) {
                if (live && !ontemporal) {
                    timeline.pause();
                    for (Packet packet : PacketSystem.movingPackets) {
                        packet.pause();
                    }
                    timer.pause();
                    gamechecktimeline.stop();
                }
                if (!conflicteffect) {
                    conflictdisable.pause();
                }
                if (!impacteffect) {
                    impactdisable.pause();
                }
                onShop = true;
                nodes.getChildren().add(shop);
            } else {
                if (live && !ontemporal) {
                    for (Packet packet : PacketSystem.movingPackets) {
                        packet.play();
                    }
                    timeline.play();
                    timer.play();
                    gamechecktimeline.play();
                }
                onShop = false;
                if (!conflicteffect) {
                    conflictdisable.play();
                }
                if (!impacteffect) {
                    impactdisable.play();
                }
                nodes.getChildren().remove(shop);
            }
        });

    }

    private static void resetStatus(int lvl) {
        if (lvl == 1) {
            level.packetrect = 2;
            level.packettri = 3;
            level.coins = 0;
            level.destroyedpackets = 0;
            level.packetsReceived = 0;
        } else if (lvl == 2) {
            level.packetrect = 10;
            level.packettri = 12;
            level.coins = 0;
            level.destroyedpackets = 0;
            level.packetsReceived = 0;
        }
        live = false;

        wire = new Text();
        wire.setText("Wire");
        wire.setX(20);
        wire.setY(50);
        wire.setFont(Font.font("Comic Sans MS", 37));
        wire.setFill(Color.WHITE);

        wireBar = new Rectangle(120, 20, 400, 40);
        wireBar.setFill(Color.web("#908caa"));
        wireBar.setArcWidth(35);
        wireBar.setArcHeight(35);

        wireUsedBar.setWidth(392);
        wireUsedBar.setFill(Color.web("#6be612"));
        wireUsedBar.setArcWidth(33);
        wireUsedBar.setArcHeight(33);

        time = new Text();
        time.setText("Time");
        time.setX(20);
        time.setY(100);
        time.setFont(Font.font("Comic Sans MS", 37));
        time.setFill(Color.WHITE);

        timeBar = new Rectangle(120, 70, 400, 40);
        timeBar.setFill(Color.web("#908caa"));
        timeBar.setArcWidth(35);
        timeBar.setArcHeight(35);

        timeUsedBar.setWidth(392);
        timeUsedBar.setFill(Color.web("#6be612"));
        timeUsedBar.setArcWidth(33);
        timeUsedBar.setArcHeight(33);

        timelimit.setText(timeformatter(level.timelimit));
        timelimit.setFill(Color.WHITE);
        timelimit.setLayoutX(StageWidth / 2 - 50);
        timelimit.setLayoutY(100);
        timelimit.setFont(Font.font("Comic Sans MS", 37));
        timelimit.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        coins.setText("Coins : 0");
        coins.setFill(Color.WHITE);
        coins.setLayoutX(StageWidth / 2 - 50);
        coins.setLayoutY(50);
        coins.setFont(Font.font("Comic Sans MS", 37));
        coins.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        packets.setText("Packets : " + level.packetsReceived + "/" + level.totalPackets);
        packets.setFill(Color.WHITE);
        packets.setLayoutX(StageWidth / 2 + 150);
        packets.setLayoutY(50);
        packets.setFont(Font.font("Comic Sans MS", 37));
        packets.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        lostpackets.setText("Packet Loss : " + level.destroyedpackets + "/" + level.totalPackets);
        lostpackets.setFill(Color.WHITE);
        lostpackets.setLayoutX(StageWidth / 2 + 150);
        lostpackets.setLayoutY(100);
        lostpackets.setFont(Font.font("Comic Sans MS", 37));
        lostpackets.setStyle("-fx-background-color: #191724;" + "-fx-padding: 10 20;" + "-fx-background-radius: 30;");

        timeUsedBar.setWidth(392);
    }
}
