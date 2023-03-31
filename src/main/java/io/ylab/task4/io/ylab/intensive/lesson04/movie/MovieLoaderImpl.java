package io.ylab.task4.io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

@SuppressWarnings("SqlResolve")
public class MovieLoaderImpl implements MovieLoader {
  private static final String INSERT_SQL = "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String DELIMITER = ";";
  private DataSource dataSource;

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {
    try (Connection conn = dataSource.getConnection();
         BufferedReader reader = new BufferedReader(new FileReader(file));
         PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {

      String line;
      reader.readLine(); // skip header
      reader.readLine(); // skip header
      while ((line = reader.readLine()) != null) {
        Movie movie = parseMovie(line);
        setStatementValues(stmt, movie);
        stmt.executeUpdate();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Movie parseMovie(String line) {
    String[] fields = line.split(DELIMITER);
    System.out.println(Arrays.toString(fields));
    Movie movie = new Movie();
    movie.setYear(parseInteger(fields[0]));
    movie.setLength(parseInteger(fields[1]));
    movie.setTitle(fields[2]);
    movie.setSubject(fields[3]);
    movie.setActors(fields[4]);
    movie.setActress(fields[5]);
    movie.setDirector(fields[6]);
    movie.setPopularity(parseInteger(fields[7]));
    movie.setAwards(parseBoolean(fields[8]));
    return movie;
  }

  private Integer parseInteger(String s) {
    return s.isEmpty() ? null : Integer.parseInt(s);
  }

  private Boolean parseBoolean(String s) {
    return s.isEmpty() ? null : Boolean.parseBoolean(s);
  }
  private String parseString(String s) {
    return s.isEmpty() ? null : s;
  }

  private void setStatementValues(PreparedStatement stmt, Movie movie) throws SQLException {
    stmt.setObject(1, movie.getYear(), Types.INTEGER);
    stmt.setObject(2, movie.getLength(), Types.INTEGER);
    stmt.setString(3, movie.getTitle());
    stmt.setString(4, movie.getSubject());
    stmt.setString(5, movie.getActors());
    stmt.setString(6, movie.getActress());
    stmt.setString(7, movie.getDirector());
    stmt.setObject(8, movie.getPopularity(), Types.INTEGER);
    stmt.setObject(9, movie.getAwards(), Types.BOOLEAN);
    if (movie.getYear() == null) {
      stmt.setNull(1, Types.INTEGER);
    }
    if (movie.getLength() == null) {
      stmt.setNull(2, Types.INTEGER);
    }
    if (movie.getTitle().isEmpty()) {
      stmt.setNull(3, Types.VARCHAR);
    }
    if(movie.getSubject().isEmpty()) {
      stmt.setNull(4, Types.VARCHAR);
    }
    if (movie.getActors().isEmpty()) {
      stmt.setNull(5, Types.VARCHAR);
    }
    if (movie.getActress().isEmpty()) {
      stmt.setNull(6, Types.VARCHAR);
    }
    if (movie.getDirector().isEmpty()) {
      stmt.setNull(7, Types.VARCHAR);
    }
    if (movie.getPopularity() == null) {
      stmt.setNull(8, Types.INTEGER);
    }
    if (movie.getAwards() == null) {
      stmt.setNull(9, Types.BOOLEAN);
    }
  }
}
