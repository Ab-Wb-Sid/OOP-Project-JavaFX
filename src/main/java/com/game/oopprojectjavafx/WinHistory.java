package com.game.oopprojectjavafx;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WinHistory {
    private static final String FILE_NAME = "ory.txt";

    public static void logWin(String winner) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(timestamp + " - " + winner + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to win history: " + e.getMessage());
        }
    }
}
