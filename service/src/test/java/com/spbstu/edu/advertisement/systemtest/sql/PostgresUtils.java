package com.spbstu.edu.advertisement.systemtest.sql;

import com.spbstu.edu.advertisement.systemtest.constants.UrlConstants;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Log4j2
public class PostgresUtils {
    private static final String URL = "jdbc:postgresql://" + UrlConstants.DATABASE_URL + "/advertisement_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DELETE_USERS_QUERY = "truncate public.user cascade;";
    private static final String DELETE_ADS_QUERY = "truncate ad cascade;";

    public static void cleanUsersInDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(DELETE_USERS_QUERY);
                log.info("Users deleted!");
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }

    public static void cleanAdsInDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(DELETE_ADS_QUERY);
                log.info("Ads deleted!");
            }
        } catch (SQLException exception) {
            log.error(exception.getMessage());
        }
    }
}
