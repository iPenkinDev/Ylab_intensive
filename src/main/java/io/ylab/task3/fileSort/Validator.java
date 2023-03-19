package io.ylab.task3.fileSort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Validator {
    private File file;

    public Validator(File file) {
        this.file = file;
    }

    public boolean isSorted() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MIN_VALUE;

            long lastPrintTs = System.currentTimeMillis();
            long index = 0;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current < prev) {
                    return false;
                } else {
                    prev = current;
                }
                index++;
                if (System.currentTimeMillis() - lastPrintTs > 5_000) {
                    System.out.println("Validated: " + index + " items from ???");
                    lastPrintTs = System.currentTimeMillis();
                }
            }
            scanner.close();
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}