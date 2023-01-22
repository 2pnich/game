package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

import static java.lang.Math.abs;

public class Enemy extends Actor {
    protected double speed;
    protected int afterHit;
    protected boolean alive = true;
    protected boolean hited = false;
    Image skelly = new Image("/skelly.png", 32, 32, false, false);
    Image pudge = new Image("/boss.png", 100, 100, false, false);

    public Enemy() {
        speed = 1;
    }

    public void enemyLogic(GraphicsContext gc, Player player, String entity) {
        if (alive) {
            if (Objects.equals(entity, "0")) {
                drawEnemy(gc, skelly);
                enemyMove(player);
                deadCheck(player);
            }
            if (Objects.equals(entity, "boss")) {
                drawEnemy(gc, pudge);
                enemyMove(player);
                bossDeadCheck(player);
            }
        }
    }

    public void enemyHit(Player player) {
        if (!hited) {
            afterHit = player.getLives() - 1;
            player.setLives(afterHit);
            hited = true;
        }
    }

    public void bossDeadCheck(Player player) {
        if (abs(Player.getBulletX() - positionX) < 64 && abs(Player.getBulletY() - positionY) < 64) {
            Boss.healthMinus();
            player.setBulletHit();
            player.setBulletCord();
        }
        if (Boss.getHealth() == 0)
            alive = false;
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
