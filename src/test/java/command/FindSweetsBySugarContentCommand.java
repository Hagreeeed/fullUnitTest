package command;

import giftService.GiftService;
import model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FindSweetsBySugarContentCommandTest {
    private GiftService service;
    private Scanner scanner;
    private FindSweetsBySugarContentCommand command;

    @BeforeEach
    void setUp() {
        service = Mockito.mock(GiftService.class); // Мокаємо GiftService
        scanner = Mockito.mock(Scanner.class);     // Мокаємо Scanner
        command = new FindSweetsBySugarContentCommand(service, scanner);
    }

    @Test
    void testExecuteWithSweetsFound() {
        // Імітуємо ввід користувача
        when(scanner.nextInt()).thenReturn(1, 10, 50); // giftId, minSugar, maxSugar
        when(service.findSweetsBySugarContent(1, 10, 50)).thenReturn(List.of(
                new Sweet("Candy", 20, 15.0),
                new Sweet("Chocolate", 30, 50.0)
        ));

        // Викликаємо метод execute
        command.execute();

        // Перевіряємо, чи метод Service викликаний із правильними параметрами
        verify(service).findSweetsBySugarContent(1, 10, 50);

        // Перевіряємо, що Scanner викликався у правильній послідовності
        verify(scanner, times(3)).nextInt();
        verify(scanner).nextLine(); // Очищення буфера
    }

    @Test
    void testExecuteWithNoSweetsFound() {
        // Імітуємо ввід користувача
        when(scanner.nextInt()).thenReturn(1, 10, 50); // giftId, minSugar, maxSugar
        when(service.findSweetsBySugarContent(1, 10, 50)).thenReturn(List.of());

        // Викликаємо метод execute
        command.execute();

        // Перевіряємо, чи метод Service викликаний із правильними параметрами
        verify(service).findSweetsBySugarContent(1, 10, 50);

        // Перевіряємо, що Scanner викликався у правильній послідовності
        verify(scanner, times(3)).nextInt();
        verify(scanner).nextLine(); // Очищення буфера
    }
}