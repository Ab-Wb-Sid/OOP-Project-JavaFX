package com.game.oopprojectjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class PongGame extends Application {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = (int)(GAME_WIDTH * 0.5555);

    private GraphicsContext gc;
    private Paddle paddle1, paddle2;
    private Ball ball;
    private Score score;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(GAME_WIDTH, GAME_HEIGHT);
        gc = canvas.getGraphicsContext2D();

        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - 50, 25, 100, 1);
        paddle2 = new Paddle(GAME_WIDTH - 25, (GAME_HEIGHT / 2) - 50, 25, 100, 2);
        ball = new Ball((GAME_WIDTH / 2) - 10, (GAME_HEIGHT / 2) - 10, 20, 20);
        score = new Score(GAME_WIDTH, GAME_HEIGHT);

        Scene scene = new Scene(new StackPane(canvas));
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> paddle1.setYDirection(-paddle1.getSpeed());
                case S -> paddle1.setYDirection(paddle1.getSpeed());
                case UP -> paddle2.setYDirection(-paddle2.getSpeed());
                case DOWN -> paddle2.setYDirection(paddle2.getSpeed());
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W, S -> paddle1.setYDirection(0);
                case UP, DOWN -> paddle2.setYDirection(0);
            }
        });

        stage.setTitle("Pong Game");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();

        new AnimationTimer() {
            public void handle(long now) {
                update();
                render();
            }
        }.start();
    }

    private void update() {
        paddle1.move();
        paddle2.move();
        ball.move();

        if (ball.getY() <= 0 || ball.getY() >= GAME_HEIGHT - ball.getHeight()) {
            ball.setYDirection(-ball.getYVelocity());
        }

        if (ball.intersects(paddle1)) {
            ball.setXDirection(Math.abs(ball.getXVelocity()) + 1);
            ball.setYDirection(ball.getYVelocity() > 0 ? ball.getYVelocity() + 1 : ball.getYVelocity() - 1);
        }
        if (ball.intersects(paddle2)) {
            ball.setXDirection(-(Math.abs(ball.getXVelocity()) + 1));
            ball.setYDirection(ball.getYVelocity() > 0 ? ball.getYVelocity() + 1 : ball.getYVelocity() - 1);
        }

        if (paddle1.getY() < 0) paddle1.setY(0);
        if (paddle1.getY() > GAME_HEIGHT - paddle1.getHeight()) paddle1.setY(GAME_HEIGHT - paddle1.getHeight());
        if (paddle2.getY() < 0) paddle2.setY(0);
        if (paddle2.getY() > GAME_HEIGHT - paddle2.getHeight()) paddle2.setY(GAME_HEIGHT - paddle2.getHeight());

        if (ball.getX() <= 0) {
            score.player2++;
            resetPositions();
        }
        if (ball.getX() >= GAME_WIDTH - ball.getWidth()) {
            score.player1++;
            resetPositions();
        }
    }

    private void render() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        paddle1.draw(gc);
        paddle2.draw(gc);
        ball.draw(gc);
        score.draw(gc);
    }

    private void resetPositions() {
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - 50, 25, 100, 1);
        paddle2 = new Paddle(GAME_WIDTH - 25, (GAME_HEIGHT / 2) - 50, 25, 100, 2);
        ball = new Ball((GAME_WIDTH / 2) - 10, (GAME_HEIGHT / 2) - 10, 20, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}