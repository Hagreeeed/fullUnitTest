package model;

// Base class for sweets
public class Sweet {
    private String name;
    private double weight;
    private double sugarContent;

    public Sweet(String name, double weight, double sugarContent) {
        this.name = name;
        this.weight = weight;
        this.sugarContent = sugarContent;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getSugarContent() {
        return sugarContent;
    }
}

