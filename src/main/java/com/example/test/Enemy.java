package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Enemy extends Actor {
    Image gnome = new Image("/enemy.png", height, width, false, false);
    List<Enemy> enemyList = new ArrayList<>();
    private int speed;
    private int afterHit;
    public Enemy() {
        speed = 16;
        positionX = 850;
        positionY = 360;
    }

    public void enemyHit(){
        afterHit = Player.getLives() - 1;
        Player.setLives(afterHit);
    }

    public void enemyMove() {
        if (abs(Player.getPositionX() - getX()) > 16) {
            positionX += speed;
        }
        else
            enemyHit();
        if (abs(Player.getPositionY() - getY()) > 16) {
            positionY += speed;
        }
        else
            enemyHit();
    }

    public void enemySpawn4(GraphicsContext gc) {

        enemyList.add(new Enemy());
        gc.drawImage(gnome, positionX, positionY);
    }
}
