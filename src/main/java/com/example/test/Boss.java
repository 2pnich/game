package com.example.test;

import javafx.scene.image.Image;

public class Boss extends Enemy{

    private int health;

    public Boss() {
        speed = 0.005;
        alive = true;
        health = 10;
    }

}
