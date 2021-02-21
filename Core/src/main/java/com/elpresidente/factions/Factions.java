package com.elpresidente.factions;

import com.elpresidente.faction.Faction;

import java.util.List;

public class Factions {
    List<Faction> factions;

    public Factions(List<Faction> factions){
        this.factions = factions;
    }

    public void populate(){
        float grow = (float) Math.random() *10 +1;
        int numberOfNewPopulation = (int) (getTotalNumberOfPartisan() * grow);

        addPopulation(numberOfNewPopulation);

    }

    public void addPopulation(int numberOfPeople){
        int n = numberOfPeople >= 0 ? 1 : -1;
        for (int i = 0; i <  Math.abs(numberOfPeople); i++) {
            factions.get((int) (Math.random() * factions.size()) ).addPartisanNumber(n);
        }
    }

    public Float getGlobalSatisfaction(){
        return  factions.stream()
                .map(faction -> faction.getSatisfaction()*faction.getPartisanNumber())
                .reduce(0, Integer::sum) / (float)getTotalNumberOfPartisan();
    }

    private Integer getTotalNumberOfPartisan(){
        return factions.stream()
                .map(Faction::getPartisanNumber)
                .reduce(0, Integer::sum);
    }
}
