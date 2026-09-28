package com.example.task03;

public class Task03Main {
    public static void main(String[] args) {
        TimeUnit unit1 = new Seconds(3600); // 1 час
        printTimeUnit(unit1);

        System.out.println("---");

        TimeUnit unit2 = new Milliseconds(1800000); // 30 минут (0.5 часа -> округлится до 1)
        printTimeUnit(unit2);
    }

    private static void printTimeUnit(TimeUnit unit) {
        System.out.println(String.format("Milliseconds: %d", unit.toMillis()));
        System.out.println(String.format("Seconds:      %d", unit.toSeconds()));
        System.out.println(String.format("Minutes:      %d", unit.toMinutes()));
        System.out.println(String.format("Hours:        %d", unit.getHours())); // Изменено
    }
}
