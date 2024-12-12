package giftService;

import dao.GiftDao;
import model.Sweet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GiftService implements AutoCloseable {
    private final GiftDao giftDao;

    public GiftService(GiftDao giftDao) {
        this.giftDao = giftDao;
    }

    // Додає новий подарунок
    public void addGift(String giftName) {
        try {
            giftDao.addGift(giftName); // Передаємо giftName у DAO
            System.out.println("Gift added successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add gift: " + e.getMessage());
        }
    }


    // Отримує ID останнього подарунка
    public int getLatestGiftId() {
        try {
            return giftDao.getLatestGiftId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    // Додає нові солодощі до подарунку та оновлює загальну вагу
    public void addSweet(int giftId, String sweetName, double weight, double sugarContent) {
        try {
            giftDao.addSweet(giftId, sweetName, weight, sugarContent);
            System.out.println("Sweet added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Виводить список подарунків
    public void displayGifts() {
        try {
            ResultSet rs = giftDao.displayGifts();
            System.out.println("\n--- List of Gifts ---");
            while (rs.next()) {
                int giftId = rs.getInt("GiftID");
                String giftName = rs.getString("GiftName");
                double totalWeight = rs.getDouble("TotalWeight");
                System.out.println("Gift ID: " + giftId + ", Name: " + giftName + ", Total Weight: " + totalWeight + "g");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sweet> findSweetsBySugarContent(int giftId, int minSugar, int maxSugar) {
        try {
            return giftDao.findSweetsBySugarContent(giftId, minSugar, maxSugar);
        } catch (SQLException e) {
            System.err.println("Failed to find sweets by sugar content: " + e.getMessage());
            return List.of(); // Повертаємо порожній список у разі помилки
        }
    }

    public List<Sweet> getSweetsByGiftId(int giftId) {
        try{
            return giftDao.SortSweets(giftId);
        }catch (SQLException e){
            System.err.println("Failed to find sweets: " + e.getMessage());
            return List.of();
        }
    }

    public void deleteGift(int giftId) {
        try {
            giftDao.deleteGift(giftId);
            System.out.println("Gift with ID " + giftId + " deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to delete gift: " + e.getMessage());
        }
    }


    @Override
    public void close() throws Exception {
        giftDao.close();
    }


}
