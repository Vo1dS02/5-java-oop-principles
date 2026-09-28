package com.example.task04;

public class ConsoleHandler implements MessageHandler {
    @Override
    public void handle(Level level, String timestamp, String loggerName, String message) {
        System.out.printf("[%s] %s %s - %s%n", level.name(), timestamp, loggerName, message);
    }
}
