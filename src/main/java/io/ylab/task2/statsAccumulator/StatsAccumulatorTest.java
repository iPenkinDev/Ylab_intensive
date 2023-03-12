package io.ylab.task2.statsAccumulator;

import java.util.jar.JarOutputStream;

public class StatsAccumulatorTest {
    public static void main(String[] args) {
        StatsAccumulatorImpl statsAccumulator = new StatsAccumulatorImpl();
        System.out.println("add 1");
        statsAccumulator.add(1);
        System.out.println("add 2");
        statsAccumulator.add(2);
        System.out.println(" stats\n");
        System.out.println("min=" + statsAccumulator.getMin());
        System.out.println("max=" + statsAccumulator.getMax());
        System.out.println("count=" + statsAccumulator.getCount());
        System.out.println("average=" + statsAccumulator.getAvg());
    }
}
