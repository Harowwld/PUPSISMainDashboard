package com.example.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.databaseOperations.DBConnection;
import com.example.auth.PasswordHandler;

public class AuthenticationService {

    public static boolean authenticate(String input, String password) {
        boolean isAuthenticated = false;
        boolean isEmail = input.contains("@");

        String query = isEmail ? "SELECT password FROM students WHERE email = ?" : "SELECT password FROM students WHERE student_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, input);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                isAuthenticated = PasswordHandler.verifyPassword(password, storedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Optionally, handle with proper logger or alert.
        }

        return isAuthenticated;
    }
}