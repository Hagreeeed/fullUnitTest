package command;
import giftService.GiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class DeleteGiftCommandTest {
    private GiftService service;
    private Scanner scanner;
    private DeleteGiftCommand command;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(GiftService.class); // Мокаємо GiftService
        scanner = Mockito.mock(Scanner.class);     // Мокаємо Scanner
        command = new DeleteGiftCommand(service, scanner);
    }

    @Test
    void testExecute() {
        // Імітуємо ввід користувача
        when(scanner.nextInt()).thenReturn(1); // Для ID подарунка

        // Викликаємо метод execute
        command.execute();

        // Перевіряємо, чи викликаний метод service.deleteGift із правильними параметрами
        verify(service).deleteGift(1);

        // Перевіряємо, що Scanner викликався у правильній послідовності
        verify(scanner).nextInt();
        verify(scanner).nextLine(); // Очищення буфера
    }
}