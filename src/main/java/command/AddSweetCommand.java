package command;

import giftService.GiftService;

import java.util.Scanner;

public class AddSweetCommand implements Command {
    private final GiftService service;
    private final Scanner scanner;

    public AddSweetCommand(GiftService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter sweet name: ");
        String sweetName = scanner.nextLine();

        System.out.print("Enter weight: ");
        double weight = scanner.nextDouble();

        System.out.print("Enter sugar content: ");
        double sugarContent = scanner.nextDouble();

        scanner.nextLine();  // Очистка буфера

        System.out.print("Enter the gift ID to add the sweet to: ");
        int giftId = scanner.nextInt();

        scanner.nextLine();  // Очистка буфера

        service.addSweet(giftId, sweetName, weight, sugarContent);  // Викликаємо метод Service для додавання солодоща
    }

    @Override
    public String getDescription() {
        return "Add Sweet";
    }
}
