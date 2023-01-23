package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static java.lang.Math.abs;

public class Coin extends Tile {
    private int count;
    private int positionX = 128;
    private int positionY = 128;
    private boolean gotCoin = false;
    Image coin = new Image("roflan.png", height / 2, width / 2, false, false);

    public Coin() {
        count = 0;
    }

    public void generate(GraphicsContext gc) {
        if (!gotCoin)
            gc.drawImage(coin, positionX, positionY);
        else {
            positionX = (int) Math.floor(Math.random() * 480) + 20;
            positionY = (int) Math.floor(Math.random() * 480) + 20;
            gc.drawImage(coin, positionX, positionY);
        }
        gotCoin = false;
    }

    public void gotCoin(GraphicsContext gc) {
        if (abs(Player.getPositionX() - positionX) < 32 && abs(Player.getPositionY() - positionY) < 32) {
            gotCoin = true;
            count++;
        }
    }

    public int getCoins() {
        return count;
    }

}
