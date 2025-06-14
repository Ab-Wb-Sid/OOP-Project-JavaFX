package com.game.oopprojectjavafx;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score {
    public static int GAME_WIDTH;
    public static int GAME_HEIGHT;
    public int player1 = 0;
    public int player2 = 0;

    public Score(int width, int height) {
        GAME_WIDTH = width;
        GAME_HEIGHT = height;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(GAME_WIDTH / 2.0, 0, GAME_WIDTH / 2.0, GAME_HEIGHT);
        gc.strokeOval(GAME_WIDTH / 2.0 - 150, GAME_HEIGHT / 2.0 - 150, 300, 300);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Consolas", 60));
        gc.fillText(String.format("%02d", player1), GAME_WIDTH / 2.0 - 85, 60);
        gc.fillText(String.format("%02d", player2), GAME_WIDTH / 2.0 + 20, 60);
    }
}
