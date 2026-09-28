package com.example.task04;

import java.util.ArrayList;
import java.util.List;

public class MemoryHandler implements MessageHandler {
    private final MessageHandler delegate;
    private final int bufferSize;
    private final List<LogRecord> buffer;

    public MemoryHandler(MessageHandler delegate, int bufferSize) {
        this.delegate = delegate;
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
    }

    @Override
    public synchronized void handle(Level level, String timestamp, String loggerName, String message) {
        buffer.add(new LogRecord(level, timestamp, loggerName, message));
        if (buffer.size() >= bufferSize) {
            flush();
        }
    }

    public synchronized void flush() {
        for (LogRecord record : buffer) {
            delegate.handle(record.level(), record.timestamp(), record.loggerName(), record.message());
        }
        buffer.clear();
    }

    // Вспомогательный DTO класс-рекорд для хранения лога в памяти
    private record LogRecord(Level level, String timestamp, String loggerName, String message) {}
}
