package io.ylab.task4.io.ylab.intensive.lesson04.filesort;
import io.ylab.task4.io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class FileSorterTest {
  public static void main(String[] args) throws SQLException, IOException {
    DataSource dataSource = initDb();
    File data = new Generator().generate("data.txt", 1_000_000);
    FileSorter fileSorter = new FileSorterImpl(dataSource);
    System.out.println("isSorted before=" + new Validator(data).isSorted());
    File res = fileSorter.sort(data);
    System.out.println("isSorted after=" + new Validator(res).isSorted());
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;"
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }


}
