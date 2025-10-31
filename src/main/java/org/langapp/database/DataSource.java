package org.langapp.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum DataSource {

    INSTANCE();

    final HikariConfig hikariConfig = new HikariConfig();
    final HikariDataSource ds;

    DataSource(){
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/lang_db");
        hikariConfig.setUsername("admin");
        hikariConfig.setPassword("1234");
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(hikariConfig);
    }
    public Connection connect() throws SQLException {
        return ds.getConnection();
    }
}
