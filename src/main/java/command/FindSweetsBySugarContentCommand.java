package command;

import giftService.GiftService;
import model.Sweet;

import java.util.List;
import java.util.Scanner;

public class FindSweetsBySugarContentCommand implements Command {
    private final GiftService service;
    private final Scanner scanner;

    public FindSweetsBySugarContentCommand(GiftService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter Gift ID: ");
        int giftId = scanner.nextInt();
        System.out.print("Enter minimum sugar content: ");
        int minSugar = scanner.nextInt();
        System.out.print("Enter maximum sugar content: ");
        int maxSugar = scanner.nextInt();
        scanner.nextLine();

        List<Sweet> foundSweets = service.findSweetsBySugarContent(giftId, minSugar, maxSugar);
        if (foundSweets.isEmpty()) {
            System.out.println("No sweets found within the given sugar content range.");
        } else {
            System.out.println("Sweets found:");
            for (Sweet sweet : foundSweets) {
                System.out.println(sweet.getName() + " - Sugar Content: " + sweet.getSugarContent() + "g" + " Weight - " + sweet.getWeight());
            }
        }
    }

    @Override
    public String getDescription() {
        return "Find sweets by sugar content.";
    }
}
