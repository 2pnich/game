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
import javafx.scene.image.ImageView;
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
    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private GraphicsContext gc;
    private int level = 1;
    private boolean bossSpawned = false;
    private String entity;
    ArrayList<Boss> boss = new ArrayList<Boss>();
    List<Enemy> enemyList = new ArrayList<>();
    Player player = new Player();
    Coin coin = new Coin();

    Image lose = new Image("lose.png", 768, 100, false, false);
    Image menu = new Image("start_men.png", 768, 860, false, false);
    Image but = new Image("button.png", 140, 80, false, false);
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
        stage.setTitle("КАЙФАРИК");                 //ФАВИКОНКА
        InputStream iconStream = getClass().getResourceAsStream("/fav.png");
        assert iconStream != null;
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setIconified(true);

        ImageView imageV = new ImageView(menu);
        ImageView imageView = new ImageView(but);

        Button button = new Button("", imageView);  //КНОПКА СТАРТ
        button.setPrefWidth(140);
        button.setPrefHeight(80);
        button.setLayoutX(300);
        button.setLayoutY(400);

        Group root = new Group();
        Canvas canvas = new Canvas(768, 860);
        root.getChildren().add(imageV);
        root.getChildren().add(button);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        gc = canvas.getGraphicsContext2D();
        enemySpawn4();
        EventHandler<ActionEvent> ev = new EventHandler<ActionEvent>() {    //КНОПКА СТАРТ
            public void handle(ActionEvent e) {
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        run(scene);
                    }
                };
                timer.start();
                button.setDisable(true);
            }
        };
        button.setOnAction(ev);
    }

    private void textSettings() {
        gc.setFill(Color.BLACK);            //ЧЕРНЫЙ ФОН
        gc.fillRect(0, 0, 1000, 1000);
        gc.setFont(Font.font(48));

        gc.setFill(Color.WHITE);            //КОЛИЧЕСТВО МОНЕТ
        gc.drawImage(coinImg, 0, 770);
        gc.fillText(toString(coin.getCoins()), 0, 810);

        gc.setFill(Color.WHITE);            //КОЛИЧЕСТВО ЖИЗНЕЙ
        gc.drawImage(live, 550, 770);
        gc.fillText(toString(player.getLives()), 600, 810);

        gc.setFill(Color.WHITE);            //УРОВЕНЬ
        gc.fillText("Уровень: ", 150, 810);
        gc.fillText(toString(level), 330, 810);

    }

    private void run(Scene scene) {        //ИГРОВОЙ ЦИКЛ
        if (player.getLives() > 0) {
            textSettings();
            drawBackground();
            coin.generate(gc);
            coin.gotCoin(gc);
            player.playerInput(scene, gc);
            player.playerDraw(gc);
            player.shootPressed(gc);
            enemyControl();
        } else {
            gc.drawImage(lose, 0, 350);
        }
    }

    private void enemyControl() {
        ArrayList<Enemy> deadEnemy = new ArrayList<Enemy>();
        for (Enemy enemy : enemyList) {           //ЛОГИКА ПРОТИВНИКОВ
            if (enemy.getState())
                enemy.enemyLogic(gc, player, "0");

            if (bossSpawned) {
                entity = "boss";
                boss.get(0).enemyLogic(gc, player, entity);
                if(!boss.get(0).getState())
                    bossSpawned = false;
            }
            if (!enemy.getState()) {
                deadEnemy.add(enemy);
            }
        }

        //ЕСЛИ ВСЕ МЕРТВЫ -ОЧИСТКА
        if (enemyList.size() == deadEnemy.size()) {
            enemyList.clear();
        }

        //ЛВЛ 2
        if (level == 1 && enemyList.isEmpty()) {
            level++;
            deadEnemy.clear();
//            enemyList.clear();
            enemySpawn4();
            enemySpawn6();
        }

        //ЛВЛ 3(БОСС)
        if (level % 2 == 0 && enemyList.isEmpty()) {
            level++;
            if (!bossSpawned) {
                bossSpawn();
            }
        }

        //>ЛВЛ 3
        if (level > 2 && enemyList.isEmpty()) {
            level++;
            if (deadEnemy.size() > 9) {
                deadEnemy.clear();
            }
            enemySpawn4();
            enemySpawn6();
        }
    }

    private void bossSpawn() {
        Boss boss1 = new Boss();
        boss1.setPosition(0, 360);
        boss.add(boss1);
        boss1.enemyLogic(gc, player, "boss");
        bossSpawned = true;
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

    private void enemySpawn6() {
        Enemy e5 = new Enemy();
        e5.setPosition(0, 280);
        enemyList.add(e5);
        Enemy e6 = new Enemy();
        e6.setPosition(280, 0);
        enemyList.add(e6);
        Enemy e7 = new Enemy();
        e7.setPosition(280, 830);
        enemyList.add(e7);
        Enemy e8 = new Enemy();
        e8.setPosition(750, 200);
        enemyList.add(e8);
        Enemy e9 = new Enemy();
        e9.setPosition(650, 200);
        enemyList.add(e9);
        Enemy e10 = new Enemy();
        e10.setPosition(600, 200);
        enemyList.add(e10);
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