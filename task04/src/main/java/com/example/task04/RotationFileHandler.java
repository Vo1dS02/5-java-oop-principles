package com.example.task04;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RotationFileHandler implements MessageHandler {
    private final String baseName;
    private final ChronoUnit rotationUnit;

    private LocalDateTime nextRotationTime;
    private String currentFilePath;

    public RotationFileHandler(String baseName, ChronoUnit rotationUnit) {
        this.baseName = baseName;
        this.rotationUnit = rotationUnit;
        updateRotationPath(LocalDateTime.now());
    }

    private void updateRotationPath(LocalDateTime now) {
        // Форматируем хвост файла в зависимости от ротации
        String formatPattern = switch (rotationUnit) {
            case HOURS -> "yyyy-MM-dd_HH";
            case DAYS -> "yyyy-MM-dd";
            case MONTHS -> "yyyy-MM";
            default -> "yyyy-MM-dd_HH-mm-ss"; // дефолтный паттерн для мелких интервалов
        };

        String suffix = now.format(DateTimeFormatter.ofPattern(formatPattern));
        this.currentFilePath = baseName + "_" + suffix + ".log";
        this.nextRotationTime = now.truncatedTo(rotationUnit).plus(1, rotationUnit);
    }

    @Override
    public void handle(Level level, String timestamp, String loggerName, String message) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(nextRotationTime) || now.isEqual(nextRotationTime)) {
            updateRotationPath(now);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(currentFilePath, true))) {
            writer.printf("[%s] %s %s - %s%n", level.name(), timestamp, loggerName, message);
        } catch (IOException e) {
            System.err.println("Ошибка ротации файла: " + e.getMessage());
        }
    }
}
