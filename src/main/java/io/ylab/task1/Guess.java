package io.ylab.task1;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
        int number = new Random().nextInt(99)+1; // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        int attempts = 0;
        Scanner scanner = new Scanner(System.in);

        while (attempts < maxAttempts) {
            System.out.println("Попытка " + (attempts + 1) + ". Введи число:");
            int guess = scanner.nextInt();
            attempts++;
            if (guess == number) {
                System.out.println("Ты угадал с " + attempts + " попытки!");
                break;
            } else if (guess > number) {
                System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - attempts) + " попыток.");
            } else {
                System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - attempts) + " попыток.");            }
        }
        if (attempts == maxAttempts) {
            System.out.println("Ты не угадал!");
        }
    }
}
