package com.example.task04;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    // -----------------------------   Поля Класса   -------------------------------------------
    private Level level = Level.DEBUG;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    private final String name;
    private static final List<Logger> INSTANCES = new ArrayList<>();

    // Список зарегистрированных обработчиков
    private final List<MessageHandler> handlers = new ArrayList<>();

    // -----------------------------   Конструктор Логгера   -------------------------------------------
    private Logger(String name) {
        this.name = name;
    }

    // -----------------------------   Геттеры и сеттеры   -------------------------------------------
    public static Logger getLogger(String name) {
        for (Logger logger : INSTANCES) {
            if (logger.name.equals(name)){
                return logger;
            }
        }
        Logger logger = new Logger(name);
        INSTANCES.add(logger);
        return logger;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    // Метод добавления нового обработчика
    public void addHandler(MessageHandler handler) {
        this.handlers.add(handler);
    }

    // Метод удаления обработчика
    public void removeHandler(MessageHandler handler) {
        this.handlers.remove(handler);
    }

    // -----------------------------   Методы log   -------------------------------------------
    public void log(Level level, String message) {
        doLog(level, message);
    }

    public void log(Level level, String template, Object... args) {
        doLog(level, String.format(template, args));
    }

    // -----------------------------   Методы debug / info / warning / error   -------------------------------------------
    public void debug(String message) { log(Level.DEBUG, message); }
    public void debug(String template, Object... args) { log(Level.DEBUG, template, args); }

    public void info(String message) { log(Level.INFO, message); }
    public void info(String template, Object... args) { log(Level.INFO, template, args); }

    public void warning(String message) { log(Level.WARNING, message); }
    public void warning(String template, Object... args) { log(Level.WARNING, template, args); }

    public void error(String message) { log(Level.ERROR, message); }
    public void error(String template, Object... args) { log(Level.ERROR, template, args); }

    // -----------------------------   doLog   -------------------------------------------
    private void doLog(Level messageLevel, String message) {
        if (messageLevel.ordinal() < level.ordinal()) {
            return;
        }
        String timestamp = LocalDateTime.now().format(FORMATTER);

        // Отправляем сообщение во все зарегистрированные обработчики
        for (MessageHandler handler : handlers) {
            handler.handle(messageLevel, timestamp, name, message);
        }
    }
}
