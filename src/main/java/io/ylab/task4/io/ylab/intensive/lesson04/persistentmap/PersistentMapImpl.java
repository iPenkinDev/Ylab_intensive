package io.ylab.task4.io.ylab.intensive.lesson04.persistentmap;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, методы которого надо реализовать 
 */
@SuppressWarnings("SqlResolve")
public class PersistentMapImpl implements PersistentMap {

  private DataSource dataSource;
  private String mapName;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    this.mapName = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT value FROM persistent_map WHERE map_name=? AND KEY=?");
      statement.setString(1, mapName);
      statement.setString(2, key);
      ResultSet rs = statement.executeQuery();
      return rs.next();
    }
  }

  @Override
  public List<String> getKeys() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT KEY FROM persistent_map WHERE map_name=?");
      statement.setString(1, mapName);
      ResultSet rs = statement.executeQuery();
      List<String> keys = new ArrayList<>();
      while (rs.next()) {
        keys.add(rs.getString("KEY"));
      }
      return keys;
    }
  }

  @Override
  public String get(String key) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("SELECT value FROM persistent_map WHERE map_name=? AND KEY=?");
      statement.setString(1, mapName);
      statement.setString(2, key);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        return rs.getString("value");
      } else {
        return null;
      }
    }
  }

  @Override
  public void remove(String key) throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM persistent_map WHERE map_name=? AND KEY=?");
      statement.setString(1, mapName);
      statement.setString(2, key);
      statement.executeUpdate();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {
    remove(key);
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("INSERT INTO persistent_map (map_name, KEY, value) VALUES (?, ?, ?)");
      statement.setString(1, mapName);
      statement.setString(2, key);
      statement.setString(3, value);
      System.out.println("dao: " + mapName + " " + key + " " + value);
      statement.executeUpdate();
      System.out.println("statement: " + statement);
    }
  }

  @Override
  public void clear() throws SQLException {
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM persistent_map WHERE map_name=?");
      statement.setString(1, mapName);
      statement.executeUpdate();
    }
  }
}
