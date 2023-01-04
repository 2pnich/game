package com.example.test;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Actor {
    protected Image image; //image that represents the actor
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }


}
