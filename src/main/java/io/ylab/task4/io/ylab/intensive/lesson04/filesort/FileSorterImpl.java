package io.ylab.task4.io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.concurrent.*;

@SuppressWarnings({"SqlResolve", "ResultOfMethodCallIgnored"})
public class FileSorterImpl implements FileSorter {
    private DataSource dataSource;
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    public FileSorterImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File inputFile) {

        // read the numbers from the input file and write to the database
        long start = System.currentTimeMillis();
        processInputNew(inputFile);
        System.out.println("input took " + (System.currentTimeMillis() - start) + " ms");

        // sort the numbers in the database and write to the output file
        start = System.currentTimeMillis();
        File outputFile = proccessOutput();
        System.out.println("output took " + (System.currentTimeMillis() - start) + " ms");

        return outputFile;
    }

    private File proccessOutput() {
        File outputFile = null;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT val FROM numbers ORDER BY val DESC");
             BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {

            int colIndex = resultSet.findColumn("val");
            while (resultSet.next()) {
                String val = resultSet.getString(colIndex);
                writer.write(val);
                writer.newLine();
            }

            outputFile = new File("output.txt"); // set the output file to the variable
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        return outputFile;
    }

    private void processInputNew(File inputFile) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                PreparedStatement preparedStatement =
                        connection.prepareStatement("INSERT INTO numbers (val) VALUES (?)");
                while ((line = reader.readLine()) != null) {
                    long val = Long.parseLong(line);
                    preparedStatement.setLong(1, val);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                preparedStatement.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}


