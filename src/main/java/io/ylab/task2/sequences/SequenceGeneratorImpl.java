package io.ylab.task2.sequences;

public class SequenceGeneratorImpl implements SequenceGenerator{
    // Реализация метода для вывода n первых членов последовательности A
    @Override
    public void a(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(2 * i + " ");
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности B
    @Override
    public void b(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(2 * i + 1 + " ");
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности C
    @Override
    public void c(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i + " ");
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности D
    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i * i + " ");
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности E
    @Override
    public void e(int n) {
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                System.out.print("1 ");
            } else {
                System.out.print("-1 ");
            }
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности F
    @Override
    public void f(int n) {
        int sign = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(i * sign + " ");
            sign *= -1;
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности G
    @Override
    public void g(int n) {
        int sign = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i * sign + " ");
            sign *= -1;
        }
        System.out.println();
    }

    // Реализация метода для вывода n первых членов последовательности H
    @Override
    public void h(int n) {
        int count = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(count + " ");
            System.out.print("0 ");
            count++;
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        int factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
            System.out.print(factorial + " ");
        }
        System.out.println();
    }

    @Override
    public void j(int n) {
        int a = 1, b = 1;
        System.out.print(a + " " + b + " ");
        for (int i = 3; i <= n; i++) {
            int c = a + b;
            System.out.print(c + " ");
            a = b;
            b = c;
        }
        System.out.println();
    }
}
