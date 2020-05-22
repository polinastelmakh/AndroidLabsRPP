package com.example.lab5;

import java.util.LinkedList;
import java.util.List;

public class SpeciesInfo {
    private static SpeciesInfo instance;
    private List<Species> species;
    private SpeciesInfo() {
    }

    public static SpeciesInfo createInstance(List<Species> species) {
        if(instance == null) {
            instance = new SpeciesInfo();
            instance.setSpecies(species);
        }
            return instance;
    }

    private void setSpecies(List<Species> species) {
        this.species = species;
    }

    public String getBreedId(String name) {
        for(Species species : this.species) {
            if(species.getName() == name) {
                return species.getId();
            }
        }
        return "";
    }

    public static SpeciesInfo getInstance() {
        return instance;
    }

    public List<String> getBreedNames() {
        List<String> names = new LinkedList<>();
        for(Species species : this.species) {
            names.add(species.getName());
        }
        return names;
    }
}
