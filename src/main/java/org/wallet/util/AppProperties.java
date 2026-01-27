package org.wallet.util;

import java.io.InputStream;
import java.util.Properties;
//application properties util
public class AppProperties {

    private static final Properties props = new Properties();

    static {
        try (InputStream in =
                     AppProperties.class
                             .getClassLoader()
                             .getResourceAsStream("liquibase.properties")) {

            props.load(in);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}
