package io.ylab.task2.rateLimitedPrinter;

/**
 * so called RateLimiterPrinter
 */
public class RateLimitedPrinterImpl implements RateLimitedPrinter {

    private long interval;
    private long lastPrintTime;

    public RateLimitedPrinterImpl(long interval, long lastPrintTime) {
        this.interval = interval;
        this.lastPrintTime = lastPrintTime;
    }

    @Override
    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastPrintTime >= interval) {
            System.out.println(message);
            lastPrintTime = currentTime;
        }
    }
}