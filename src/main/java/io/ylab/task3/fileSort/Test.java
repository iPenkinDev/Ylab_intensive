package io.ylab.task3.fileSort;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Test {
    public static void main(String[] args) throws IOException {
        File tmpFolder = new File("tmp");
        if (!tmpFolder.exists()) {
            tmpFolder.mkdirs();
        }
        File outputData = new File(tmpFolder, "data.txt");

        File dataFile = new Generator().generate(outputData.getAbsolutePath(), 100);
        System.out.println(new Validator(dataFile).isSorted()); // false
        LocalTime start = LocalTime.now();
        File sortedFile = new Sorter().sortFile(dataFile);
        LocalTime end = LocalTime.now();
        System.out.println("Sorting time: " + start.until(end, ChronoUnit.SECONDS) + " seconds");
        System.out.println(new Validator(sortedFile).isSorted()); // true
    }
}
