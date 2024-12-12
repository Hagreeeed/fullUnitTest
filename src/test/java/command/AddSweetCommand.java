package command;

import command.AddSweetCommand;
import giftService.GiftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class AddSweetCommandTest {

    @Mock
    private GiftService giftService;

    @Mock
    private Scanner scanner;

    private AddSweetCommand addSweetCommand;

    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        addSweetCommand = new AddSweetCommand(giftService, scanner);

        // Перехоплюємо виведення в консоль
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testExecute() {
        // Мокаємо введення користувача для створення солодощів
        when(scanner.nextLine()).thenReturn("Candy");  // Введення назви солодоща
        when(scanner.nextDouble()).thenReturn(50.0, 5.0);  // Введення ваги і вмісту цукру
        when(scanner.nextInt()).thenReturn(1);  // Введення Gift ID як ціле число

        // Викликаємо метод
        addSweetCommand.execute();

        // Перевірка взаємодій з моком
        verify(scanner).nextLine();  // Перевірка, чи викликано nextLine() для введення назви
        verify(scanner, times(2)).nextDouble();  // Перевірка, чи викликано nextDouble() для ваги та цукру
        verify(scanner).nextInt();  // Перевірка, чи викликано nextInt() для введення ID
        verify(giftService).addSweet(1, "Candy", 50.0, 5.0);  // Перевірка виклику addSweet
    }
}
