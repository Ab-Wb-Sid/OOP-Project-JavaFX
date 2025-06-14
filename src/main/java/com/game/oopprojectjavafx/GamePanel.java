package com.game.oopprojectjavafx;

//import javafx.animation.AnimationTimer;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.paint.Color;
//
//public class GamePanel extends Canvas {
//    static final int GAME_WIDTH = 1000;
//    static final int GAME_HEIGHT = (int)(GAME_WIDTH * 0.5555);
//    static final int BALL_DIAMETER = 20;
//    static final int PADDLE_WIDTH = 25;
//    static final int PADDLE_HEIGHT = 100;
//
//    Paddle paddle1;
//    Paddle paddle2;
//    Ball ball;
//    Score score;
//
//    private boolean wPressed = false;
//    private boolean sPressed = false;
//    private boolean upPressed = false;
//    private boolean downPressed = false;
//
//    public GamePanel() {
//        super(GAME_WIDTH, GAME_HEIGHT);
//
//        newPaddles();
//        newBall();
//        score = new Score(GAME_WIDTH, GAME_HEIGHT);
//
//        setupInput();
//
//        AnimationTimer timer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                move();
//                checkCollision();
//                draw();
//            }
//        };
//        timer.start();
//    }
//
//    private void newBall() {
//        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), (GAME_HEIGHT / 2) - (BALL_DIAMETER / 2), BALL_DIAMETER);
//    }
//
//    private void newPaddles() {
//        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT);
//        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT);
//    }
//
//    private void setupInput() {
//        this.setFocusTraversable(true);
//        this.setOnKeyPressed(e -> handleKeyPress(e));
//        this.setOnKeyReleased(e -> handleKeyRelease(e));
//    }
//
//    private void handleKeyPress(KeyEvent e) {
//        KeyCode code = e.getCode();
//        if (code == KeyCode.W) wPressed = true;
//        if (code == KeyCode.S) sPressed = true;
//        if (code == KeyCode.UP) upPressed = true;
//        if (code == KeyCode.DOWN) downPressed = true;
//    }
//
//    private void handleKeyRelease(KeyEvent e) {
//        KeyCode code = e.getCode();
//        if (code == KeyCode.W) wPressed = false;
//        if (code == KeyCode.S) sPressed = false;
//        if (code == KeyCode.UP) upPressed = false;
//        if (code == KeyCode.DOWN) downPressed = false;
//    }
//
//    private void move() {
//        if (wPressed) paddle1.moveUp();
//        if (sPressed) paddle1.moveDown(GAME_HEIGHT);
//        if (upPressed) paddle2.moveUp();
//        if (downPressed) paddle2.moveDown(GAME_HEIGHT);
//        ball.move();
//    }
//
//    private void draw() {
//        GraphicsContext gc = this.getGraphicsContext2D();
//        gc.setFill(Color.BLACK);
//        gc.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
//
//        paddle1.draw(gc);
//        paddle2.draw(gc);
//        ball.draw(gc);
//        score.draw(gc);
//    }
//
//    private void checkCollision() {
//        // Bounce off top and bottom
//        if (ball.getY() <= 0 || ball.getY() >= GAME_HEIGHT - BALL_DIAMETER) {
//            ball.setYVelocity(-ball.getYVelocity());
//        }
//
//        // Paddle collision
//        if (ball.intersects(paddle1)) {
//            ball.bounceOffPaddle(true);
//        }
//        if (ball.intersects(paddle2)) {
//            ball.bounceOffPaddle(false);
//        }
//
//        // Scoring
//        if (ball.getX() <= 0) {
//            score.player2++;
//            newPaddles();
//            newBall();
//        }
//        if (ball.getX() >= GAME_WIDTH - BALL_DIAMETER) {
//            score.player1++;
//            newPaddles();
//            newBall();
//        }
//    }
//}
