package io.ylab.task1;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args){
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int pell = calculatePell(n);
            System.out.println(pell);
        }
    }

    private static int calculatePell(int n) {
        int p1 = 0, p2 = 1, p = 0;
        if (n == 0) {
            return p1;
        } else if (n == 1) {
            return p2;
        } else {
            for (int i = 2; i <= n; i++) {
                p = 2 * p2 + p1;
                p1 = p2;
                p2 = p;
            }
            return p;
        }
    }
}