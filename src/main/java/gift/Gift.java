package gift;

import model.Sweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Gift {
    private int id;
    private String name;
    private List<Sweet> sweets = new ArrayList<>();

    public Gift(int id, String name, double totalWeight) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addSweet(Sweet sweet) {
        sweets.add(sweet);
    }

    public List<Sweet> getSweets() {
        return sweets;
    }

    public double getTotalWeight() {
        return sweets.stream().mapToDouble(Sweet::getWeight).sum();
    }

    public void sortSweets(Comparator<Sweet> comparator) {
        Collections.sort(sweets, comparator);
    }

    public List<Sweet> findSweetsBySugarContent(double min, double max) {
        List<Sweet> result = new ArrayList<>();
        for (Sweet sweet : sweets) {
            if (sweet.getSugarContent() >= min && sweet.getSugarContent() <= max) {
                result.add(sweet);
            }
        }
        return result;
    }
}
