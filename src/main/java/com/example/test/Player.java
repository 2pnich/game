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
    private int lives;
    private static int positionY = 512;
    private static int positionX = 512;
    private static int bulletX;
    private static int bulletY;
    private static boolean bulletHit = false;
    private Boolean shootPressed = false;
    String[] dir = {"", "", "", ""};
    String dirB = "";

    Image player = new Image("player.png", height, width, false, false);
    Image playerL = new Image("player_b.png", height, width, false, false);
    Image bullet = new Image("bullet.png", 7, 7, false, false);

    public Player() {
        bulletSpeed = 16;
        lives = 3;
        speed = 32;
    }

    public void bulletMoveUp(GraphicsContext gc) {
        bulletY -= bulletSpeed;
        gc.drawImage(bullet, bulletX, bulletY);
    }

    public void bulletMoveDown(GraphicsContext gc) {
        bulletY += bulletSpeed;
        gc.drawImage(bullet, bulletX, bulletY);
    }

    public void bulletMoveLeft(GraphicsContext gc) {
        bulletX -= bulletSpeed;
        gc.drawImage(bullet, bulletX, bulletY);
    }

    public void bulletMoveRight(GraphicsContext gc) {
        bulletX += bulletSpeed;
        gc.drawImage(bullet, bulletX, bulletY);
    }

    public void setBulletStart() {
        bulletX = positionX;
        bulletY = positionY + 10;
    }

    public void shootPressed(GraphicsContext gc) {        //ОТРИСОВКА ПУЛИ И ПРОВЕРКА НАПРАВЛЕНИЯ
        if (Objects.equals(dirB, "UP")) {
            setBulletStart();
            gc.drawImage(bullet, bulletX, bulletY);
            dirB = "SU";
        }
        else if (Objects.equals(dirB, "SU") && !bulletHit) {
            bulletMoveUp(gc);
        }

        if (Objects.equals(dirB, "DOWN")) {
            setBulletStart();
            gc.drawImage(bullet, bulletX, bulletY);
            dirB = "SD";
        }
        else if (Objects.equals(dirB, "SD") && !bulletHit)
            bulletMoveDown(gc);

        if (Objects.equals(dirB, "RIGHT")) {
            setBulletStart();
            gc.drawImage(bullet, bulletX, bulletY);
            dirB = "SR";
        }
        else if (Objects.equals(dirB, "SR") && !bulletHit)
            bulletMoveRight(gc);

        if (Objects.equals(dirB, "LEFT")) {
            setBulletStart();
            gc.drawImage(bullet, bulletX, bulletY);
            dirB = "SL";
        }
        else if (Objects.equals(dirB, "SL") && !bulletHit)
            bulletMoveLeft(gc);

    }

    public void playerDraw(GraphicsContext gc) {
        if (Objects.equals(dir[3], "L")) {          //НАЖАТА 'A' - спрайт смотрит влево
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
                if (code == KeyCode.UP) {
                    dirB = "UP";
                    shootPressed = true;
                    bulletHit = false;
                }
                if (code == KeyCode.DOWN) {
                    dirB = "DOWN";
                    shootPressed = true;
                    bulletHit = false;
                }
                if (code == KeyCode.LEFT) {
                    dirB = "LEFT";
                    dir[3] = "L";
                    dir[2] = "";
                    shootPressed = true;
                    bulletHit = false;
                }
                if (code == KeyCode.RIGHT) {
                    dirB = "RIGHT";
                    dir[2] = "R";
                    dir[3] = "";
                    shootPressed = true;
                    bulletHit = false;
                }
            }
        });
    }

    public void setBulletCord() {
        bulletX = 0;
        bulletY = 0;
    }

    public void setBulletHit() {
        bulletHit = true;
    }

    public void setLives(int Lives) {
        lives = Lives;
    }

    public int getLives() {
        return lives;
    }

    static int getBulletX() {
        return bulletX;
    }

    static int getBulletY() {
        return bulletY;
    }

    static int getPositionX() {
        return positionX;
    }

    static int getPositionY() {
        return positionY;
    }
}
