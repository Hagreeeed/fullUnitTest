import SQLDataBase.DatabaseConnector;
import dao.GiftDao;
import giftService.GiftService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in);
             DatabaseConnector connector = new DatabaseConnector()) { // Передаємо DatabaseConnector

            // Ініціалізація DAO та сервісу
            GiftDao giftDao = new GiftDao(connector);
            GiftService giftService = new GiftService(giftDao);

            // Передача сервісу в меню
            Menu menu = new Menu(giftService,  scanner);
            menu.run();
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
