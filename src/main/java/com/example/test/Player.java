package com.example.test;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private final int bulletSpeed;
    private final int speed;
    private static int lives;
    private static int positionY = 512;
    private static int positionX = 512;
    private int bulletX;
    private int bulletY;
    Image player = new Image("player.png", height, width, false, false);
    Image playerL = new Image("player_b.png", height, width, false, false);
    List<Image> bullet = new ArrayList<>();

    public Player() {
//        animTimer = 0;
//        animSpeed = 0;
//        gc.drawImage(player, positionX, positionY);
        bulletSpeed = 48;
        lives = 3;
        speed = 32;
    }

    public void bulletMove(GraphicsContext gc){
        gc.fillRect(positionX, positionY, 3, 5);
    }

    public void shoot(GraphicsContext gc) {
        gc.fillRect(positionX, positionY, 3, 5);

    }

    public void moveBackX(){
        positionX -= speed;
    }

    public void moveForX(){
        positionX += speed;
    }

    public void moveBackY(){
        positionY -= speed;
    }

    public void moveForY(){
        positionY += speed;
    }

    public void playerDraw(GraphicsContext gc){
        gc.drawImage(player, positionX, positionY);
    }

    public void playerDrawL(GraphicsContext gc){      //ВЛЕВО
        gc.drawImage(playerL, positionX, positionY);
    }

    public void playerInput(Scene scene, GraphicsContext gc) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //WASD - передвижение
                KeyCode code = event.getCode();
                if (code == KeyCode.W && (positionY - speed > 0)) {
                    moveBackY();
                    playerDraw(gc);
                }
                if (code == KeyCode.S && (positionY + speed < 960)) {
                    moveForY();
                    playerDraw(gc);
                }
                if (code == KeyCode.D && (positionX + speed < 960)) {
                    moveForX();
                    playerDraw(gc);
                }
                if (code == KeyCode.A && (positionX - speed > 0)) {
                    moveBackX();
                    playerDrawL(gc);
                }
                //Стрельба
                if (code == KeyCode.UP)
                    ;
                if (code == KeyCode.DOWN)
                    ;
                if (code == KeyCode.LEFT)
                    ;
                if (code == KeyCode.RIGHT)
                    ;
            }

        });
    }

    static void setLives(int Lives) {
        lives = Lives;
    }

    static  int getLives() {
        return lives;
    }

    static int getPositionX() {
        return positionX;
    }

    static int getPositionY() {
        return positionY;
    }
}
