package SQLDataBase;

import java.sql.*;

public class DatabaseConnector implements AutoCloseable {
    private Connection connection;

    public DatabaseConnector() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=NewYearGiftsDB;encrypt=true;trustServerCertificate=true",
                "sa",
                "1234"
        );
    }

    // Метод для отримання об'єкта Connection
    public Connection getConnection() {
        return connection;
    }

    // Метод для виконання запиту на додавання/оновлення даних
    public int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]); // Встановлення параметрів
            }
            return preparedStatement.executeUpdate();
        }
    }


    // Метод для виконання запиту на отримання даних
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Connection closed successfully.");
        }
    }

    public PreparedStatement prepareStatement(String query) {
        return null;
    }
}
