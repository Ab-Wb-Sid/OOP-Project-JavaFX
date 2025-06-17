package com.game.oopprojectjavafx;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WinHistory {

    private static final String FILE_HOME = System.getProperty("user.home") + "/pong_win_history.txt";
    private static final String FILE_TARGET = "win_history.txt";

    public static void logWin(String winner) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String entry = timestamp + " - " + winner + "\n";

        try (
                FileWriter homeWriter = new FileWriter(FILE_HOME, true);
                FileWriter targetWriter = new FileWriter(FILE_TARGET, true)
        ) {
            homeWriter.write(entry);
            targetWriter.write(entry);
        } catch (IOException e) {
            System.err.println("Error writing to win history: " + e.getMessage());
        }
    }
}