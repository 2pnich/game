package com.example.test;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {
    private final int height = 48;
    private final int width = 48;
    private boolean gameStarted = true;
    private final int start = 0;
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private GraphicsContext gc;
    private final int level = 0;
    boolean flag = false;
    List<Enemy> enemyList = new ArrayList<>();
    Player player = new Player();
    Coin coin = new Coin();
    Image coinImg = new Image("roflan.png", height, width, false, false);
    Image grass = new Image("/grass.png", height, width, false, false);
    Image grassS = new Image("/grassS.png", height, width, false, false);
    Image tree = new Image("/tree.png", height, width, false, false);
    Image live = new Image("/Plives.png", height, width, false, false);

    private int[][] map = {                                         //0 - трава
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,},      //1- дерево
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},      //2 - трава с камнями
            {1, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,},
            {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,},
    };

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("КАЙФАРИК");
        InputStream iconStream = getClass().getResourceAsStream("/fav.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setIconified(true);

        Group root = new Group();
        Canvas canvas = new Canvas(768, 860);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 1000, 1000);
//
//        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
//        scene.setOnKeyReleased(event -> {
//            keys.put(event.getCode(), false);
//        });
        enemySpawn4();


        if (gameStarted) {
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    run(scene);
                }
            };
            timer.start();
        }
    }

    private void textSettings() {
        gc.setFill(Color.BLACK);            //ЧЕРНЫЙ ФОН
        gc.fillRect(0, 0, 1000, 1000);
        gc.setFont(Font.font(48));

        gc.setFill(Color.WHITE);            //КОЛИЧЕСТВО МОНЕТ
        gc.drawImage(coinImg, 0, 770);
        gc.fillText(toString(coin.getCoins()), 0, 810);

        gc.setFill(Color.WHITE);            //КОЛИЧЕСТВО ЖИЗНЕЙ
        gc.drawImage(live, 380, 770);
        gc.fillText(toString(Player.getLives()), 400, 810);

    }

    private void run(Scene scene) {
        if (gameStarted) {
            textSettings();
            drawBackground();
            coin.generate(gc);
            coin.gotCoin(gc);
            player.playerInput(scene, gc);
            player.playerDraw(gc);
            player.shootPressed(gc);
           enemyControl();
        } else
            ;
    }

    private void enemyControl() {
        for (int i = 0; i < 4; i++) {
            enemyList.get(i).enemyLogic(gc);
        }
        if (!enemyList.get(0).getState() && !enemyList.get(1).getState()
                && !enemyList.get(2).getState() && !enemyList.get(3).getState()) {
            enemyList.clear();
            enemySpawn4();
        }
    }

    private void enemySpawn4() {
        Enemy e1 = new Enemy();
        e1.setPosition(0, 360);
        enemyList.add(e1);
        Enemy e2 = new Enemy();
        e2.setPosition(360, 0);
        enemyList.add(e2);
        Enemy e3 = new Enemy();
        e3.setPosition(360, 860);
        enemyList.add(e3);
        Enemy e4 = new Enemy();
        e4.setPosition(860, 360);
        enemyList.add(e4);
    }

    private void drawBackground() {
        gc.setFill(Color.BLACK);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (map[i][j] == 0)
                    gc.drawImage(grass, i * height, j * width);
                else if (map[i][j] == 1)
                    gc.drawImage(tree, i * height, j * width);
                else if (map[i][j] == 2)
                    gc.drawImage(grassS, i * height, j * width);
            }
        }
    }

    private String toString(int coins) {
        return "   X" + coins;
    }

    public static void main(String[] args) {
        launch();
    }
}