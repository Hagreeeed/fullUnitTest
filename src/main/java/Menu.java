
import giftService.GiftService;
import command.*;
import command.Command;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Comparator;

class Menu {
    private final Map<Integer, Command> menu = new HashMap<>();
    private final Scanner scanner;


    public Menu(GiftService dbConnector, Scanner scanner) {
        this.scanner = scanner;
        populateMenu(dbConnector);
    }

    // Метод для заповнення меню
    private void populateMenu(GiftService dbConnector) {
        menu.put(1, new AddGiftCommand(dbConnector, scanner));
        menu.put(2, new AddSweetCommand(dbConnector, scanner));
        menu.put(3, new DisplayGiftsCommand(dbConnector));
        menu.put(4, new FindSweetsBySugarContentCommand(dbConnector, scanner));
        menu.put(5, new DeleteGiftCommand(dbConnector, scanner));
        menu.put(6, new SortSweetsCommand(dbConnector, scanner));
    }

    // Метод для виведення меню
    private void displayMenu() {
        System.out.println("\n--- New Year Gift Menu ---");
        System.out.println("0. Exit");

        for (var element : menu.entrySet()) {
            System.out.println(element.getKey() + ". " + element.getValue().getDescription());
        }
    }

    // Основний метод для запуску меню
    public void run() {
        while (true) {
            displayMenu(); // Виклик нового методу

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очищення буфера після nextInt()

            if (choice == 0) {
                break;
            }

            Command command = menu.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
