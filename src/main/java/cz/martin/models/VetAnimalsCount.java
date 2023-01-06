package cz.martin.models;

public class VetAnimalsCount {
    private String name;
    private int count;

    public VetAnimalsCount(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
