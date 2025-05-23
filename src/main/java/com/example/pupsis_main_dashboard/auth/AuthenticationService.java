package com.example.pupsis_main_dashboard.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.pupsis_main_dashboard.databaseOperations.DBConnection;

public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public static boolean authenticate(String input, String password) {
        boolean isAuthenticated = false;
        boolean isEmail = input.contains("@");

        String query = isEmail 
            ? "SELECT password FROM students WHERE LOWER(email) = LOWER(?)" 
            : "SELECT password FROM students WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, input.toLowerCase());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                isAuthenticated = PasswordHandler.verifyPassword(password, storedPassword);
            }

        } catch (SQLException e) {
            logger.error("SQL error during authentication", e);
        }

        return isAuthenticated;
    }

}