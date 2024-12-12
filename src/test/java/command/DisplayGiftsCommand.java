package command;

import giftService.GiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class DisplayGiftsCommandTest {
    private GiftService service;
    private DisplayGiftsCommand command;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(GiftService.class); // Мокаємо GiftService
        command = new DisplayGiftsCommand(service);
    }

    @Test
    void testExecute() {
        // Викликаємо метод execute
        command.execute();

        // Перевіряємо, чи викликаний метод service.displayGifts
        verify(service).displayGifts();
    }
}