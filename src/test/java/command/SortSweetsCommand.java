package command;

import command.SortSweetsCommand;
import giftService.GiftService;
import model.Sweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SortSweetsCommandTest {

    @Mock
    private GiftService giftService;

    @Mock
    private Scanner scanner;

    private SortSweetsCommand sortSweetsCommand;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sortSweetsCommand = new SortSweetsCommand(giftService, scanner);

        // Перехоплюємо виведення в консоль
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testExecute_whenSweetsExist() {
        // Підготовка даних
        Sweet sweet1 = new Sweet("Candy", 5.0, 50.0);
        Sweet sweet2 = new Sweet("Chocolate", 10.0, 100.0);
        List<Sweet> sweets = Arrays.asList(sweet1, sweet2);

        // Мокаємо поведінку сервісу та сканера
        when(scanner.nextInt()).thenReturn(1);  // Симулюємо введення Gift ID
        when(giftService.getSweetsByGiftId(1)).thenReturn(sweets);

        // Викликаємо метод
        sortSweetsCommand.execute();

        // Перевірка взаємодій
        verify(giftService).getSweetsByGiftId(1);
        verify(scanner).nextInt();

        // Перевіряємо, чи виведено правильне повідомлення для введення ID подарунка
        String output = outputStream.toString();
        assertTrue(output.contains("Enter Gift ID to sort sweets:"));
    }

    @Test
    public void testExecute_whenNoSweetsFound() {
        // Мокаємо ситуацію, коли немає солодощів для вказаного Gift ID
        when(scanner.nextInt()).thenReturn(1);
        when(giftService.getSweetsByGiftId(1)).thenReturn(List.of());

        // Викликаємо метод
        sortSweetsCommand.execute();

        // Перевірка взаємодій
        verify(giftService).getSweetsByGiftId(1);
        verify(scanner).nextInt();

        // Перевіряємо, чи виведено правильне повідомлення "No sweets found for the given Gift ID."
        String output = outputStream.toString();
        assertTrue(output.contains("No sweets found for the given Gift ID."));
    }
}
