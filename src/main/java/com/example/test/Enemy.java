package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static java.lang.Math.abs;

public class Enemy extends Actor {
    Image skelly = new Image("/skelly.png", height, width, false, false);
    private final int speed;
    private int afterHit;
    private boolean alive = true;

    public Enemy() {
        positionX = 20;
        positionY = 380;
        speed = 1;
    }

    public void enemyLogic(GraphicsContext gc, Player player) {
        if (alive) {
            drawEnemy(gc);
            enemyMove();
            deadCheck();
        }
    }

    public void deadCheck() {
        if (abs(Player.getBulletX() - positionX) < 32 && abs(Player.getBulletY() - positionY) < 32) {
            alive = false;
        }
    }

    public void drawEnemy(GraphicsContext gc) {
        gc.drawImage(skelly, positionX, positionY);
    }

    public void enemyMove() {
        if (Player.getPositionX() < getX()) {
            positionX -= speed;
        } else if (Player.getPositionX() > getX())
            positionX += speed;
        if (abs(Player.getPositionX() - getX()) < 16 && abs(Player.getPositionY() - getY()) < 32) {
            enemyHit();
        }
        if (Player.getPositionY() < getY()) {
            positionY -= speed;
        } else if (Player.getPositionY() > getY()) {
            positionY += speed;
        }
    }

    public void enemyHit() {
        afterHit = Player.getLives() - 1;
        Player.setLives(afterHit);
    }

    private void generateEnemy4() {
        Enemy e1 = new Enemy();
        e1.setPosition(860, 360);
        Enemy e2 = new Enemy();
        e2.setPosition(50, 360);
        Enemy e3 = new Enemy();
        e3.setPosition(360, 860);
        Enemy e4 = new Enemy();
        e4.setPosition(860, 360);
    }

    public void enemySpawn4(GraphicsContext gc) {
        gc.drawImage(skelly, positionX, positionY);
    }
}
