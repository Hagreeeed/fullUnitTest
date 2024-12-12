package command;

import giftService.GiftService;

public class DisplayGiftsCommand implements Command {
    private final GiftService service;

    public DisplayGiftsCommand(GiftService service) {
        this.service = service;
    }

    @Override
    public void execute() {
        service.displayGifts();  // Викликаємо метод Service для відображення подарунків
    }

    @Override
    public String getDescription() {
        return "Display Gifts";
    }
}
