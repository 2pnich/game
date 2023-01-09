package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static java.lang.Math.abs;

public class Enemy extends Actor {
    Image skelly = new Image("/skelly.png", height, width, false, false);
    private final int speed;
    private int afterHit;
    private boolean alive = true;
    private boolean hited = false;

    public Enemy() {
        speed = 1;
    }

    public void enemyLogic(GraphicsContext gc) {
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
        if (Player.getPositionY() < getY()) {
            positionY -= speed;
        } else if (Player.getPositionY() > getY()) {
            positionY += speed;
        }
        if (abs(Player.getPositionX() - getX()) < 16 && abs(Player.getPositionY() - getY()) < 32) {
            enemyHit();
        }
    }

    public void enemyHit() {
        if (!hited) {
            afterHit = Player.getLives() - 1;
            Player.setLives(afterHit);
            hited = true;
        }
    }

    public boolean getState() {
        return alive;
    }
}
