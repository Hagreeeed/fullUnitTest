package dao;


import SQLDataBase.DatabaseConnector;
import model.Sweet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftDao {
    private final DatabaseConnector dbConnector;

    public GiftDao(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // Додає новий подарунок
    public void addGift(String giftName) throws SQLException {
        String query = "INSERT INTO Gifts (GiftName, TotalWeight) VALUES (?, 0)";
        dbConnector.executeUpdate(query, giftName); // Параметр giftName встановлюється у PreparedStatement
    }


    // Отримує ID останнього подарунка
    public int getLatestGiftId() throws SQLException {
        String query = "SELECT TOP 1 GiftID FROM Gifts ORDER BY GiftID DESC";
        try (ResultSet rs = dbConnector.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("GiftID");
            } else {
                throw new SQLException("No gifts found in the database.");
            }
        }
    }

    // Додає нові солодощі до подарунку та оновлює загальну вагу
    public void addSweet(int giftId, String sweetName, double weight, double sugarContent) throws SQLException {
        String query = "INSERT INTO Sweets (GiftID, Name, Weight, SugarContent) VALUES (?, ?, ?, ?)";
        dbConnector.executeUpdate(query, giftId, sweetName, weight, sugarContent);
        updateTotalWeight(giftId);
    }

    // Оновлює загальну вагу подарунку
    private void updateTotalWeight(int giftId) throws SQLException {
        String query = "UPDATE Gifts SET TotalWeight = (SELECT SUM(Weight) FROM Sweets WHERE GiftID = ?) WHERE GiftID = ?";
        dbConnector.executeUpdate(query, giftId, giftId);
    }

    // Виводить список подарунків
    public ResultSet displayGifts() throws SQLException {
        String query = "SELECT * FROM Gifts";
        return dbConnector.executeQuery(query);
    }

    public List<Sweet> findSweetsBySugarContent(int giftId, double minSugar, double maxSugar) throws SQLException {
        String query = "SELECT Name, Weight, SugarContent FROM Sweets WHERE GiftID = ? AND SugarContent BETWEEN ? AND ?";
        List<Sweet> sweets = new ArrayList<>();

        // Отримання PreparedStatement через Connection
        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(query)) {
            stmt.setInt(1, giftId);
            stmt.setDouble(2, minSugar); // Використовуємо setDouble для точності
            stmt.setDouble(3, maxSugar);

            // Виконання запиту
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sweets.add(new Sweet(
                            rs.getString("Name"),
                            rs.getDouble("Weight"),
                            rs.getDouble("SugarContent")
                    ));
                }
            }
        }
        return sweets; // Повертаємо знайдені солодощі
    }

    public void deleteGift(int giftId) throws SQLException {
        String deleteSwits = "DELETE FROM Sweets WHERE GiftID = ?";

        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(deleteSwits)) {
            stmt.setInt(1, giftId);
            stmt.executeUpdate();
        }

        String query = "DELETE FROM Gifts WHERE GiftID = ?";

        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(query)) {
            stmt.setInt(1, giftId);
            stmt.executeUpdate();
        }

    }
    public List<Sweet> SortSweets(int giftId) throws SQLException {
        String query = "SELECT Name, Weight, SugarContent FROM Sweets WHERE GiftID = ?";
        List<Sweet> sweets = new ArrayList<>();

        try (PreparedStatement stmt = dbConnector.getConnection().prepareStatement(query)) {
            stmt.setInt(1, giftId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    sweets.add(new Sweet(
                            rs.getString("Name"),
                            rs.getDouble("Weight"),
                            rs.getDouble("SugarContent")
                    ));
                }
            }
        }
        return sweets;
    }

    public void close() {
    }
}
