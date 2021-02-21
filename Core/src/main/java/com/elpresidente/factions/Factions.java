package com.elpresidente.factions;

import com.elpresidente.faction.Faction;

import java.util.List;

public class Factions {
    List<Faction> factions;

    public Factions(List<Faction> factions){
        this.factions = factions;
    }

    public void corrupt(String factionName){
        Faction faction = getFaction(factionName);
        int price;
        if(faction == null) return;

        price = faction.getCorruptionPrice();
        faction.addSatisfaction(10);

        faction = getFaction("Loyalistes");
        if(faction == null) return;

        faction.addSatisfaction( -price/10);
    }

    private Faction getFaction(String name){
        Faction faction = null;

        for (Faction fac: factions) {
            if(fac.getName().equals(name)){
                faction = fac;
            }
        }

        return faction;
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
