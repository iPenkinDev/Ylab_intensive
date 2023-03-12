package io.ylab.task2.rateLimitedPrinter;

/**
 * so called RateLimiterPrinter
 */
public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        RateLimitedPrinter rateLimiterPrinter = new RateLimitedPrinterImpl(1000, 10);
        for (int i = 0; i < 1_000_000_000; i++) {
            rateLimiterPrinter.print(String.valueOf(i));
        }
    }
}
