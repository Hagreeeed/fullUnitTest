package giftService;

import dao.GiftDao;
import model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftServiceTest {

    private GiftDao giftDaoMock;
    private GiftService giftService;

    @BeforeEach
    void setUp() {
        giftDaoMock = mock(GiftDao.class); // Мок об'єкт GiftDao
        giftService = new GiftService(giftDaoMock);
    }

    @Test
    void testAddGift() throws SQLException {
        // Arrange
        String giftName = "Birthday Gift";
        doNothing().when(giftDaoMock).addGift(giftName);

        // Act
        giftService.addGift(giftName);

        // Assert
        verify(giftDaoMock, times(1)).addGift(giftName);
    }

    @Test
    void testGetLatestGiftId() throws SQLException {
        // Arrange
        when(giftDaoMock.getLatestGiftId()).thenReturn(42);

        // Act
        int latestGiftId = giftService.getLatestGiftId();

        // Assert
        assertEquals(42, latestGiftId);
        verify(giftDaoMock, times(1)).getLatestGiftId();
    }

    @Test
    void testAddSweet() throws SQLException {
        // Arrange
        int giftId = 1;
        String sweetName = "Chocolate";
        double weight = 150.0;
        double sugarContent = 50.0;

        doNothing().when(giftDaoMock).addSweet(giftId, sweetName, weight, sugarContent);

        // Act
        giftService.addSweet(giftId, sweetName, weight, sugarContent);

        // Assert
        verify(giftDaoMock, times(1)).addSweet(giftId, sweetName, weight, sugarContent);
    }

    @Test
    void testFindSweetsBySugarContent() throws SQLException {
        // Arrange
        int giftId = 1;
        int minSugar = 10;
        int maxSugar = 50;

        List<Sweet> mockSweets = List.of(new Sweet("Candy", 20.0, 30.0));
        when(giftDaoMock.findSweetsBySugarContent(giftId, minSugar, maxSugar)).thenReturn(mockSweets);

        // Act
        List<Sweet> result = giftService.findSweetsBySugarContent(giftId, minSugar, maxSugar);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Candy", result.get(0).getName());
        verify(giftDaoMock, times(1)).findSweetsBySugarContent(giftId, minSugar, maxSugar);
    }

    @Test
    void testDeleteGift() throws SQLException {
        // Arrange
        int giftId = 1;
        doNothing().when(giftDaoMock).deleteGift(giftId);

        // Act
        giftService.deleteGift(giftId);

        // Assert
        verify(giftDaoMock, times(1)).deleteGift(giftId);
    }

    @Test
    void testClose() throws Exception {
        // Act
        giftService.close();

        // Assert
        verify(giftDaoMock, times(1)).close();
    }
}
