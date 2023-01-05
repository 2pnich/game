package com.example.test;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player extends Actor {
    private final int bulletSpeed;
    private final int speed;
    private static int lives;
    private static int positionY = 512;
    private static int positionX = 512;
    private int bulletX;
    private int bulletY;
    String[] dir = {"", "", "", ""};
    String[] dirB = {"", "", "", ""};
    enum bulletDir {UP, DOWN, LEFT, RIGHT}

    Image player = new Image("player.png", height, width, false, false);
    Image playerL = new Image("player_b.png", height, width, false, false);
    List<Image> bullet = new ArrayList<>();

    public Player() {
//        animTimer = 0;
//        animSpeed = 0;
        bulletSpeed = 16;
        lives = 3;
        speed = 32;
    }

    public void bulletMoveUp(GraphicsContext gc) {
        bulletY -= bulletSpeed;
        gc.setFill(Color.BLACK);
        gc.fillRect(bulletX, bulletY, 3, 5);
    }

    public void bulletMoveDown(GraphicsContext gc) {
        bulletY += bulletSpeed;
        gc.setFill(Color.BLACK);
        gc.fillRect(bulletX, bulletY, 3, 5);
    }

    public void bulletMoveLeft(GraphicsContext gc) {
        bulletX -= bulletSpeed;
        gc.setFill(Color.BLACK);
        gc.fillRect(bulletX, bulletY, 3, 5);
    }

    public void bulletMoveRight(GraphicsContext gc) {
        bulletX += bulletSpeed;
        gc.setFill(Color.BLACK);
        gc.fillRect(bulletX, bulletY, 3, 5);
    }

    public void setBulletStart() {
        bulletX = positionX;
        bulletY = positionY;
    }

    public void shootPressed(GraphicsContext gc) {
        if (Objects.equals(dirB[0], "UP")) {
            gc.setFill(Color.BLACK);
            setBulletStart();
            gc.fillRect(positionX, positionY, 3, 5);
            dirB[0] = "SU";
        }
        else if (Objects.equals(dirB[0], "SU") && !Objects.equals(dirB[1], "SD"))
            bulletMoveUp(gc);

        if (Objects.equals(dirB[1], "DOWN")) {
            gc.setFill(Color.BLACK);
            setBulletStart();
            gc.fillRect(positionX, positionY, 3, 5);
            dirB[1] = "SD";
        }
        else if (Objects.equals(dirB[1], "SD") && !Objects.equals(dirB[0], "SU"))
            bulletMoveDown(gc);

        if (Objects.equals(dirB[2], "LEFT")) {
            gc.setFill(Color.BLACK);
            setBulletStart();
            gc.fillRect(positionX, positionY, 5, 3);
            dirB[2] = "SL";
        }
        else if (Objects.equals(dirB[2], "SL"))
            bulletMoveLeft(gc);

        if (Objects.equals(dirB[3], "RIGHT")) {
            gc.setFill(Color.BLACK);
            setBulletStart();
            gc.fillRect(positionX, positionY, 5, 3);
            dirB[3] = "SR";
        }
        else if (Objects.equals(dirB[3], "SR"))
            bulletMoveRight(gc);
    }

    public void playerDraw(GraphicsContext gc) {
        if (Objects.equals(dir[3], "L")) {
            gc.drawImage(playerL, positionX, positionY);
        }
        if (Objects.equals(dir[2], "R")) {
            gc.drawImage(player, positionX, positionY);
        }
        if (Objects.equals(dir[2], "") && Objects.equals(dir[3], ""))
            gc.drawImage(player, positionX, positionY);
    }

    public void moveBackX() {
        positionX -= speed;
    }

    public void moveForX() {
        positionX += speed;
    }

    public void moveBackY() {
        positionY -= speed;
    }

    public void moveForY() {
        positionY += speed;
    }

    public void playerInput(Scene scene, GraphicsContext gc) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //WASD - передвижение
                KeyCode code = event.getCode();
                if (code == KeyCode.W && (positionY - speed > 0)) {
                    moveBackY();
                    dir[0] = "W";
                }
                if (code == KeyCode.S && (positionY + speed < 960)) {
                    moveForY();
                    dir[1] = "S";
                }
                if (code == KeyCode.D && (positionX + speed < 960)) {
                    moveForX();
                    dir[2] = "R";
                    dir[3] = "";
                }
                if (code == KeyCode.A && (positionX - speed > 0)) {
                    moveBackX();
                    dir[3] = "L";
                    dir[2] = "";
                }
                //Стрельба
                if (code == KeyCode.UP)
                    dirB[0] = "UP";
                if (code == KeyCode.DOWN)
                    dirB[1] = "DOWN";
                if (code == KeyCode.LEFT)
                    dirB[2] = "LEFT";
                if (code == KeyCode.RIGHT) {
                    dirB[3] = "RIGHT";
                }
            }
        });
    }

    static void setLives(int Lives) {
        lives = Lives;
    }

    static int getLives() {
        return lives;
    }

    static int getPositionX() {
        return positionX;
    }

    static int getPositionY() {
        return positionY;
    }
}
