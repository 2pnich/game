package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Actor {
    protected double positionX; //x-coordinate of actor's position
    protected double positionY; //y-coordinate of actor's position
    protected double velocityX; //x value of actor's velocity
    protected double velocityY; //y value of actor's velocity
    protected double width = 32; //width of actor's image
    protected double height = 32; //height of actor's image


    public double getX() {
        return positionX;
    }

    public double getY() {
        return positionY;
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


}
