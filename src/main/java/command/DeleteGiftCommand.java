package command;

import giftService.GiftService;

import java.util.Scanner;

public class DeleteGiftCommand implements Command {
    private final GiftService service;
    private final Scanner scanner;

    public DeleteGiftCommand(GiftService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter Gift ID to delete: ");
        int giftId = scanner.nextInt();
        scanner.nextLine();
        service.deleteGift(giftId);
    }

    @Override
    public String getDescription() {
        return "Delete a Gift by ID.";
    }
}
