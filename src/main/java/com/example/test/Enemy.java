package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static java.lang.Math.abs;

public class Enemy extends Actor {
    protected double speed;
    protected int afterHit;
    protected boolean alive = true;
    protected boolean hited = false;

    public Enemy() {
        speed = 1;
    }

    public void enemyLogic(GraphicsContext gc, Player player, Image img) {
        if (alive) {
            drawEnemy(gc, img);
            enemyMove(player);
            deadCheck(player);
        }
    }

    public void enemyHit(Player player) {
        if (!hited) {
            afterHit = player.getLives() - 1;
            player.setLives(afterHit);
            hited = true;
        }
    }

    public void deadCheck(Player player) {
        if (abs(Player.getBulletX() - positionX) < 32 && abs(Player.getBulletY() - positionY) < 32) {
            alive = false;
            player.setBulletHit();
            player.setBulletCord();
        }
    }

    public void drawEnemy(GraphicsContext gc, Image img) {
        gc.drawImage(img, positionX, positionY);
    }

    public void enemyMove(Player player) {
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
            enemyHit(player);
        }
    }

    public boolean getState() {
        return alive;
    }
}
