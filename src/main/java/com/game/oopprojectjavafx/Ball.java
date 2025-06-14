package com.game.oopprojectjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public class Ball {
    private double x, y;
    private final int width, height;
    private int xVelocity, yVelocity;
    private final int initialSpeed = 3;

    public Ball(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        Random random = new Random();
        xVelocity = random.nextBoolean() ? initialSpeed : -initialSpeed;
        yVelocity = random.nextBoolean() ? initialSpeed : -initialSpeed;
    }

    public void setXDirection(int xDirection) {
        xVelocity = xDirection;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, width, height);
    }

    public boolean intersects(Paddle paddle) {
        return x < paddle.getX() + paddle.getWidth() &&
                x + width > paddle.getX() &&
                y < paddle.getY() + paddle.getHeight() &&
                y + height > paddle.getY();
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getXVelocity() { return xVelocity; }
    public int getYVelocity() { return yVelocity; }
}