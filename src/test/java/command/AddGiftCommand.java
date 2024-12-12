package command;

import giftService.GiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AddGiftCommandTest {

    private GiftService giftServiceMock;
    private Scanner scannerMock;
    private AddGiftCommand addGiftCommand;

    @BeforeEach
    void setUp() {
        giftServiceMock = mock(GiftService.class); // Мок для GiftService
        scannerMock = mock(Scanner.class); // Мок для Scanner
        addGiftCommand = new AddGiftCommand(giftServiceMock, scannerMock);
    }

    @Test
    void testExecute() {
        // Arrange
        String giftName = "Birthday Gift";
        when(scannerMock.nextLine()).thenReturn(giftName); // Емуляція вводу з консолі

        // Act
        addGiftCommand.execute();

        // Assert
        verify(scannerMock, times(1)).nextLine(); // Перевірка, чи викликаний метод nextLine()
        verify(giftServiceMock, times(1)).addGift(giftName); // Перевірка, чи викликаний метод addGift() з правильним аргументом
    }

    @Test
    void testGetDescription() {
        // Act
        String description = addGiftCommand.getDescription();

        // Assert
        assertEquals("Add Gift", description, "Description should match the expected value");
    }
}
