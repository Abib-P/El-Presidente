package com.elpresidente.factions;

import com.elpresidente.faction.Faction;

import java.util.List;

public class Factions {

    public static final String LoyalistsFactionKey = "LOYALISTS";

    List<Faction> factions;

    public Factions(List<Faction> factions){
        this.factions = factions;
    }

    public void corrupt(String factionName){
        Faction faction = getFaction(factionName);
        if(faction == null) return;
        corrupt(faction);
    }

    public void corrupt(Faction faction){

        if(faction == null) return;

        faction.addSatisfaction(10);

        faction = getFaction(LoyalistsFactionKey);
        if(faction == null) return;

        faction.addSatisfaction( -faction.getCorruptionImpactOnLoyalist());
    }

    private Faction getFaction(String key){
        Faction faction = null;

        for (Faction fac: factions) {
            if(fac.getKey().equals(key)){
                faction = fac;
            }
        }

        return faction;
    }

    public Faction getFactionByName(String name){
        Faction faction = null;

        for (Faction fac: factions) {
            if(fac.getName().equals(name)){
                faction = fac;
            }
        }

        return faction;
    }

    public boolean areLoyalist(){
        return getFaction(LoyalistsFactionKey) != null;
    }

    public void populate(){
        double[] stats = new double[factions.size()];
        int total = 0;

        float grow = (float) Math.random() *0.09f +1.01f;
        int numberOfNewPopulation = (int) (getTotalNumberOfPartisan() * grow);
        System.out.println("new pop: "+ numberOfNewPopulation);
        for (int i = 0; i < factions.size(); i++) {
            Faction faction = factions.get(i);
            stats[i] = faction.getSatisfaction() * faction.getPartisanNumber();
            total += stats[i];
        }

        for (int i = 0; i < factions.size(); i++) {
            stats[i] /= total;
        }

        for (int i = 0; i < factions.size(); i++) {
            Faction faction = factions.get(i);
            System.out.println(faction.getName() +" ("+faction.getPartisanNumber()+") "+faction.getSatisfaction()+"% :"+ (int) (numberOfNewPopulation * stats[i]));
            faction.addPartisanNumber((int) (numberOfNewPopulation * stats[i]));
        }
    }

    public void addSatisfactionToFaction(String factionName, int delta){
        Faction faction = getFaction(factionName);
        if(faction == null)  return;

        faction.addSatisfaction(delta);
    }

    public void addPopulation(int numberOfPeople){
        int n = numberOfPeople >= 0 ? 1 : -1;
        Faction faction;
        for (int i = 0; i <  Math.abs(numberOfPeople); i++) {
            faction =  factions.get( (int) (Math.random() * factions.size()) );
            faction.addPartisanNumber(n);

            if( numberOfPeople < 0) {
                faction.addSatisfaction( numberOfPeople);
            }

        }
    }

    public Float getGlobalSatisfaction(){
        return  factions.stream()
                .map(faction -> faction.getSatisfaction()*faction.getPartisanNumber())
                .reduce(0, Integer::sum) / (float)getTotalNumberOfPartisan();
    }

    public Integer getTotalNumberOfPartisan(){
        return factions.stream()
                .map(Faction::getPartisanNumber)
                .reduce(0, Integer::sum);
    }

    public List<Faction> getFactions() {
        return factions;
    }
}
