package com.example.task04;

public interface MessageHandler {
    void handle(Level level, String timestamp, String loggerName, String message);
}
