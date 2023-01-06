package cz.martin.models;

public class VetWithAnimals {
    private String name;
    private String[] animals;

    public VetWithAnimals(String name, String[] animals) {
        this.name = name;
        this.animals = animals;
    }

    public String getName() {
        return name;
    }

    public String[] getAnimals() {
        return animals;
    }

    public int getCount() {
        return animals.length;
    }
}
