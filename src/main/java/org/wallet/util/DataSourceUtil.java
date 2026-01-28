package org.wallet.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceUtil {

    private static HikariDataSource dataSource;

    private DataSourceUtil() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceUtil.class) {
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig();

                    config.setJdbcUrl(AppProperties.get("db.url"));
                    config.setUsername(AppProperties.get("db.username"));
                    config.setPassword(AppProperties.get("db.password"));
                    config.setDriverClassName(AppProperties.get("db.driver"));
                    config.setMaximumPoolSize(10);
                    config.setMinimumIdle(2);
                    config.setPoolName("WalletHikariPool");

                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }
}
