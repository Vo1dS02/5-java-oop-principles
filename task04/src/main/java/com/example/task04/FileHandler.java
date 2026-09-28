package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileHandler implements MessageHandler {
    private final String filePath;

    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handle(Level level, String timestamp, String loggerName, String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.printf("[%s] %s %s - %s%n", level.name(), timestamp, loggerName, message);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
