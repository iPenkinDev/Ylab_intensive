package io.ylab.task5.lesson05.sqlquerybuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SQLQueryBuilderImpl implements SQLQueryBuilder {

    private final DataSource dataSource;

    public SQLQueryBuilderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String queryForTable(String tableName) throws SQLException {

        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        ResultSet rs = metaData.getTables(null, null, tableName, null);
        if (!rs.next()) {
            return null;
        }

        List<String> columns = new ArrayList<>();
        ResultSet columnsRs = metaData.getColumns(null, null, tableName, null);
        while (columnsRs.next()) {
            columns.add(columnsRs.getString("COLUMN_NAME"));
        }

        String query = "SELECT ";
        for (int i = 0; i < columns.size(); i++) {
            query += columns.get(i);
            if (i < columns.size() - 1) {
                query += ", ";
            }
        }
        query += " FROM " + tableName;

        return query;
    }

    @Override
    public List<String> getTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
        ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }
}
