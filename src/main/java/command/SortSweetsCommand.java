package command;

import giftService.GiftService;
import model.Sweet;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class SortSweetsCommand implements Command {
    private final GiftService service;
    private final Scanner scanner;

    public SortSweetsCommand(GiftService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter Gift ID to sort sweets: ");
        int giftId = scanner.nextInt();
        scanner.nextLine();

        List<Sweet> foundSweets = service.getSweetsByGiftId(giftId);
        if (foundSweets.isEmpty()) {
            System.out.println("No sweets found for the given Gift ID.");
        } else {
            foundSweets.sort((Comparator.comparingDouble(Sweet::getSugarContent)));

            System.out.println("Sweets sorted successfully:");
            for (Sweet sweet : foundSweets) {
                System.out.println(sweet.getName() +
                        " - Sugar Content: " + sweet.getSugarContent() + "g" +
                        " - Weight: " + sweet.getWeight() + "g");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Sort sweets by sugar content.";
    }
}
