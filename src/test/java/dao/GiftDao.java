package dao;

import dao.GiftDao;
import SQLDataBase.DatabaseConnector;
import model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftDaoTest {

    private DatabaseConnector mockDbConnector;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;
    private GiftDao giftDao;

    @BeforeEach
    void setUp() throws SQLException {
        mockDbConnector = mock(DatabaseConnector.class);
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDbConnector.getConnection()).thenReturn(mockConnection);

        giftDao = new GiftDao(mockDbConnector);
    }

    @Test
    void testAddGift() throws SQLException {
        String giftName = "Birthday Gift";

        // Моки
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockDbConnector.getConnection()).thenReturn(mockConnection);

        // Виконання
        giftDao.addGift(giftName);

        // Перевірка
        verify(mockPreparedStatement).setString(1, giftName);
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    void testGetLatestGiftId() throws SQLException {
        String query = "SELECT TOP 1 GiftID FROM Gifts ORDER BY GiftID DESC";
        when(mockDbConnector.executeQuery(query)).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("GiftID")).thenReturn(42);

        int latestGiftId = giftDao.getLatestGiftId();

        assertEquals(42, latestGiftId);
    }

    @Test
    void testAddSweet() throws SQLException {
        // Параметри для тесту
        int giftId = 1;
        String sweetName = "Chocolate";
        double weight = 100.0;
        double sugarContent = 50.0;
        String query = "INSERT INTO Sweets (GiftID, Name, Weight, SugarContent) VALUES (?, ?, ?, ?)";

        // Мокування підготовленого statement та підключення
        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);

        // Виклик методу addSweet
        giftDao.addSweet(giftId, sweetName, weight, sugarContent);

        // Перевірка, що правильні значення передаються в PreparedStatement
        verify(mockPreparedStatement).setInt(1, giftId);
        verify(mockPreparedStatement).setString(2, sweetName);
        verify(mockPreparedStatement).setDouble(3, weight);
        verify(mockPreparedStatement).setDouble(4, sugarContent);

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    void testFindSweetsBySugarContent() throws SQLException {
        int giftId = 1;
        double minSugar = 10.0;
        double maxSugar = 20.0;
        String query = "SELECT Name, Weight, SugarContent FROM Sweets WHERE GiftID = ? AND SugarContent BETWEEN ? AND ?";

        when(mockConnection.prepareStatement(query)).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getString("Name")).thenReturn("Candy");
        when(mockResultSet.getDouble("Weight")).thenReturn(50.0);
        when(mockResultSet.getDouble("SugarContent")).thenReturn(15.0);

        List<Sweet> sweets = giftDao.findSweetsBySugarContent(giftId, minSugar, maxSugar);

        assertEquals(1, sweets.size());
        Sweet sweet = sweets.get(0);
        assertEquals("Candy", sweet.getName());
        assertEquals(50.0, sweet.getWeight());
        assertEquals(15.0, sweet.getSugarContent());
    }

    @Test
    void testDeleteGift() throws SQLException {
        int giftId = 1;
        String deleteSweetsQuery = "DELETE FROM Sweets WHERE GiftID = ?";
        String deleteGiftQuery = "DELETE FROM Gifts WHERE GiftID = ?";

        when(mockConnection.prepareStatement(deleteSweetsQuery)).thenReturn(mockPreparedStatement);
        when(mockConnection.prepareStatement(deleteGiftQuery)).thenReturn(mockPreparedStatement);

        giftDao.deleteGift(giftId);

        verify(mockPreparedStatement, times(2)).setInt(1, giftId);
        verify(mockPreparedStatement, times(2)).executeUpdate();
    }
}