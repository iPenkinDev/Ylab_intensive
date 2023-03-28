package io.ylab.task3.fileSort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("data.txt", 375_000_000);
        System.out.println("isSorted before=" + new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println("isSorted after=" + new Validator(sortedFile).isSorted()); // true
    }
}
