package com.game.oopprojectjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private double x, y;
    private final int width, height;
    private final int id;
    private int yVelocity = 0;
    private final int speed = 10;

    public Paddle(double x, double y, int width, int height, int id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        y += yVelocity;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(id == 1 ? Color.BLUE : Color.RED);
        gc.fillRect(x, y, width, height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setY(double y) { this.y = y; }
    public int getSpeed() { return speed; }
}