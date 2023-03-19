package io.ylab.task3.fileSort;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class Sorter {
    private static final int CHUNK_SIZE = 10; // block size

    //init comparator
    Comparator<SmartReader> smartReaderComparator = (s1, s2) -> {
        if (s1.currentNumber == s2.currentNumber) {
            return 0;
        }
        if (s1.currentNumber == null) {
            return 1;
        }
        if (s2.currentNumber == null) {
            return -1;
        }
        return Long.compare(s1.currentNumber, s2.currentNumber);

    };

    public File sortFile(File inputFile) {
        try {
            List<File> chunkFiles = splitFile(inputFile); // split file into blocks

            // create a thread pool
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            List<Future<File>> sortedChunkFiles = new ArrayList<>();

            // sort each block in a separate thread
            for (File chunkFile : chunkFiles) {
                Callable<File> task = () -> {
                    BufferedReader reader = new BufferedReader(new FileReader(chunkFile));
                    List<Long> longs = new ArrayList<>();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        longs.add(Long.valueOf(line));
                    }

                    reader.close();
                    longs.sort(Long::compareTo); // sort block

                    // record sorted block to a file
                    File sortedChunkFile = new File(chunkFile.getAbsolutePath() + ".sorted");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(sortedChunkFile));

                    for (Long sortedLine : longs) {
                        writer.write(sortedLine.toString());
                        writer.newLine();
                    }
                    writer.flush();
                    writer.close();
                    return sortedChunkFile;
                };

                sortedChunkFiles.add(executorService.submit(task));
            }

            for (Future<File> futures : sortedChunkFiles) {
                futures.get();
            }
            executorService.shutdown();

            // merge sorted blocks into one file
            return mergeSortedFiles(sortedChunkFiles);

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    // split file into blocks method
    private List<File> splitFile(File inputFile) throws IOException {
        List<File> chunkFiles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        int chunkIndex = 0;
        List<String> lines = new ArrayList<>(CHUNK_SIZE);
        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line);

            if (lines.size() == CHUNK_SIZE) {
                chunkIndex++;
                chunkFiles.add(writeChunkToFile(lines, chunkIndex));
                lines.clear();
            }
        }

        if (!lines.isEmpty()) {
            chunkIndex++;
            chunkFiles.add(writeChunkToFile(lines, chunkIndex));
        }

        reader.close();

        return chunkFiles;
    }

    // record block to a file method
    private File writeChunkToFile(List<String> lines, int chunkIndex) throws IOException {
        File chunkFile = new File("./tmp/chunk" + chunkIndex + ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(chunkFile));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();

        return chunkFile;
    }

    // merge sorted blocks into one file method
    private File mergeSortedFiles(List<Future<File>> chunksFutures) throws IOException, ExecutionException, InterruptedException {
        List<SmartReader> readers = new ArrayList<>();
        for (Future<File> sortedChunkFile : chunksFutures) {
            readers.add(new SmartReader(sortedChunkFile.get()));
        }

        File outputFile = new File("./tmp/output.txt");
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(outputFile));


        while (true) {
            readers.sort(smartReaderComparator);

            SmartReader nextReader = readers.get(0);

            if (nextReader.currentNumber == null) {
                break;
            }

            outputWriter.write(nextReader.currentNumber.toString());
            outputWriter.newLine();

            nextReader.readNext();
        }

        outputWriter.flush();
        outputWriter.close();
        return outputFile;
    }

    // class for reading blocks
    private static class SmartReader {
        private final BufferedReader bufferedReader;
        private Long currentNumber;
        private File file;

        public SmartReader(File file) throws IOException {
            this.file = file;

            bufferedReader = new BufferedReader(new FileReader(file));

            currentNumber = Long.valueOf(bufferedReader.readLine());
        }

        public void readNext() throws IOException {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                bufferedReader.close();
                currentNumber = null;
            } else {
                currentNumber = Long.valueOf(readLine);
            }
        }
    }
}

