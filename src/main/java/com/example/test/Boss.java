package com.example.test;

import javafx.scene.image.Image;

public class Boss extends Enemy{

    static int health;

    public Boss(float level) {
        if(level != 2)
            speed = 0.05 + level / 10;
        else
            speed = 0.05;
        alive = true;
        health = 5;
    }

    static void healthMinus() {
        health--;
    }

    static int getHealth() {
        return health;
    }
}
