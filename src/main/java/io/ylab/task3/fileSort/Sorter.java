package io.ylab.task3.fileSort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Sorter {
   // private static final int CHUNK_SIZE = 1000000; // размер блока данных для сортировки
    private static final int CHUNK_SIZE = 10; // размер блока данных для сортировки

    public File sortFile(File inputFile) {
        try {
            List<File> chunkFiles = splitFile(inputFile); // разбиение файла на блоки

            // создание пула потоков для сортировки блоков
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            List<Future<File>> sortedChunkFiles = new ArrayList<>();

            // сортировка каждого блока в отдельном потоке
            for (File chunkFile : chunkFiles) {
                Callable<File> task = () -> {
                    BufferedReader reader = new BufferedReader(new FileReader(chunkFile));
                    List<Long> longs = new ArrayList<>();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        longs.add(Long.valueOf(line));
                    }

                    reader.close();
                    longs.sort(Long::compareTo); // сортировка данных

                    // запись отсортированных данных в новый файл
                    File sortedChunkFile = new File(chunkFile.getAbsolutePath() + ".sorted");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(sortedChunkFile));

                    for (Long sortedLine : longs) {
                        writer.write(sortedLine.toString());
                        writer.newLine();
                    }

                    writer.close();
                    return sortedChunkFile;
                };

                sortedChunkFiles.add(executorService.submit(task));
            }

            executorService.shutdown();

            // ожидание завершения сортировки всех блоков
            while (!executorService.isTerminated()) {
                Thread.sleep(1000);
            }

            // объединение отсортированных блоков в один файл
            return mergeSortedFiles(sortedChunkFiles);

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    // разбиение файла на блоки
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

    // запись блока данных в файл
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

    // объединение отсортированных блоков в один файл
    private File mergeSortedFiles(List<Future<File>> sortedChunkFiles) throws IOException, ExecutionException, InterruptedException {
        List<File> files = new ArrayList<>();
        for (Future<File> sortedChunkFile : sortedChunkFiles) {
            files.add(sortedChunkFile.get());
        }

        File outputFile = new File("output.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        List<BufferedReader> readers = new ArrayList<>();

        // открытие всех файлов для чтения
        for (File file : files) {
            readers.add(new BufferedReader(new FileReader(file)));
        }

        // получение первых строк из всех файлов
        List<String> lines = new ArrayList<>(files.size());
        for (BufferedReader reader : readers) {
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
        }

        // сортировка строк и запись отсортированных данных в выходной файл
        while (!lines.isEmpty()) {
            lines.sort(String::compareTo);
            String minLine = lines.get(0);
            writer.write(minLine);
            writer.newLine();

            // получение следующей строки из файла, из которого была взята минимальная строка
            for (int i = 0; i < readers.size(); i++) {
                BufferedReader reader = readers.get(i);

                if (minLine.equals(lines.get(i))) {
                    String nextLine = reader.readLine();
                    if (nextLine != null) {
                        lines.set(i, nextLine);
                    } else {
                        // закрытие файла, если все строки из него уже считаны
                        reader.close();
                        files.get(i).delete(); // удаление временного файла
                        readers.remove(i);
                        lines.remove(i);
                    }
                    break;
                }
            }
        }
        writer.close();
        return outputFile;
    }
}

