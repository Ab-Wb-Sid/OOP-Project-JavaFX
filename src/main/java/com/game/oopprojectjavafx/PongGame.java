package com.game.oopprojectjavafx;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PongGame extends Application {
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = (int) (GAME_WIDTH * 0.5555);

    private GraphicsContext gc;
    private Paddle paddle1, paddle2;
    private Ball ball;
    private Score score;

    private boolean vsComputer = false;
    private String difficulty = "Medium";
    private int scoreLimit = 5;

    private AnimationTimer timer;

    @Override
    public void start(Stage stage) {
        showMainMenu(stage);
    }

    private void showMainMenu(Stage stage) {
        VBox buttonMenu = new VBox(20);
        buttonMenu.setAlignment(Pos.CENTER);

        Label title = new Label("Ball Blitz");
        title.setFont(Font.font("Consolas", FontWeight.EXTRA_BOLD, 64));
        title.setTextFill(Color.WHITE);
        title.setEffect(new DropShadow(20, Color.BLUE));
        VBox.setMargin(title, new javafx.geometry.Insets(0, 0, -10, 0));

        Label subtitle = new Label("2D Table Tennis Game");
        subtitle.setFont(Font.font("Consolas", FontWeight.BOLD, 28));
        subtitle.setTextFill(Color.WHITE);
        subtitle.setEffect(new DropShadow(10, Color.RED));

        Button pvpButton = new Button("Player vs Player");
        Button pvcButton = new Button("Player vs Computer");
        ComboBox<Integer> scoreLimitCombo = new ComboBox<>();
        for (int i = 3; i <= 15; i++) scoreLimitCombo.getItems().add(i);
        scoreLimitCombo.setValue(5);

        styleButton(pvpButton, Color.BLUE);
        styleButton(pvcButton, Color.RED);
        scoreLimitCombo.setStyle("-fx-font-size: 16px; -fx-background-color: white; -fx-pref-width: 200px;");

        pvpButton.setOnAction(e -> {
            vsComputer = false;
            scoreLimit = scoreLimitCombo.getValue();
            launchGame(stage);
        });

        pvcButton.setOnAction(e -> {
            vsComputer = true;
            difficulty = "Medium";
            scoreLimit = scoreLimitCombo.getValue();
            launchGame(stage);
        });

        buttonMenu.getChildren().addAll(title, subtitle, pvpButton, pvcButton, new Label("Score Limit:"), scoreLimitCombo);

        HBox menuLayout = new HBox(50);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setStyle("-fx-background-color: black;");

        Image leftImage = new Image(getClass().getResource("/com/game/oopprojectjavafx/image.png").toExternalForm(), 200, 0, true, true);
        ImageView leftImageView = new ImageView(leftImage);

        Image rightImage = new Image(getClass().getResource("/com/game/oopprojectjavafx/image.png").toExternalForm(), 200, 0, true, true);
        ImageView rightImageView = new ImageView(rightImage);

        menuLayout.getChildren().addAll(leftImageView, buttonMenu, rightImageView);

        Scene menuScene = new Scene(menuLayout, GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(menuScene);
        stage.setWidth(GAME_WIDTH);
        stage.setHeight(GAME_HEIGHT);
        stage.setResizable(false);
        stage.setTitle("Ball Blitz - Menu");
        stage.show();
    }

    private void styleButton(Button button, Color glowColor) {
        button.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: white; -fx-border-width: 2px;");
        DropShadow glow = new DropShadow(20, glowColor);
        button.setEffect(glow);
        button.setPrefWidth(250);

        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e ->
                button.setStyle("-fx-background-color: " + toRgbCode(glowColor) + "; -fx-text-fill: black; -fx-font-size: 20px; -fx-border-color: white; -fx-border-width: 2px;")
        );
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e ->
                button.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: white; -fx-border-width: 2px;")
        );
    }

    private String toRgbCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public void launchGame(Stage stage) {
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
                case UP -> {
                    if (!vsComputer) paddle2.setYDirection(-paddle2.getSpeed());
                }
                case DOWN -> {
                    if (!vsComputer) paddle2.setYDirection(paddle2.getSpeed());
                }
            }
        });
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W, S -> paddle1.setYDirection(0);
                case UP, DOWN -> paddle2.setYDirection(0);
            }
        });

        stage.setScene(scene);
        stage.setFullScreen(true);

        timer = new AnimationTimer() {
            public void handle(long now) {
                update();
                render();
            }
        };
        timer.start();
    }

    private void update() {
        paddle1.move();
        if (!vsComputer) paddle2.move();
        ball.move();

        if (vsComputer) moveAI();

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

        if (score.player1 >= scoreLimit || score.player2 >= scoreLimit) {
            endGame();
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

    private void moveAI() {
        double targetY = ball.getY() - paddle2.getHeight() / 2.0;
        double diff = targetY - paddle2.getY();

        int aiSpeed;
        switch (difficulty) {
            case "Easy" -> aiSpeed = 3;
            case "Hard" -> aiSpeed = 9;
            default -> aiSpeed = 6;
        }

        if (Math.abs(diff) > aiSpeed) {
            paddle2.setY(paddle2.getY() + (diff > 0 ? aiSpeed : -aiSpeed));
        } else {
            paddle2.setY(targetY);
        }
    }

    private void endGame() {
        timer.stop();
        String winner = score.player1 >= scoreLimit ? "Player 1" : (vsComputer ? "Computer" : "Player 2");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(winner + " wins!");
        alert.setContentText("Final Score - Player 1: " + score.player1 + " | Player 2: " + score.player2);
        alert.showAndWait();

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
