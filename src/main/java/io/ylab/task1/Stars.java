package io.ylab.task1;

import java.util.Scanner;

public class Stars {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            if (template.length() != 1) {
                throw new IllegalArgumentException("Template should be a single character");
            }

            char symbol = template.charAt(0);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(symbol);
                }
                System.out.println();
            }
        }
    }
}
