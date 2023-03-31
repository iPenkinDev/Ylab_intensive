package io.ylab.task4.io.ylab.intensive.lesson04.persistentmap;


import io.ylab.task4.io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);

    // Написать код демонстрации работы
    persistentMap.init("persistent_map");

    // add value
    persistentMap.put("key1", "value1");
    persistentMap.put("key2", "value2");
    persistentMap.put("key3", "value3");

    assertEquals(persistentMap.get("key1"), "value1");
    assertEquals(persistentMap.get("key2"), "value2");
    assertEquals(persistentMap.get("key3"), "value3");

    System.out.println("get from map= " + persistentMap.get("key1"));

    // get value
    String value = persistentMap.get("key1");
    assertEquals(value, "value1");
    System.out.println("Value for key 'key1': " + value);

    // contains key
    boolean containsKey = persistentMap.containsKey("key2");
    assertEquals(containsKey, true);
    System.out.println("Contains key 'key2': " + containsKey);

    // get all keys
    List<String> keys = persistentMap.getKeys();
    ArrayList expectedKey = new ArrayList();
    expectedKey.add("key1");
    expectedKey.add("key2");
    expectedKey.add("key3");
    assertEquals(keys, expectedKey);

    System.out.println("persistentMap.getKeys=" + keys);

    // delete key
    persistentMap.remove("key3");
    assertEquals(persistentMap.get("key3"), null);

    // clear map
   persistentMap.clear();
   assertEquals(persistentMap.getKeys(), Collections.emptyList());
  }
    // assertEquals
  private static void assertEquals(Object actual, Object expected) {
    if (actual == null && expected == null) {
      return;
    }
    if (actual == null) {
      throw new AssertionError("Expected: " + expected + ", actual: " + actual);
    }
    if (!actual.equals(expected)) {
      throw new AssertionError("Expected: " + expected + ", actual: " + actual);
    }
  }

  public static DataSource initDb() throws SQLException {
    String createMapTable = ""
                                + "drop table if exists persistent_map; "
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar\n"
                                + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
