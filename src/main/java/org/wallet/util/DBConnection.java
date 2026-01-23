package org.wallet.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static final HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppProperties.get("db.url"));
        config.setUsername(AppProperties.get("db.username"));
        config.setPassword(AppProperties.get("db.password"));
        config.setDriverClassName(AppProperties.get("db.driver"));
        config.setMaximumPoolSize(
                Integer.parseInt(AppProperties.get("db.pool.size"))
        );

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

