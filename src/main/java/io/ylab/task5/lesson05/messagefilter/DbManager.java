package io.ylab.task5.lesson05.messagefilter;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlResolve")
@Component
public class DbManager {

    private JdbcTemplate jdbcTemplate;

    public DbManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() throws SQLException, IOException {
        boolean tableExists = false;

        DatabaseMetaData meta = jdbcTemplate.getDataSource().getConnection().getMetaData();

        ResultSet badWords = meta.getTables(null, null, "bad_words", null);
        if (!badWords.next()) {
            System.out.println("Table bad_words does not exist");
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS bad_words (word VARCHAR)");
        } else {
            System.out.println("Table bad_words exists");
            jdbcTemplate.update("DELETE FROM bad_words");
        }

        InputStream is = new FileInputStream("./mat.csv");
        System.out.println("Loading bad words");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        int count = 0;
        List<Object[]> args = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            if (line.contains(" ")) {
                continue;
            }
            count++;
            args.add(new Object[]{line.trim().toLowerCase()});
        }
        jdbcTemplate.batchUpdate("INSERT INTO bad_words (word) VALUES (?)", args);
        reader.close();
        System.out.println("Loaded " + count + " bad words");
    }

    public boolean isBadWord(String word) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM bad_words WHERE word = ?",
                new Object[]{word.toLowerCase()}, Integer.class) > 0;
    }
}
